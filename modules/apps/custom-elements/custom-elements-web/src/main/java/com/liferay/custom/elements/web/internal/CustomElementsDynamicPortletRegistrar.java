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

package com.liferay.custom.elements.web.internal;

import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.custom.elements.service.CustomElementsPortletDescriptorLocalService;
import com.liferay.custom.elements.web.internal.portlet.CustomElementsDynamicPortlet;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true, service = CustomElementsDynamicPortletRegistrar.class
)
public class CustomElementsDynamicPortletRegistrar {

	public void registerPortlet(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		_registerPortlet(customElementsPortletDescriptor);
	}

	public void unregisterPortlet(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		_unregisterPortlet(
			customElementsPortletDescriptor.
				getCustomElementsPortletDescriptorId());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		if (_log.isInfoEnabled()) {
			_log.info("Starting custom elements dynamic portlets");
		}

		for (CustomElementsPortletDescriptor customElementsPortletDescriptor :
				_customElementsPortletDescriptorLocalService.
					getCustomElementsPortletDescriptors(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			registerPortlet(customElementsPortletDescriptor);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info("Stopping custom elements dynamic portlets");
		}

		for (long customElementPortletEntryId :
				_customElementsDynamicPortlets.keySet()) {

			_unregisterPortlet(customElementPortletEntryId);
		}
	}

	private void _registerPortlet(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		CustomElementsDynamicPortlet customElementsDynamicPortlet =
			new CustomElementsDynamicPortlet(customElementsPortletDescriptor);

		long customElementsPortletDescriptorId =
			customElementsPortletDescriptor.
				getCustomElementsPortletDescriptorId();

		CustomElementsDynamicPortlet existingCustomElementsDynamicPortlet =
			_customElementsDynamicPortlets.putIfAbsent(
				customElementsPortletDescriptorId,
				customElementsDynamicPortlet);

		if (existingCustomElementsDynamicPortlet != null) {
			throw new IllegalStateException(
				"Custom elements dynamic portlet " +
					customElementsPortletDescriptorId +
						" is already registered");
		}

		customElementsDynamicPortlet.register(_bundleContext);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Started custom elements dynamic portlet " +
					customElementsDynamicPortlet.getName());
		}
	}

	private void _unregisterPortlet(long customElementPortletEntryId) {
		CustomElementsDynamicPortlet customElementsDynamicPortlet =
			_customElementsDynamicPortlets.remove(customElementPortletEntryId);

		if (customElementsDynamicPortlet != null) {
			customElementsDynamicPortlet.unregister();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Stopped custom elements dynamic portlet " +
						customElementsDynamicPortlet.getName());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomElementsDynamicPortletRegistrar.class);

	private BundleContext _bundleContext;
	private final ConcurrentMap<Long, CustomElementsDynamicPortlet>
		_customElementsDynamicPortlets = new ConcurrentHashMap<>();

	@Reference
	private CustomElementsPortletDescriptorLocalService
		_customElementsPortletDescriptorLocalService;

}