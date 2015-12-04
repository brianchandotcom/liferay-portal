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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.SanitizerLogWrapper;
import com.liferay.portal.kernel.url.URLContainer;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.CustomJspRegistryUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peter Fellwock
 * @author Raymond Augé
 */
public class CustomJspBagRegistryUtil {

	public static Map<ServiceReference<CustomJspBag>, CustomJspBag>
		getCustomJspBags() {

		return Collections.unmodifiableMap(_instance._customJspBagsMap);
	}

	protected InputStream getCustomJspInputStream(
			URLContainer urlContainer, String customJsp)
		throws IOException {

		URL url = urlContainer.getResource(customJsp);

		return url.openStream();
	}

	protected void getCustomJsps(
		URLContainer urlContainer, String resourcePath,
		List<String> customJsps) {

		Set<String> resourcePaths = urlContainer.getResources(resourcePath);

		if ((resourcePaths == null) || resourcePaths.isEmpty()) {
			return;
		}

		for (String curResourcePath : resourcePaths) {
			if (curResourcePath.endsWith(StringPool.SLASH)) {
				getCustomJsps(urlContainer, curResourcePath, customJsps);
			}
			else {
				String customJsp = curResourcePath;

				customJsp = StringUtil.replace(
					customJsp, StringPool.DOUBLE_SLASH, StringPool.SLASH);

				customJsps.add(customJsp);
			}
		}
	}

	protected String getPortalJsp(String customJsp, String customJspDir) {
		if (Validator.isNull(customJsp) || Validator.isNull(customJspDir)) {
			return null;
		}

		int pos = customJsp.indexOf(customJspDir);

		return customJsp.substring(pos + customJspDir.length());
	}

	protected File getPortalJspBackupFile(File portalJspFile) {
		String fileName = portalJspFile.getName();
		String filePath = portalJspFile.toString();

		int fileNameIndex = fileName.lastIndexOf(CharPool.PERIOD);

		if (fileNameIndex > 0) {
			int filePathIndex = filePath.lastIndexOf(fileName);

			fileName =
				fileName.substring(0, fileNameIndex) + ".portal" +
					fileName.substring(fileNameIndex);

			filePath = filePath.substring(0, filePathIndex) + fileName;
		}
		else {
			filePath += ".portal";
		}

		return new File(filePath);
	}

	protected void initCustomJspBag(
			String contextId, String contextName, CustomJspBag customJspBag)
		throws Exception {

		String customJspDir = customJspBag.getCustomJspDir();
		boolean customJspGlobal = customJspBag.isCustomJspGlobal();
		List<String> customJsps = customJspBag.getCustomJsps();

		String portalWebDir = PortalUtil.getPortalWebDir();

		for (String customJsp : customJsps) {
			String portalJsp = getPortalJsp(customJsp, customJspDir);

			if (customJspGlobal) {
				File portalJspFile = new File(portalWebDir + portalJsp);
				File portalJspBackupFile = getPortalJspBackupFile(
					portalJspFile);

				if (portalJspFile.exists() && !portalJspBackupFile.exists()) {
					FileUtil.copyFile(portalJspFile, portalJspBackupFile);
				}
			}
			else {
				portalJsp = CustomJspRegistryUtil.getCustomJspFileName(
					contextId, portalJsp);
			}

			FileUtil.write(
				portalWebDir + portalJsp,
				getCustomJspInputStream(
					customJspBag.getURLContainer(), customJsp));
		}

		if (!customJspGlobal) {
			CustomJspRegistryUtil.registerServletContextName(
				contextId, contextName);
		}
	}

	protected void verifyCustomJsps(String contextId, CustomJspBag customJspBag)
		throws DuplicateCustomJspException {

		Set<String> customJsps = new HashSet<>();

		for (String customJsp : customJspBag.getCustomJsps()) {
			String portalJsp = getPortalJsp(
				customJsp, customJspBag.getCustomJspDir());

			customJsps.add(portalJsp);
		}

		Map<String, String> conflictingCustomJsps = new HashMap<>();

		for (Map.Entry<ServiceReference<CustomJspBag>, CustomJspBag> entry :
				_customJspBagsMap.entrySet()) {

			CustomJspBag currentCustomJspBag = entry.getValue();

			if (!currentCustomJspBag.isCustomJspGlobal()) {
				continue;
			}

			ServiceReference<CustomJspBag> serviceReference = entry.getKey();

			String contextName = GetterUtil.getString(
				serviceReference.getProperty("context.name"));

			List<String> currentCustomJsps =
				currentCustomJspBag.getCustomJsps();

			for (String currentCustomJsp : currentCustomJsps) {
				String currentPortalJsp = getPortalJsp(
					currentCustomJsp, currentCustomJspBag.getCustomJspDir());

				if (customJsps.contains(currentPortalJsp)) {
					conflictingCustomJsps.put(currentPortalJsp, contextName);
				}
			}
		}

		if (conflictingCustomJsps.isEmpty()) {
			return;
		}

		_log.error(contextId + " conflicts with the installed hooks");

		if (_log.isDebugEnabled()) {
			Log log = SanitizerLogWrapper.allowCRLF(_log);

			StringBundler sb = new StringBundler(
				conflictingCustomJsps.size() * 4 + 2);

			sb.append("Colliding JSP files in ");
			sb.append(contextId);
			sb.append(StringPool.NEW_LINE);

			int i = 0;

			for (Map.Entry<String, String> entry :
					conflictingCustomJsps.entrySet()) {

				sb.append(entry.getKey());
				sb.append(" with ");
				sb.append(entry.getValue());

				if ((i + 1) < conflictingCustomJsps.size()) {
					sb.append(StringPool.NEW_LINE);
				}

				i++;
			}

			log.debug(sb.toString());
		}

		throw new DuplicateCustomJspException();
	}

