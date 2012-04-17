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

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 */
public class FileChecker extends BaseChecker {

	public FileChecker(PACLPolicy paclPolicy) {
		super(paclPolicy);

		_rootDir = WebDirDetector.getRootDir(getClassLoader());

		initPermissions();

		ClassLoader portalClassLoader = FileChecker.class.getClassLoader();

		_commonClassLoader = portalClassLoader.getParent();
	}

	public boolean hasDelete(String fileName) {
		Permission permission = new FilePermission(
			fileName, _PERMISSION_DELETE);

		for (Permission deleteFilePermission : _deletePermissions) {
			if (deleteFilePermission.implies(permission)) {
				return true;
			}
		}

		if (isJSPCompiler(fileName, _PERMISSION_DELETE)) {
			return true;
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

		if (isJSPCompiler(fileName, _PERMISSION_READ)) {
			return true;
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

		if (isJSPCompiler(fileName, _PERMISSION_WRITE)) {
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
		String javaHome = System.getProperty("java.home") + "/";

		String portalWebDir = PortalUtil.getPortalWebDir();

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
			"security-manager-files-delete", _PERMISSION_DELETE);
		_executePermissions = getPermissions(
			"security-manager-files-execute", _PERMISSION_EXECUTE);
		_readPermissions = getPermissions(
			"security-manager-files-read", _PERMISSION_READ);
		_writePermissions = getPermissions(
			"security-manager-files-write", _PERMISSION_WRITE);
	}

	protected boolean isJSPCompiler(String fileName, String action) {
		for (int i = 1;; i++) {
			Class<?> callerClass = Reflection.getCallerClass(i);

			if (callerClass == null) {
				break;
			}

			String callerClassName = callerClass.getName();

			if (callerClassName.startsWith(
					_PACKAGE_ORG_APACHE_JASPER_COMPILER) ||
				callerClassName.startsWith(
					_PACKAGE_ORG_APACHE_JASPER_XMLPARSER) ||
				callerClassName.startsWith(
					_PACKAGE_ORG_APACHE_NAMING_RESOURCES) ||
				callerClassName.equals(_ClASS_NAME_JASPER_LOADER)) {

				if (callerClass.getClassLoader() != _commonClassLoader) {
					_log.error(
						"A plugin is hijacking the JSP compiler via " +
							callerClassName + " to " + action + " " + fileName);

					return false;
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Allowing the JSP compiler via " + callerClassName +
							" to " + action + " " + fileName);
				}

				return true;
			}
		}

		return false;
	}

	private static final String _ClASS_NAME_JASPER_LOADER =
		"org.apache.jasper.servlet.JasperLoader";

	private static final String _PACKAGE_ORG_APACHE_JASPER_COMPILER =
		"org.apache.jasper.compiler.";

	private static final String _PACKAGE_ORG_APACHE_JASPER_XMLPARSER =
		"org.apache.jasper.xmlparser.";

	private static final String _PACKAGE_ORG_APACHE_NAMING_RESOURCES =
		"org.apache.naming.resources";

	private static final String _PERMISSION_DELETE = "delete";

	private static final String _PERMISSION_EXECUTE = "execute";

	private static final String _PERMISSION_READ = "read";

	private static final String _PERMISSION_WRITE = "write";

	private static Log _log = LogFactoryUtil.getLog(FileChecker.class);

	private ClassLoader _commonClassLoader;
	private List<Permission> _deletePermissions;
	private List<Permission> _executePermissions;
	private List<Permission> _readPermissions;
	private String _rootDir;
	private List<Permission> _writePermissions;

}