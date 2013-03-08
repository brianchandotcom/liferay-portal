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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PathUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.servlet.DirectServletRegistryImpl;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

import java.security.Permission;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class FileChecker extends BaseChecker {

	public void afterPropertiesSet() {
		try {
			_rootDir = WebDirDetector.getRootDir(getClassLoader());
		}
		catch (Exception e) {

			// This means the WAR is probably not exploded

		}

		if (_log.isDebugEnabled()) {
			_log.debug("Root directory " + _rootDir);
		}

		ServletContext servletContext = ServletContextPool.get(
			getServletContextName());

		if (servletContext != null) {
			File tempDir = (File)servletContext.getAttribute(
				JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

			_workDir = tempDir.getAbsolutePath();

			if (_log.isDebugEnabled()) {
				_log.debug("Work directory " + _workDir);
			}
		}

		_defaultReadPathsFromArray = new String[] {
			"${auto.deploy.installed.dir}",
			"${catalina.base}",
			"${com.sun.aas.instanceRoot}",
			"${com.sun.aas.installRoot}",
			"${file.separator}",
			"${java.io.tmpdir}",
			"${jboss.home.dir}",
			"${jetty.home}",
			"${jonas.base}",
			"${liferay.web.portal.dir}",
			"${line.separator}",
			"${org.apache.geronimo.home.dir}",
			"${path.separator}",
			"${plugin.servlet.context.name}",
			"${release.info.version}",
			"${resin.home}",
			"${user.dir}",
			"${user.home}",
			"${user.name}",
			"${weblogic.domain.dir}",
			"${websphere.profile.dir}",
			StringPool.DOUBLE_SLASH
		};

		String installedDir = StringPool.BLANK;

		try {
			if (DeployManagerUtil.getDeployManager() != null) {
				installedDir = DeployManagerUtil.getInstalledDir();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_defaultReadPathsToArray = new String[] {
			installedDir, System.getProperty("catalina.base"),
			System.getProperty("com.sun.aas.instanceRoot"),
			System.getProperty("com.sun.aas.installRoot"),
			System.getProperty("file.separator"),
			System.getProperty("java.io.tmpdir"),
			System.getProperty("jboss.home.dir"),
			System.getProperty("jetty.home"), System.getProperty("jonas.base"),
			_portalDir, System.getProperty("line.separator"),
			System.getProperty("org.apache.geronimo.home.dir"),
			System.getProperty("path.separator"), getServletContextName(),
			ReleaseInfo.getVersion(), System.getProperty("resin.home"),
			System.getProperty("user.dir"), System.getProperty("user.home"),
			System.getProperty("user.name"), System.getenv("DOMAIN_HOME"),
			System.getenv("USER_INSTALL_ROOT"), StringPool.SLASH
		};

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Default read paths replace with " +
					StringUtil.merge(_defaultReadPathsToArray));
		}

		initPermissions();
	}

	@Override
	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments) {

		if ((arguments == null) || (arguments.length != 1) ||
			!(arguments[0] instanceof Permission)) {

			return null;
		}

		Permission permission = (Permission)arguments[0];

		String actions = permission.getActions();

		String key = null;

		if (actions.equals(FILE_PERMISSION_ACTION_DELETE)) {
			key = "security-manager-files-delete";
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_EXECUTE)) {
			key = "security-manager-files-execute";
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_READ)) {
			key = "security-manager-files-read";
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_WRITE)) {
			key = "security-manager-files-write";
		}
		else {
			return null;
		}

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		authorizationProperty.setKey(key);
		authorizationProperty.setValue(permission.getName());

		return authorizationProperty;
	}

	public boolean implies(Permission permission) {
		String name = permission.getName();
		String actions = permission.getActions();

		if (actions.equals(FILE_PERMISSION_ACTION_DELETE)) {
			if (!hasDelete(permission)) {
				logSecurityException(_log, "Attempted to delete file " + name);

				return false;
			}
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_EXECUTE)) {
			if (!hasExecute(permission)) {
				logSecurityException(_log, "Attempted to execute file " + name);

				return false;
			}
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_READ)) {
			if (PortalSecurityManagerThreadLocal.isCheckReadFile() &&
				!hasRead(permission)) {

				logSecurityException(_log, "Attempted to read file " + name);

				return false;
			}
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_WRITE)) {
			if (!hasWrite(permission)) {
				logSecurityException(_log, "Attempted to write file " + name);

				return false;
			}
		}

		return true;
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

	protected void addDefaultReadPaths(List<String> paths, String selector) {
		String[] pathsArray = PropsUtil.getArray(
			PropsKeys.PORTAL_SECURITY_MANAGER_FILE_CHECKER_DEFAULT_READ_PATHS,
			new Filter(selector));

		for (String path : pathsArray) {
			path = StringUtil.replace(
				path, _defaultReadPathsFromArray, _defaultReadPathsToArray);

			paths.add(path);
		}
	}

	protected void addPermission(
		List<Permission> permissions, String path, String actions) {

		if (_log.isDebugEnabled()) {
			_log.debug("Allowing " + actions + " on " + path);
		}

		String unixPath = PathUtil.toUnixPath(path);

		Permission unixPermission = new FilePermission(unixPath, actions);

		permissions.add(unixPermission);

		String windowsPath = PathUtil.toWindowsPath(path);

		Permission windowsPermission = new FilePermission(windowsPath, actions);

		permissions.add(windowsPermission);
	}

	protected List<Permission> getPermissions(String key, String actions) {
		List<Permission> permissions = new CopyOnWriteArrayList<Permission>();

		String value = getProperty(key);

		if (value != null) {
			value = StringUtil.replace(
				value, _defaultReadPathsFromArray, _defaultReadPathsToArray);

			String[] paths = StringUtil.split(value);

			if (value.contains("${comma}")) {
				for (int i = 0; i < paths.length; i++) {
					paths[i] = StringUtil.replace(
						paths[i], "${comma}", StringPool.COMMA);
				}
			}

			for (String path : paths) {
				addPermission(permissions, path, actions);
			}
		}

		// Plugin can do anything, except execute, in its own work folder

		String pathContext = ContextPathUtil.getContextPath(
			PropsValues.PORTAL_CTX);

		ServletContext servletContext = ServletContextPool.get(pathContext);

		if (!actions.equals(FILE_PERMISSION_ACTION_EXECUTE) &&
			(_workDir != null)) {

			addPermission(permissions, _workDir, actions);
			addPermission(permissions, _workDir + "/-", actions);

			if (servletContext != null) {
				File tempDir = (File)servletContext.getAttribute(
					JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

				String tempDirAbsolutePath = tempDir.getAbsolutePath();

				if (_log.isDebugEnabled()) {
					_log.debug("Temp directory " + tempDirAbsolutePath);
				}

				if (actions.equals(FILE_PERMISSION_ACTION_READ)) {
					addPermission(permissions, tempDirAbsolutePath, actions);
				}

				addPermission(permissions, tempDirAbsolutePath + "/-", actions);
			}
		}

		if (!actions.equals(FILE_PERMISSION_ACTION_READ)) {
			return permissions;
		}

		List<String> paths = new UniqueList<String>();

		// JDK

		// There may be JARs in the system library that are symlinked. We must
		// include their canonical paths or they will fail permission checks.

		try {
			File file = new File(System.getProperty("java.home") + "/lib");

			addCanonicalPaths(paths, file);

			ClassLoader classLoader = ClassLoader.getSystemClassLoader();

			Enumeration<URL> enumeration = classLoader.getResources(
				"META-INF/MANIFEST.MF");

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				URLConnection urlConnection = url.openConnection();

				if (urlConnection instanceof JarURLConnection) {
					JarURLConnection jarURLConnection =
						(JarURLConnection)url.openConnection();

					URL jarFileURL = jarURLConnection.getJarFileURL();

					String fileName = jarFileURL.getFile();

					int pos = fileName.lastIndexOf(File.separatorChar);

					if (pos != -1) {
						fileName = fileName.substring(0, pos + 1);
					}

					addCanonicalPath(paths, fileName);
				}
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		// Shared libs

		if (Validator.isNotNull(_globalSharedLibDir)) {
			paths.add(_globalSharedLibDir + "-");
		}

		// Plugin

		if (_rootDir != null) {
			paths.add(_rootDir + "-");
		}

		// Portal

		addDefaultReadPaths(paths, ServerDetector.getServerId());

		for (String path : paths) {
			addPermission(permissions, path, actions);
		}

		return permissions;
	}

	protected boolean hasDelete(Permission permission) {
		for (Permission deleteFilePermission : _deletePermissions) {
			if (deleteFilePermission.implies(permission)) {
				return true;
			}
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

		for (int i = 7;; i++) {
			Class<?> callerClass = Reflection.getCallerClass(i);

			if (callerClass == null) {
				return false;
			}

			if ((callerClass == DirectServletRegistryImpl.class) ||
				(callerClass == FileAvailabilityUtil.class)) {

				return true;
			}

			if (ClassLoader.class.isAssignableFrom(callerClass)) {
				String callerClassName = callerClass.getName();

				if (!callerClassName.equals(_CLASS_NAME_METHOD_UTIL)) {
					return true;
				}
			}

			if (ServerDetector.isGlassfish()) {
				Class<?> enclosingClass = callerClass.getEnclosingClass();

				if (enclosingClass != null) {
					if ((enclosingClass.getEnclosingClass() ==
							URLClassLoader.class) &&
						CheckerUtil.isAccessControllerDoPrivileged(i + 1)) {

						return true;
					}
				}
			}
		}
	}

	protected boolean hasWrite(Permission permission) {
		for (Permission writeFilePermission : _writePermissions) {
			if (writeFilePermission.implies(permission)) {
				return true;
			}
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

	private static final String _CLASS_NAME_METHOD_UTIL =
		"sun.reflect.misc.MethodUtil";

	private static Log _log = LogFactoryUtil.getLog(FileChecker.class);

	private String[] _defaultReadPathsFromArray;
	private String[] _defaultReadPathsToArray;
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