	private CustomJspBagRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(context.id=*)(context.name=*)(objectClass=" +
				CustomJspBag.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new CustomJspBagRegistryUtilServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomJspBagRegistryUtil.class);

	private static final CustomJspBagRegistryUtil _instance =
		new CustomJspBagRegistryUtil();

	private final Map<ServiceReference<CustomJspBag>, CustomJspBag>
		_customJspBagsMap = new ConcurrentHashMap<>();
	private final ServiceTracker<CustomJspBag, CustomJspBag> _serviceTracker;

	private class CustomJspBagRegistryUtilServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<CustomJspBag, CustomJspBag> {

		@Override
		public CustomJspBag addingService(
			ServiceReference<CustomJspBag> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			CustomJspBag customJspBag = registry.getService(serviceReference);

			List<String> customJsps = customJspBag.getCustomJsps();

			if (customJsps.isEmpty()) {
				getCustomJsps(
					customJspBag.getURLContainer(),
					customJspBag.getCustomJspDir(),
					customJspBag.getCustomJsps());

				customJsps = customJspBag.getCustomJsps();

				if (customJsps.isEmpty()) {
					return null;
				}
			}

			if (_log.isDebugEnabled()) {
				StringBundler sb = new StringBundler(customJsps.size() * 2);

				sb.append("Custom JSP files:\n");

				for (int i = 0; i < customJsps.size(); i++) {
					String customJsp = customJsps.get(i);

					sb.append(customJsp);

					if ((i + 1) < customJsps.size()) {
						sb.append(StringPool.NEW_LINE);
					}
				}

				Log log = SanitizerLogWrapper.allowCRLF(_log);

				log.debug(sb.toString());
			}

			String contextId = GetterUtil.getString(
				serviceReference.getProperty("context.id"));

			if (customJspBag.isCustomJspGlobal() &&
				!_customJspBagsMap.isEmpty()) {

				try {
					verifyCustomJsps(contextId, customJspBag);
				}
				catch (DuplicateCustomJspException e) {
					return null;
				}
			}

			_customJspBagsMap.put(serviceReference, customJspBag);

			String contextName = GetterUtil.getString(
				serviceReference.getProperty("context.name"));

			try {
				initCustomJspBag(contextId, contextName, customJspBag);
			}
			catch (Exception e) {
				return null;
			}

			return customJspBag;
		}

		@Override
		public void modifiedService(
			ServiceReference<CustomJspBag> serviceReference,
			CustomJspBag customJspBag) {

			removedService(serviceReference, customJspBag);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<CustomJspBag> serviceReference,
			CustomJspBag customJspBag) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String contextId = GetterUtil.getString(
				serviceReference.getProperty("context.id"));

			for (String customJsp : customJspBag.getCustomJsps()) {
				String customJspDir = customJspBag.getCustomJspDir();

				int pos = customJsp.indexOf(customJspDir);

				String portalJsp = customJsp.substring(
					pos + customJspDir.length());

				if (customJspBag.isCustomJspGlobal()) {
					File portalJspFile = new File(
						PortalUtil.getPortalWebDir() + portalJsp);
					File portalJspBackupFile = getPortalJspBackupFile(
						portalJspFile);

					if (portalJspBackupFile.exists()) {
						try {
							FileUtil.copyFile(
								portalJspBackupFile, portalJspFile);
						}
						catch (IOException e) {
							return;
						}

						portalJspBackupFile.delete();
					}
					else if (portalJspFile.exists()) {
						portalJspFile.delete();
					}
				}
				else {
					portalJsp = CustomJspRegistryUtil.getCustomJspFileName(
						contextId, portalJsp);

					File portalJspFile = new File(
						PortalUtil.getPortalWebDir() + portalJsp);

					if (portalJspFile.exists()) {
						portalJspFile.delete();
					}
				}
			}

			if (!customJspBag.isCustomJspGlobal()) {
				CustomJspRegistryUtil.unregisterServletContextName(contextId);
			}

			_customJspBagsMap.remove(serviceReference);
		}

	}

}