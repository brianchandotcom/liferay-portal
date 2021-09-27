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

package com.liferay.template.internal.model.listener;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.template.internal.info.item.renderer.TemplateEntryInfoItemRenderer;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	service = {ModelListener.class, PortalInstanceLifecycleListener.class}
)
public class TemplateEntryModelListener
	extends BaseModelListener<TemplateEntry>
	implements PortalInstanceLifecycleListener {

	@Override
	public void onAfterCreate(TemplateEntry templateEntry)
		throws ModelListenerException {

		Map<Long, ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrations.computeIfAbsent(
				templateEntry.getCompanyId(), key -> new HashMap<>());

		serviceRegistrations.put(
			templateEntry.getTemplateEntryId(),
			_bundleContext.registerService(
				InfoItemRenderer.class,
				new TemplateEntryInfoItemRenderer<>(
					_infoItemServiceTracker, templateEntry),
				HashMapDictionaryBuilder.<String, Object>put(
					"item.class.name", templateEntry.getInfoItemClassName()
				).build()));
	}

	@Override
	public void onBeforeRemove(TemplateEntry templateEntry)
		throws ModelListenerException {

		Map<Long, ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrations.get(templateEntry.getCompanyId());

		if (MapUtil.isNotEmpty(serviceRegistrations)) {
			ServiceRegistration<?> serviceRegistration =
				serviceRegistrations.remove(templateEntry.getTemplateEntryId());

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	@Override
	public void portalInstanceRegistered(Company company) {
		Map<Long, ServiceRegistration<?>> serviceRegistrations =
			new HashMap<>();

		List<Group> companyGroups = _groupLocalService.getCompanyGroups(
			company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<TemplateEntry> templateEntries =
			_templateEntryLocalService.getTemplateEntries(
				ListUtil.toLongArray(companyGroups, Group::getGroupId));

		for (TemplateEntry templateEntry : templateEntries) {
			serviceRegistrations.put(
				templateEntry.getTemplateEntryId(),
				_bundleContext.registerService(
					InfoItemRenderer.class,
					new TemplateEntryInfoItemRenderer<>(
						_infoItemServiceTracker, templateEntry),
					HashMapDictionaryBuilder.<String, Object>put(
						"item.class.name", templateEntry.getInfoItemClassName()
					).build()));
		}

		if (MapUtil.isNotEmpty(serviceRegistrations)) {
			_serviceRegistrations.put(
				company.getCompanyId(), serviceRegistrations);
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) {
		_unregisterCompanyTemplateEntries(company.getCompanyId());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_companyLocalService.forEachCompanyId(
			TemplateEntryModelListener.this::_unregisterCompanyTemplateEntries);
	}

	private void _unregisterCompanyTemplateEntries(long companyId) {
		Map<Long, ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrations.remove(companyId);

		if (serviceRegistrations == null) {
			return;
		}

		for (Map.Entry<Long, ServiceRegistration<?>> entry :
				serviceRegistrations.entrySet()) {

			ServiceRegistration<?> serviceRegistration = entry.getValue();

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	private final Map<Long, Map<Long, ServiceRegistration<?>>>
		_serviceRegistrations = Collections.synchronizedMap(
			new LinkedHashMap<>());

	@Reference
	private TemplateEntryLocalService _templateEntryLocalService;

}