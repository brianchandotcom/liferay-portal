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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PluginsGitSvnSyncer {

	public static void main(String[] args) {
		String gitPluginsDirName = System.getProperty("git.plugins.dir");
		String svnPluginsDirName = System.getProperty("svn.plugins.dir");
		String syncTo = System.getProperty("sync.to");

		new PluginsGitSvnSyncer(gitPluginsDirName, svnPluginsDirName, syncTo);
	}

	public PluginsGitSvnSyncer(
		String gitPluginsDirName, String svnPluginsDirName, String syncTo) {

		try {
			if (!gitPluginsDirName.endsWith("/")) {
				gitPluginsDirName = gitPluginsDirName + "/";
			}

			if (!svnPluginsDirName.endsWith("/")) {
				svnPluginsDirName = svnPluginsDirName + "/";
			}

			if (syncTo.equals("git")) {
				_updateGitIgnores(svnPluginsDirName, gitPluginsDirName);
			}
			else if (syncTo.equals("svn")) {
				_updateSvnIgnores(gitPluginsDirName, svnPluginsDirName);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String[] _exec(String cmd) throws Exception {
		Runtime runtime = Runtime.getRuntime();

		Process process = runtime.exec(cmd);

		String[] stdout = _getExecOutput(process.getInputStream());
		String[] stderr = _getExecOutput(process.getErrorStream());

		if (stderr.length > 0) {
			StringBundler sb = new StringBundler(stderr.length * 3 + 3);

			sb.append("Received errors in executing '");
			sb.append(cmd);
			sb.append("'\n");

			for (String err : stderr) {
				sb.append("\t");
				sb.append(err);
				sb.append("\n");
			}

			throw new Exception(sb.toString());
		}

		return stdout;
	}

	private String[] _getExecOutput(InputStream is) throws IOException {
		List<String> list = new ArrayList<>();

		UnsyncBufferedReader unsyncBufferedReader = null;

		try {
			unsyncBufferedReader = new UnsyncBufferedReader(
				new InputStreamReader(is));

			String line = unsyncBufferedReader.readLine();

			while (line != null) {
				line = line.trim();

				if (Validator.isNotNull(line)) {
					list.add(line);
				}

				line = unsyncBufferedReader.readLine();
			}
		}
		finally {
			if (unsyncBufferedReader != null) {
				try {
					unsyncBufferedReader.close();
				}
				catch (Exception e) {
				}
			}
		}

		return list.toArray(new String[list.size()]);
	}

	private void _updateGitIgnores(String srcDirName, String destDirName)
		throws Exception {

		for (String pluginType : _PLUGIN_TYPES) {
			String[] dirNames = _fileUtil.listDirs(srcDirName + pluginType);

			for (String dirName : dirNames) {
				if (dirName.equals(".svn")) {
					continue;
				}

				for (String pluginDirName : _PLUGIN_DIR_NAMES) {
					_updateGitIgnores(
						srcDirName + pluginType + "/",
						destDirName + pluginType + "/",
						dirName + pluginDirName + "/");
				}
			}
		}
	}

	private void _updateGitIgnores(
			String srcDirName, String destDirName, String dirName)
		throws Exception {

		File gitIgnoreFile = new File(destDirName + dirName + ".gitignore");

		if (!_fileUtil.exists(srcDirName + dirName + ".svn")) {
			_fileUtil.delete(gitIgnoreFile);

			return;
		}

		List<String> ignores = null;

		if (!dirName.contains("/docroot/")) {
			ignores = Collections.emptyList();
		}
		else {
			ignores = ListUtil.fromArray(
				_exec(_SVN_GET_IGNORES + srcDirName + dirName));
		}

		Collections.sort(ignores);

		Iterator<String> itr = ignores.iterator();

		while (itr.hasNext()) {
			String ignore = itr.next();

			if (ignore.equals("classes")) {
				itr.remove();
			}
		}

		if (!ignores.isEmpty()) {
			String[] ignoresArray = ignores.toArray(new String[ignores.size()]);

			for (int i = 0; i < ignoresArray.length; i++) {
				String ignore = ignoresArray[i];

				if (Validator.isNotNull(ignore) && !ignore.startsWith("/")) {
					ignoresArray[i] = "/" + ignore;
				}
			}

			_fileUtil.write(
				destDirName + dirName + ".gitignore",
				StringUtil.merge(ignoresArray, "\n"));
		}
		else {
			_fileUtil.delete(gitIgnoreFile);
		}
	}

	private void _updateSvnIgnores(String srcDirName, String destDirName)
		throws Exception {

		for (String pluginType : _PLUGIN_TYPES) {
			String[] dirNames = _fileUtil.listDirs(srcDirName + pluginType);

			for (String dirName : dirNames) {
				for (String pluginDirName : _PLUGIN_DIR_NAMES) {
					_updateSvnIgnores(
						srcDirName + pluginType + "/",
						destDirName + pluginType + "/",
						dirName + pluginDirName + "/");
				}
			}
		}
	}

	private void _updateSvnIgnores(
			String srcDirName, String destDirName, String dirName)
		throws Exception {

		if (!_fileUtil.exists(destDirName + dirName)) {
			return;
		}

		File gitIgnoreFile = new File(srcDirName + dirName + ".gitignore");
		File svnDir = new File(destDirName + dirName + ".svn");

		if (gitIgnoreFile.exists() && !svnDir.exists()) {
			System.out.println(
				"Invalid SVN directory " + destDirName + dirName);

			return;
		}

		List<String> ignores = null;

		if (!dirName.contains("/docroot")) {
			ignores = new ArrayList<>();

			ignores.add("bin");
			ignores.add("classes");
			ignores.add("tmp");
		}
		else {
			ignores = ListUtil.fromFile(gitIgnoreFile);

			for (int i = 0; i < ignores.size(); i++) {
				String ignore = ignores.get(i);

				if (ignore.startsWith("/")) {
					ignore = ignore.substring(1);
				}

				ignores.set(i, ignore);
			}

			if (dirName.endsWith("/docroot/WEB-INF/")) {
				if (!ignores.contains("classes")) {
					ignores.add("classes");
				}
			}
		}

		Collections.sort(ignores);

		if (ignores.isEmpty() && !svnDir.exists()) {
			return;
		}

		if (ignores.isEmpty()) {
			try {
				_exec(_SVN_DEL_IGNORES + destDirName + dirName);
			}
			catch (Exception e) {
				String message = e.getMessage();

				if (!message.contains(
						"svn: Attempting to delete nonexistent property " +
							"'svn:ignore'")) {

					throw e;
				}
			}

			return;
		}

		File tempFile = _fileUtil.createTempFile("svn-ignores-", "tmp");

		try {
			String[] ignoresArray = ignores.toArray(new String[ignores.size()]);

			_fileUtil.write(tempFile, StringUtil.merge(ignoresArray, "\n"));

			_exec(
				_SVN_SET_IGNORES + "-F \"" + tempFile.getCanonicalPath() +
					"\" \"" + destDirName + dirName + "\"");
		}
		finally {
			_fileUtil.delete(tempFile);
		}
	}

	private static final String[] _PLUGIN_DIR_NAMES = {
		"", "/docroot", "/docroot/WEB-INF", "/docroot/WEB-INF/lib",
		"/docroot/WEB-INF/tld"
	};

	private static final String[] _PLUGIN_TYPES =
		{"clients", "ext", "hooks", "layouttpl", "portlets", "themes", "webs"};

	private static final String _SVN_DEL_IGNORES = "svn propdel svn:ignore ";

	private static final String _SVN_GET_IGNORES = "svn propget svn:ignore ";

	private static final String _SVN_SET_IGNORES = "svn propset svn:ignore ";

	private static final FileImpl _fileUtil = FileImpl.getInstance();

}