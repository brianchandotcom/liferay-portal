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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.BaseJSONWebServiceConfigurator;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceConfigurator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.net.URL;
import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import jodd.io.findfile.ClassFinder;
import jodd.io.findfile.FindFile;
import jodd.io.findfile.RegExpFindFile;

import jodd.util.ClassLoaderUtil;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public class JSONWebServiceConfiguratorImpl extends ClassFinder
	implements JSONWebServiceConfigurator {

	public JSONWebServiceConfiguratorImpl() {

		// We cannot extend two classes, so use an anonymous delegate.

		_baseJSONWebServiceConfigurator = new BaseJSONWebServiceConfigurator() {

			@Override
			public void configure() throws PortalException, SystemException {
				File[] classPathFiles = null;

				if (getClassLoader() !=
						PACLClassLoaderUtil.getPortalClassLoader()) {

					classPathFiles = getPluginClassPathFiles();
				}
				else {
					classPathFiles = getPortalClassPathFiles();
				}

				_configure(classPathFiles);
			}

		};
	}

	public void clean() {
		_baseJSONWebServiceConfigurator.clean();
	}

	public void configure() throws PortalException, SystemException {
		_baseJSONWebServiceConfigurator.configure();
	}

	public int getRegisteredActionsCount() {
		return _baseJSONWebServiceConfigurator.getRegisteredActionsCount();
	}

	public void init(ServletContext servletContext, ClassLoader classLoader) {
		_baseJSONWebServiceConfigurator.init(servletContext, classLoader);

		setIncludedJars(
			"*_wl_cls_gen.jar", "*-hook-service*.jar", "*-portlet-service*.jar",
			"*-web-service*.jar", "*portal-impl.jar", "*portal-service.jar");
	}

	public void registerClass(String className, InputStream inputStream)
		throws Exception {

		_baseJSONWebServiceConfigurator.registerClass(className, inputStream);
	}

	protected File[] getPluginClassPathFiles() throws SystemException {
		ClassLoader classLoader =
			_baseJSONWebServiceConfigurator.getClassLoader();

		URL servicePropertiesURL = classLoader.getResource(
			"service.properties");

		String servicePropertiesPath = null;

		try {
			servicePropertiesPath = URLDecoder.decode(
				servicePropertiesURL.getPath(), StringPool.UTF8);
		}
		catch (UnsupportedEncodingException uee) {
			throw new SystemException(uee);
		}

		File classPathFile = null;

		File libDir = null;

		int pos = servicePropertiesPath.indexOf("_wl_cls_gen.jar!");

		if (pos != -1) {
			String wlClsGenJarPath = servicePropertiesPath.substring(
				0, pos + 15);

			classPathFile = new File(wlClsGenJarPath);

			libDir = new File(classPathFile.getParent());
		}
		else {
			File servicePropertiesFile = new File(servicePropertiesPath);

			classPathFile = servicePropertiesFile.getParentFile();

			libDir = new File(classPathFile.getParent(), "lib");
		}

		List<File> classPaths = new ArrayList<File>();

		classPaths.add(classPathFile);

		FindFile findFile = new RegExpFindFile(
			".*-(hook|portlet|web)-service.*\\.jar");

		findFile.searchPath(libDir);

		File file = null;

		while ((file = findFile.nextFile()) != null) {
			if (classPaths.contains(file)) {
				continue;
			}

			classPaths.add(file);
		}

		File classesDir = new File(libDir.getParent(), "classes");

		if (!classPaths.contains(classesDir)) {
			classPaths.add(classesDir);
		}

		return classPaths.toArray(new File[classPaths.size()]);
	}

	protected File[] getPortalClassPathFiles() {
		File[] classPathFiles = null;

		File portalImplJarFile = new File(
			PortalUtil.getPortalLibDir(), "portal-impl.jar");
		File portalServiceJarFile = new File(
			PortalUtil.getGlobalLibDir(), "portal-service.jar");

		if (portalImplJarFile.exists() && portalServiceJarFile.exists()) {
			classPathFiles = new File[2];

			classPathFiles[0] = portalImplJarFile;
			classPathFiles[1] = portalServiceJarFile;
		}
		else {
			classPathFiles = ClassLoaderUtil.getDefaultClasspath(
				_baseJSONWebServiceConfigurator.getClassLoader());
		}

		return classPathFiles;
	}

	@Override
	protected void onEntry(EntryData entryData) throws Exception {
		String className = entryData.getName();

		InputStream inputStream = new BufferedInputStream(
			entryData.openInputStream());

		registerClass(className, inputStream);
	}

	private void _configure(File... classPathFiles) throws PortalException {
		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			_log.debug("Configure JSON web service actions");

			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			scanPaths(classPathFiles);
		}
		catch (Exception e) {
			throw new PortalException(e.getMessage(), e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Configured " + getRegisteredActionsCount() + " actions in " +
					stopWatch.getTime() + " ms");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceConfiguratorImpl.class);

	private BaseJSONWebServiceConfigurator _baseJSONWebServiceConfigurator;

}