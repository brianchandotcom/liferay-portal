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
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PathUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLClassUtil;
import com.liferay.portal.util.PropsUtil;
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

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class FileChecker extends BaseChecker {

	public void afterPropertiesSet() {
		_rootDir = WebDirDetector.getRootDir(getClassLoader());

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
			"${jboss.home.dir}",
			"${jetty.home}",
			"${jonas.base}",
			"${liferay.web.portal.dir}",
			"${org.apache.geronimo.home.dir}",
			"${org.apache.geronimo.home.dir}",
			"${plugin.servlet.context.name}",
			"${release.info.version}",
			"${weblogic.home.dir}"
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
			System.getProperty("jboss.home.dir"),
			System.getProperty("jetty.home"), System.getProperty("jonas.base"),
			_portalDir, System.getProperty("org.apache.geronimo.home.dir"),
			System.getProperty("resin.home"), getServletContextName(),
			ReleaseInfo.getVersion(), System.getenv("DOMAIN_HOME")
		};

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Default read paths replace with " +
					StringUtil.merge(_defaultReadPathsToArray));
		}

		initPermissions();
	}

	public void checkPermission(Permission permission) {
		String name = permission.getName();
		String actions = permission.getActions();

		if (actions.equals(FILE_PERMISSION_ACTION_DELETE)) {
			if (!hasDelete(permission)) {
				throwSecurityException(
					_log, "Attempted to delete file " + name);
			}
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_EXECUTE)) {
			if (!hasExecute(permission)) {
				throwSecurityException(
					_log, "Attempted to execute file " + name);
			}
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_READ)) {
			if (PortalSecurityManagerThreadLocal.isCheckReadFile() &&
				!hasRead(permission)) {

				throwSecurityException(_log, "Attempted to read file " + name);
			}
		}
		else if (actions.equals(FILE_PERMISSION_ACTION_WRITE)) {
			if (!hasWrite(permission)) {
				throwSecurityException(_log, "Attempted to write file " + name);
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
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		// Shared libs

		paths.add(_globalSharedLibDir + "-");

		// Plugin

		paths.add(_rootDir + "-");

		// Portal

		addDefaultReadPaths(paths, "common");
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

		Class<?> callerClass10 = Reflection.getCallerClass(10);

		if (isGeronimoJarFileClassLoader(
				callerClass10.getEnclosingClass()) &&
			CheckerUtil.isAccessControllerDoPrivileged(11)) {

			return true;
		}

		Class<?> callerClass20 = Reflection.getCallerClass(20);

		if (isGeronimoJarFileClassLoader(
				callerClass20.getEnclosingClass()) &&
			CheckerUtil.isAccessControllerDoPrivileged(21)) {

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

	protected boolean isGeronimoJarFileClassLoader(Class<?> clazz) {
		if (!ServerDetector.isGeronimo()) {
			return false;
		}

		if (clazz == null) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_JAR_FILE_CLASS_LOADER)) {
			return false;
		}

		String actualClassLocation = PACLClassUtil.getClassLocation(clazz);
		String expectedClassLocation = PathUtil.toUnixPath(
			System.getProperty("org.apache.geronimo.home.dir") +
				"/lib/geronimo-kernel-");

		return actualClassLocation.contains(expectedClassLocation);
	}

	private static final String _CLASS_NAME_JAR_FILE_CLASS_LOADER =
		"org.apache.geronimo.kernel.classloader.JarFileClassLoader";

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