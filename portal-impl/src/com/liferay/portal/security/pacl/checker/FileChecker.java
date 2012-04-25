/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.UniqueList;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;

import java.security.Permission;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class FileChecker extends BaseChecker {

	public void afterPropertiesSet() {
		_rootDir = WebDirDetector.getRootDir(getClassLoader());

		ServletContext servletContext = ServletContextPool.get(
			getServletContextName());

		if (servletContext != null) {
			File tempDir = (File)servletContext.getAttribute(
				JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

			_workDir = tempDir.getAbsolutePath();
		}

		initPermissions();
	}

	public void checkPermission(Permission permission) {
		String name = permission.getName();
		String actions = permission.getActions();

		if (actions.equals(FILE_PERMISSION_ACTION_DELETE)) {
			if (!hasDelete(permission)) {
				throw new SecurityException("Attempted to delete file " + name);
			}
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_EXECUTE)) {
			if (!hasExecute(permission)) {
				throw new SecurityException(
					"Attempted to execute file " + name);
			}
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_READ)) {
			if (!hasRead(permission)) {
				throw new SecurityException("Attempted to read file " + name);
			}
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_WRITE)) {
			if (!hasWrite(permission)) {
				throw new SecurityException("Attempted to write file " + name);
			}
		}
	}

	protected void addCanonicalPath(List<String> paths, String path) {
		Iterator<String> itr = paths.iterator();

		while (itr.hasNext()) {
			String curPath = itr.next();

			if (curPath.startsWith(path) &&
				(curPath.length() > path.length())) {

				itr.remove();
			}
			else if (path.startsWith(curPath)) {
				return;
			}
		}

		path = StringUtil.replace(
			path, StringPool.BACK_SLASH, StringPool.SLASH);

		if (path.endsWith(StringPool.SLASH)) {
			path = path + "-";
		}

		paths.add(path);
	}

	protected void addCanonicalPaths(List<String> paths, File directory)
		throws IOException {

		addCanonicalPath(
			paths, directory.getCanonicalPath() + StringPool.SLASH);

		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				addCanonicalPaths(paths, file);
			}
			else {
				File canonicalFile = new File(file.getCanonicalPath());

				File parentFile = canonicalFile.getParentFile();

				addCanonicalPath(
					paths, parentFile.getPath() + StringPool.SLASH);
			}
		}
	}

	protected void addPermission(
		List<Permission> permissions, String path, String action) {

		if (_log.isDebugEnabled()) {
			_log.debug("Allowing " + action + " on " + path);
		}

		Permission permission = new FilePermission(path, action);

		permissions.add(permission);
	}

	protected List<Permission> getPermissions(String key, String action) {
		List<Permission> permissions = new CopyOnWriteArrayList<Permission>();

		String value = getProperty(key);

		if (value != null) {
			String[] paths = StringUtil.split(value);

			if (value.contains("${comma}")) {
				for (int i = 0; i < paths.length; i++) {
					paths[i] = StringUtil.replace(
						paths[i], "${comma}", StringPool.COMMA);
				}
			}

			for (String path : paths) {
				addPermission(permissions, path, action);
			}
		}

		// Plugin can do anything, except execute, in its own work folder

		String pathContext = ContextPathUtil.getContextPath(
			PropsValues.PORTAL_CTX);

		ServletContext servletContext = ServletContextPool.get(pathContext);

		if (!action.equals(FILE_PERMISSION_ACTION_EXECUTE) &&
			(_workDir != null)) {

			addPermission(permissions, _workDir, action);
			addPermission(permissions, _workDir + "/-", action);

			if (servletContext != null) {
				File tempDir = (File)servletContext.getAttribute(
					JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

				String tempDirAbsolutePath = tempDir.getAbsolutePath();

				if (action.equals(FILE_PERMISSION_ACTION_READ)) {
					addPermission(permissions, tempDirAbsolutePath, action);
				}

				addPermission(permissions, tempDirAbsolutePath + "/-", action);
			}
		}

		if (!action.equals(FILE_PERMISSION_ACTION_READ)) {
			return permissions;
		}

		List<String> paths = new UniqueList<String>();

		// JDK

		// There may be JARs in the system library that are symlinked. We must
		// include their canonical paths or they will fail permission checks.

		try {
			File file = new File(System.getProperty("java.home") + "/lib");

			addCanonicalPaths(paths, file);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		// Shared libs

		paths.add(_globalSharedLibDir + "-");

		// Plugin

		paths.add(_rootDir + "-");

		// Portal

		if (!_portalDir.equals(_rootDir)) {
			paths.add(_portalDir + "html/common/-");
			paths.add(_portalDir + "html/taglib/-");
			paths.add(_portalDir + "localhost/html/common/-");
			paths.add(_portalDir + "localhost/html/taglib/-");
			paths.add(_portalDir + "localhost/WEB-INF/tld/-");
			paths.add(_portalDir + "WEB-INF/classes/java/-");
			paths.add(_portalDir + "WEB-INF/classes/javax/-");
			paths.add(_portalDir + "WEB-INF/classes/org/apache/-");
			paths.add(
				_portalDir + "WEB-INF/classes/META-INF/services/" +
					"javax.el.ExpressionFactory");
			paths.add(_portalDir + "WEB-INF/tld/-");
		}

		for (String path : paths) {
			addPermission(permissions, path, action);
		}

		return permissions;
	}

	protected boolean hasDelete(Permission permission) {
		for (Permission deleteFilePermission : _deletePermissions) {
			if (deleteFilePermission.implies(permission)) {
				return true;
			}
		}

		if (isJSPCompiler(
				permission.getName(), FILE_PERMISSION_ACTION_DELETE)) {

			return true;
		}

		return false;
	}

	protected boolean hasExecute(Permission permission) {
		for (Permission executeFilePermission : _executePermissions) {
			if (executeFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	protected boolean hasRead(Permission permission) {
		for (Permission readFilePermission : _readPermissions) {
			if (readFilePermission.implies(permission)) {
				return true;
			}
		}

		if (isJSPCompiler(permission.getName(), FILE_PERMISSION_ACTION_READ)) {
			return true;
		}

		return false;
	}

	protected boolean hasWrite(Permission permission) {
		for (Permission writeFilePermission : _writePermissions) {
			if (writeFilePermission.implies(permission)) {
				return true;
			}
		}

		if (isJSPCompiler(permission.getName(), FILE_PERMISSION_ACTION_WRITE)) {
			return true;
		}

		return false;
	}

	protected void initPermissions() {
		_deletePermissions = getPermissions(
			"security-manager-files-delete", FILE_PERMISSION_ACTION_DELETE);
		_executePermissions = getPermissions(
			"security-manager-files-execute", FILE_PERMISSION_ACTION_EXECUTE);
		_readPermissions = getPermissions(
			"security-manager-files-read", FILE_PERMISSION_ACTION_READ);
		_writePermissions = getPermissions(
			"security-manager-files-write", FILE_PERMISSION_ACTION_WRITE);
	}

	private static Log _log = LogFactoryUtil.getLog(FileChecker.class);

	private List<Permission> _deletePermissions;
	private List<Permission> _executePermissions;
	private String _globalSharedLibDir =
		PropsValues.LIFERAY_LIB_GLOBAL_SHARED_DIR;
	private String _portalDir = PropsValues.LIFERAY_WEB_PORTAL_DIR;
	private List<Permission> _readPermissions;
	private String _rootDir;
	private String _workDir;
	private List<Permission> _writePermissions;

}