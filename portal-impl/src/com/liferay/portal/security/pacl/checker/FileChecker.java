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
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.util.PortalUtil;

import java.io.FilePermission;

import java.security.Permission;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Brian Wing Shun Chan
 */
public class FileChecker extends BaseChecker {

	public FileChecker(PACLPolicy paclPolicy) {
		super(paclPolicy);

		_rootDir = WebDirDetector.getRootDir(getClassLoader());

		initPermissions();
	}

	public boolean hasDelete(String fileName) {
		Permission permission = new FilePermission(
			fileName, _PERMISSION_DELETE);

		for (Permission deleteFilePermission : _deletePermissions) {
			if (deleteFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasExecute(String fileName) {
		Permission permission = new FilePermission(
			fileName, _PERMISSION_EXECUTE);

		for (Permission executeFilePermission : _executePermissions) {
			if (executeFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasRead(String fileName) {
		Permission permission = new FilePermission(fileName, _PERMISSION_READ);

		for (Permission readFilePermission : _readPermissions) {
			if (readFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasWrite(String fileName) {
		Permission permission = new FilePermission(fileName, _PERMISSION_WRITE);

		for (Permission writeFilePermission : _writePermissions) {
			if (writeFilePermission.implies(permission)) {
				return true;
			}
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

		if (!action.equals(_PERMISSION_READ)) {
			return permissions;
		}

		String catalinaHome = System.getProperty("catalina.home") + "/";

		String portalWebDir = PortalUtil.getPortalWebDir();

		String[] defaultPaths = {

			// Plugin

			_rootDir + "-",

			// Plugin runtime

			catalinaHome + "work/Catalina/localhost/" +
				getServletContextName() + "/-",

			// Portal

			portalWebDir + "html/common/-", portalWebDir + "html/taglib/-",
			portalWebDir + "localhost/html/taglib/-",

			// Portal runtime

			portalWebDir + "WEB-INF/classes/org/apache/jasper/-",
			portalWebDir + "WEB-INF/classes/org/apache/tomcat/-",
			portalWebDir +
				"WEB-INF/classes/services/javax.el.ExpressionFactory",
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
			"security-manager-files-delete", _PERMISSION_DELETE);
		_executePermissions = getPermissions(
			"security-manager-files-execute", _PERMISSION_EXECUTE);
		_readPermissions = getPermissions(
			"security-manager-files-read", _PERMISSION_READ);
		_writePermissions = getPermissions(
			"security-manager-files-write", _PERMISSION_WRITE);
	}

	private static final String _PERMISSION_DELETE = "delete";

	private static final String _PERMISSION_EXECUTE = "execute";

	private static final String _PERMISSION_READ = "read";

	private static final String _PERMISSION_WRITE = "write";

	private static Log _log = LogFactoryUtil.getLog(FileChecker.class);

	private List<Permission> _deletePermissions;
	private List<Permission> _executePermissions;
	private List<Permission> _readPermissions;
	private String _rootDir;
	private List<Permission> _writePermissions;

}