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
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.FilePermission;

import java.security.Permission;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Brian Wing Shun Chan
 */
public class FileChecker extends BaseChecker {

	public void afterPropertiesSet() {
		_rootDir = WebDirDetector.getRootDir(getClassLoader());

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

	public boolean hasDelete(Permission permission) {
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

	public boolean hasExecute(Permission permission) {
		for (Permission executeFilePermission : _executePermissions) {
			if (executeFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasRead(Permission permission) {
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

	public boolean hasWrite(Permission permission) {
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

		if (!action.equals(FILE_PERMISSION_ACTION_READ)) {
			return permissions;
		}

		String catalinaHome = System.getProperty("catalina.home") + "/";
		String javaHome = System.getProperty("java.home") + "/";

		String portalWebDir = PropsValues.LIFERAY_WEB_PORTAL_DIR;

		String[] defaultPaths = {

			// JDK

			javaHome + "lib/-",

			// Plugin

			_rootDir + "-",

			// Portal

			portalWebDir + "html/common/-", portalWebDir + "html/taglib/-",
			portalWebDir + "localhost/html/taglib/-",
			portalWebDir + "localhost/WEB-INF/tld/-",
			portalWebDir + "WEB-INF/classes/java/-",
			portalWebDir + "WEB-INF/classes/javax/-",
			portalWebDir + "WEB-INF/classes/org/apache/-",
			portalWebDir +
				"WEB-INF/classes/META-INF/services/javax.el.ExpressionFactory",
			portalWebDir + "WEB-INF/tld/-",

			// Tomcat

			catalinaHome + "lib/-",
			catalinaHome + "work/Catalina/localhost/" +
				getServletContextName() + "/-",
			catalinaHome + "work/Catalina/localhost/_",
			catalinaHome + "work/Catalina/localhost/_/-"
		};

		for (String defaultPath : defaultPaths) {
			addPermission(permissions, defaultPath, action);
		}

		return permissions;
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
	private List<Permission> _readPermissions;
	private String _rootDir;
	private List<Permission> _writePermissions;

}