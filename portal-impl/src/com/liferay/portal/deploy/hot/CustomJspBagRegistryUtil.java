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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.CustomJspRegistryUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Peter Fellwock
 */
public class CustomJspBagRegistryUtil {

	public static CustomJspBagRegistryUtil getInstance() {
		return _instance;
	}

	public CustomJspBag get(String servletContextName) {
		return _customJspBagsMap.get(servletContextName);
	}

	public Set<String> getKeys() {
		return _customJspBagsMap.keySet();
	}

	public void register(CustomJspBag customJspBag) {
		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(CustomJspBag.class, customJspBag);
	}

	protected static void getCustomJsps(
		ServletContext servletContext, String resourcePath,
		List<String> customJsps) {

		Set<String> resourcePaths = servletContext.getResourcePaths(
			resourcePath);

		if ((resourcePaths == null) || resourcePaths.isEmpty()) {
			return;
		}

		for (String curResourcePath : resourcePaths) {
			if (curResourcePath.endsWith(StringPool.SLASH)) {
				getCustomJsps(servletContext, curResourcePath, customJsps);
			}
			else {
				String customJsp = curResourcePath;

				customJsp = StringUtil.replace(
					customJsp, StringPool.DOUBLE_SLASH, StringPool.SLASH);

				customJsps.add(customJsp);
			}
		}
	}

	protected InputStream getCustomJspInputStream(
		ServletContext servletContext, String customJsp) {

		return servletContext.getResourceAsStream(customJsp);
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
			String servletContextName, String displayName,
			CustomJspBag customJspBag)
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
					servletContextName, portalJsp);
			}

			FileUtil.write(
				portalWebDir + portalJsp,
				getCustomJspInputStream(
					customJspBag.getServletContext(), customJsp));
		}

		if (!customJspGlobal) {
			CustomJspRegistryUtil.registerServletContextName(
				servletContextName, displayName);
		}
	}

	protected void verifyCustomJsps(
			String servletContextName, CustomJspBag customJspBag)
		throws DuplicateCustomJspException {

		Set<String> customJsps = new HashSet<>();

		for (String customJsp : customJspBag.getCustomJsps()) {
			String portalJsp = getPortalJsp(
				customJsp, customJspBag.getCustomJspDir());

			customJsps.add(portalJsp);
		}

		Map<String, String> conflictingCustomJsps = new HashMap<>();

		for (Map.Entry<String, CustomJspBag> entry :
				_customJspBagsMap.entrySet()) {

			CustomJspBag currentCustomJspBag = entry.getValue();

			if (!currentCustomJspBag.isCustomJspGlobal()) {
				continue;
			}

			String currentServletContextName = entry.getKey();

			List<String> currentCustomJsps =
				currentCustomJspBag.getCustomJsps();

			for (String currentCustomJsp : currentCustomJsps) {
				String currentPortalJsp = getPortalJsp(
					currentCustomJsp, currentCustomJspBag.getCustomJspDir());

				if (customJsps.contains(currentPortalJsp)) {
					conflictingCustomJsps.put(
						currentPortalJsp, currentServletContextName);
				}
			}
		}

		if (conflictingCustomJsps.isEmpty()) {
			return;
		}

		_log.error(servletContextName + " conflicts with the installed hooks");

		if (_log.isDebugEnabled()) {
			Log log = SanitizerLogWrapper.allowCRLF(_log);

			StringBundler sb = new StringBundler(
				conflictingCustomJsps.size() * 4 + 2);

			sb.append("Colliding JSP files in ");
			sb.append(servletContextName);
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
					customJspBag.getServletContext(),
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
					String customJsp = customJsps.get(0);

					sb.append(customJsp);

					if ((i + 1) < customJsps.size()) {
						sb.append(StringPool.NEW_LINE);
					}
				}

				Log log = SanitizerLogWrapper.allowCRLF(_log);

				log.debug(sb.toString());
			}

			String servletContextName = customJspBag.getServletContextName();

			if (
					customJspBag.isCustomJspGlobal() &&
				!_customJspBagsMap.isEmpty() &&
				!_customJspBagsMap.containsKey(servletContextName)) {

				try {
					verifyCustomJsps(servletContextName, customJspBag);
				}
				catch (DuplicateCustomJspException e) {
					return null;
				}
			}

			_customJspBagsMap.put(servletContextName, customJspBag);

			try {
				initCustomJspBag(
					servletContextName, customJspBag.getPluginPackageName(),
					customJspBag);
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
		}

		@Override
		public void removedService(
			ServiceReference<CustomJspBag> serviceReference,
			CustomJspBag customJspBag) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String customJspDir = customJspBag.getCustomJspDir();
			boolean customJspGlobal = customJspBag.isCustomJspGlobal();
			List<String> customJsps = customJspBag.getCustomJsps();

			String portalWebDir = PortalUtil.getPortalWebDir();

			for (String customJsp : customJsps) {
				int pos = customJsp.indexOf(customJspDir);

				String portalJsp = customJsp.substring(
					pos + customJspDir.length());

				if (customJspGlobal) {
					File portalJspFile = new File(portalWebDir + portalJsp);
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
						customJspBag.getServletContextName(), portalJsp);

						File portalJspFile = new File(portalWebDir + portalJsp);

						if (portalJspFile.exists()) {
							portalJspFile.delete();
						}
					}
				}

				if (!customJspGlobal) {
					CustomJspRegistryUtil.unregisterServletContextName(
						customJspBag.getServletContextName());
				}

				_customJspBagsMap.remove(customJspBag.getServletContextName());
			}
		}

		private final ServiceTracker<CustomJspBag, CustomJspBag>
		_serviceTracker;

		private static final Log _log = LogFactoryUtil.getLog(
			CustomJspBagRegistryUtil.class);

		private static final CustomJspBagRegistryUtil _instance =
			new CustomJspBagRegistryUtil();

		private final Map<String, CustomJspBag> _customJspBagsMap =
			new HashMap<>();

		private CustomJspBagRegistryUtil() {

			Registry registry = RegistryUtil.getRegistry();

			_serviceTracker = registry.trackServices(
				CustomJspBag.class,
				new CustomJspBagRegistryUtilServiceTrackerCustomizer());

			_serviceTracker.open();
		}

}