/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ant.jgit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;

/**
 * @author Shuyang Zhou
 */
public class GitUpToDateCacheTask extends Task {

	@Override
	public void execute() throws BuildException {
		if (_dir == null) {
			throw new BuildException(
				"Dir attribute is required", getLocation());
		}

		List<Path> paths = scanSnapshotPaths();

		if (paths.isEmpty()) {
			return;
		}

		Properties properties = new Properties();

		properties.put("pname", UpToDateUtil.getProcessName());

		File gitDir = PathUtil.getGitDir(_gitDir, getProject(), getLocation());

		try (Repository repository = RepositoryCache.open(
				FileKey.exact(gitDir, FS.DETECTED))) {

			Git git = new Git(repository);

			for (Path path : paths) {
				String relativePath = PathUtil.toRelativePath(
					gitDir, path.toString());

				if (!UpToDateUtil.isClean(git, relativePath)) {
					properties.put(relativePath, "false");

					continue;
				}

				properties.put(
					relativePath,
					Boolean.toString(
						!UpToDateUtil.hasChangedSince(
							repository, relativePath,
							getModuleSnapshotGitHash(path))));
			}

			if (properties.size() > 1) {
				File cacheFile = new File(_dir, _cacheFileName);

				cacheFile.deleteOnExit();

				try (OutputStream outputStream = new FileOutputStream(
					cacheFile)) {

					properties.store(outputStream, null);
				}
			}
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setCacheFileName(String cacheFileName) {
		_cacheFileName = cacheFileName;
	}

	public void setDir(String dir) {
		_dir = dir;
	}

	public void setGitDir(File gitDir) {
		_gitDir = gitDir;
	}

	public void setSnapshotFileName(String snapshotFileName) {
		_snapshotFileName = snapshotFileName;
	}

	protected String getModuleSnapshotGitHash(Path path) throws IOException {
		try (InputStream inputStream = Files.newInputStream(
			path.resolve(_snapshotFileName))) {

			Properties properties = new Properties();

			properties.load(inputStream);

			return properties.getProperty("module.snapshot.git.hash");
		}
	}

	protected List<Path> scanSnapshotPaths() {
		final List<Path> paths = new ArrayList<>();

		try {
			Files.walkFileTree(
				Paths.get(_dir),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes) {

						if (Files.exists(dirPath.resolve(_snapshotFileName))) {
							paths.add(dirPath);

							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioe) {
			throw new BuildException(ioe);
		}

		return paths;
	}

	private String _cacheFileName = "uptodate.properties";
	private String _dir;
	private File _gitDir;
	private String _snapshotFileName = "snapshot.properties";

}