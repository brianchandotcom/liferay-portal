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

package com.liferay.document.library.web.internal.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.web.internal.info.collection.provider.DLFileEntryTypeRelatedInfoCollectionProvider;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

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
 * @author Jürgen Kappler
 */
@Component(
	enabled = false, immediate = true,
	service = {ModelListener.class, PortalInstanceLifecycleListener.class}
)
public class DLFileEntryTypeModelListener
	extends BaseModelListener<DLFileEntryType>
	implements PortalInstanceLifecycleListener {

	@Override
	public void onAfterCreate(DLFileEntryType dlFileEntryType)
		throws ModelListenerException {

		Map<Long, ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrations.computeIfAbsent(
				dlFileEntryType.getCompanyId(), key -> new HashMap<>());

		serviceRegistrations.put(
			dlFileEntryType.getFileEntryTypeId(),
			_bundleContext.registerService(
				RelatedInfoItemCollectionProvider.class,
				new DLFileEntryTypeRelatedInfoCollectionProvider(
					_dlAppLocalService, dlFileEntryType),
				null));
	}

	@Override
	public void onBeforeRemove(DLFileEntryType dlFileEntryType)
		throws ModelListenerException {

		Map<Long, ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrations.get(dlFileEntryType.getCompanyId());

		if (MapUtil.isNotEmpty(serviceRegistrations)) {
			ServiceRegistration<?> serviceRegistration =
				serviceRegistrations.remove(
					dlFileEntryType.getFileEntryTypeId());

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	@Override
	public void portalInstanceRegistered(Company company) {
		_registerCompanyDLFileEntryTypes(company);
		_registerDefaultDLFileEntryType();
	}

	@Override
	public void portalInstanceUnregistered(Company company) {
		_unregisterCompanyDLFileEntryTypes(company.getCompanyId());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_unregisterCompanyDLFileEntryTypes(0);

		_companyLocalService.forEachCompanyId(
			companyId -> _unregisterCompanyDLFileEntryTypes(companyId));
	}

	private void _registerCompanyDLFileEntryTypes(Company company) {
		Map<Long, ServiceRegistration<?>> serviceRegistrations =
			new HashMap<>();

		List<Group> companyGroups = _groupLocalService.getCompanyGroups(
			company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<DLFileEntryType> dlFileEntryTypes =
			_dlFileEntryTypeLocalService.getFileEntryTypes(
				ListUtil.toLongArray(companyGroups, Group::getGroupId));

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			serviceRegistrations.put(
				dlFileEntryType.getFileEntryTypeId(),
				_bundleContext.registerService(
					RelatedInfoItemCollectionProvider.class,
					new DLFileEntryTypeRelatedInfoCollectionProvider(
						_dlAppLocalService, dlFileEntryType),
					null));
		}

		if (MapUtil.isNotEmpty(serviceRegistrations)) {
			_serviceRegistrations.put(
				company.getCompanyId(), serviceRegistrations);
		}
	}

	private void _registerDefaultDLFileEntryType() {
		if (_serviceRegistrations.containsKey(0L)) {
			return;
		}

		DLFileEntryType basicDocumentDLFileEntryType =
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		_serviceRegistrations.put(
			0L,
			HashMapBuilder.<Long, ServiceRegistration<?>>put(
				basicDocumentDLFileEntryType.getFileEntryTypeId(),
				_bundleContext.registerService(
					RelatedInfoItemCollectionProvider.class,
					new DLFileEntryTypeRelatedInfoCollectionProvider(
						_dlAppLocalService, basicDocumentDLFileEntryType),
					null)
			).build());
	}

	private void _unregisterCompanyDLFileEntryTypes(long companyId) {
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
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private final Map<Long, Map<Long, ServiceRegistration<?>>>
		_serviceRegistrations = Collections.synchronizedMap(
			new LinkedHashMap<>());

}