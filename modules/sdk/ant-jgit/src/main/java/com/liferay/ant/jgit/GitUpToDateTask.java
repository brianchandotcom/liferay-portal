/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.jgit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.condition.Condition;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.util.FS;

/**
 * @author Shuyang Zhou
 */
public class GitUpToDateTask extends Task implements Condition {

	@Override
	public boolean eval() throws BuildException {
		if (_path == null) {
			throw new BuildException(
				"Path attribute is required", getLocation());
		}

		if (_since == null) {
			throw new BuildException(
				"Since attribute is required", getLocation());
		}

		try {
			Map<String, Boolean> upToDateMap = loadUpToDateCache();

			File gitDir = PathUtil.getGitDir(
				_gitDir, getProject(), getLocation());

			String relativePath = PathUtil.toRelativePath(gitDir, _path);

			if (upToDateMap == null) {
				log(
					"Unable to load cache file, fallback to up to date " +
						"checking",
					Project.MSG_VERBOSE);
			}
			else {
				Boolean upToDate = upToDateMap.get(relativePath);

				if (upToDate == null) {
					log(
						"Cache missed for " + relativePath +
							", fallback to up to date checking",
						Project.MSG_VERBOSE);
				}
				else {
					return upToDate;
				}
			}

			RepositoryCache.FileKey fileKey = RepositoryCache.FileKey.exact(
				gitDir, FS.DETECTED);

			try (Repository repository = fileKey.open(true)) {
				if (UpToDateUtil.isClean(new Git(repository), relativePath) &&
					!UpToDateUtil.hasChangedSince(
						repository, relativePath, _since,
						_ignoredMessagePattern)) {

					return true;
				}

				return false;
			}
			catch (Exception exception) {
				throw new BuildException(
					"Unable to check cleanness for path " + _path, exception);
			}
		}
		catch (IOException ioException) {
			throw new BuildException(ioException);
		}
	}

	@Override
	public void execute() throws BuildException {
		if (_property == null) {
			throw new BuildException(
				"Property attribute is required", getLocation());
		}

		if (eval()) {
			Project currentProject = getProject();

			if (_value == null) {
				currentProject.setNewProperty(_property, "true");
			}
			else {
				currentProject.setNewProperty(_property, _value);
			}
		}
	}

	public void setCacheFileName(String cacheFileName) {
		_cacheFileName = cacheFileName;
	}

	public void setGitDir(File gitDir) {
		_gitDir = gitDir;
	}

	public void setIgnoredMessagePattern(String ignoredMessagePattern) {
		_ignoredMessagePattern = ignoredMessagePattern;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setProperty(String property) {
		_property = property;
	}

	public void setSince(String since) {
		_since = since;
	}

	protected Path getCacheFilePath() {
		Project currentProject = getProject();

		File baseDir = currentProject.getBaseDir();

		Path path = baseDir.toPath();

		while (path != null) {
			Path cacheFilePath = path.resolve(_cacheFileName);

			if (Files.exists(cacheFilePath)) {
				return cacheFilePath;
			}

			path = path.getParent();
		}

		return null;
	}

	protected Map<String, Boolean> loadUpToDateCache() throws IOException {
		Map<String, Boolean> upToDateMap = _upToDateMap;

		if (upToDateMap != null) {
			return upToDateMap;
		}

		Path cacheFilePath = getCacheFilePath();

		if (cacheFilePath == null) {
			return null;
		}

		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(cacheFilePath)) {
			properties.load(inputStream);
		}

		Object pname = properties.remove("pname");

		if (pname == null) {
			Files.delete(cacheFilePath);

			log(
				"Deleted corrupted cache file (missing process name info)" +
					_cacheFileName,
				Project.MSG_ERR);

			return null;
		}

		if (!pname.equals(UpToDateUtil.getProcessName())) {
			Files.delete(cacheFilePath);

			log(
				"Deleted left over cache file from previous Ant process" +
					_cacheFileName,
				Project.MSG_WARN);

			return null;
		}

		upToDateMap = new HashMap<>();

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			upToDateMap.put(
				String.valueOf(entry.getKey()),
				Boolean.parseBoolean(String.valueOf(entry.getValue())));
		}

		_upToDateMap = upToDateMap;

		return upToDateMap;
	}

	private static volatile Map<String, Boolean> _upToDateMap;

	private String _cacheFileName = "uptodate.properties";
	private File _gitDir;
	private String _ignoredMessagePattern;
	private String _path;
	private String _property;
	private String _since;
	private String _value;

}