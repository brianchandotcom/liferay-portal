/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Tina Tian
 * @author Raymond Augé
 */
public class TemplateManagerUtil {

	public static Template getTemplate(
			String templateManagerName, TemplateResource templateResource,
			boolean restricted)
		throws TemplateException {

		TemplateManager templateManager = _getTemplateManagerChecked(
			templateManagerName);

		return templateManager.getTemplate(templateResource, restricted);
	}

	public static TemplateManager getTemplateManager(
		String templateManagerName) {

		return _templateManagers.get(templateManagerName);
	}

	public static Set<String> getTemplateManagerNames() {
		return _templateManagers.keySet();
	}

	public static Map<String, TemplateManager> getTemplateManagers() {
		return Collections.unmodifiableMap(_templateManagers);
	}

	public static boolean hasTemplateManager(String templateManagerName) {
		return _templateManagers.containsKey(templateManagerName);
	}

	private static TemplateManager _getTemplateManagerChecked(
			String templateManagerName)
		throws TemplateException {

		TemplateManager templateManager = _templateManagers.get(
			templateManagerName);

		if (templateManager == null) {
			throw new TemplateException(
				"Unsupported template manager " + templateManagerName);
		}

		return templateManager;
	}

	private TemplateManagerUtil() {
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final ServiceTracker<TemplateManager, TemplateManager>
		_serviceTracker;
	private static final Map<String, TemplateManager> _templateManagers =
		new ConcurrentHashMap<>();

	private static class TemplateManagerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<TemplateManager, TemplateManager> {

		@Override
		public TemplateManager addingService(
			ServiceReference<TemplateManager> serviceReference) {

			TemplateManager templateManager = _bundleContext.getService(
				serviceReference);

			_templateManagers.put(templateManager.getName(), templateManager);

			return templateManager;
		}

		@Override
		public void modifiedService(
			ServiceReference<TemplateManager> serviceReference,
			TemplateManager templateManager) {
		}

		@Override
		public void removedService(
			ServiceReference<TemplateManager> serviceReference,
			TemplateManager templateManager) {

			_bundleContext.ungetService(serviceReference);

			_templateManagers.remove(templateManager.getName());
		}

	}

	static {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			SystemBundleUtil.createFilter(
				"(&(language.type=*)(objectClass=" +
					TemplateManager.class.getName() + "))"),
			new TemplateManagerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

}