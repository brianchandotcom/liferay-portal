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

package com.liferay.sharing.service.impl;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingPermissionChecker;
import com.liferay.sharing.service.base.SharingEntryServiceBaseImpl;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Sergio González
 */
public class SharingEntryServiceImpl extends SharingEntryServiceBaseImpl {

	@Override
	public SharingEntry addOrUpdateSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			Collection<SharingEntryActionKey> sharingEntryActionKeys,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		_checkSharingPermission(
			getUserId(), classNameId, classPK, groupId, sharingEntryActionKeys);

		return sharingEntryLocalService.addOrUpdateSharingEntry(
			getUserId(), toUserId, classNameId, classPK, groupId, shareable,
			sharingEntryActionKeys, expirationDate, serviceContext);
	}

	@Override
	public SharingEntry addSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			Collection<SharingEntryActionKey> sharingEntryActionKeys,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		_checkSharingPermission(
			getUserId(), classNameId, classPK, groupId, sharingEntryActionKeys);

		return sharingEntryLocalService.addSharingEntry(
			getUserId(), toUserId, classNameId, classPK, groupId, shareable,
			sharingEntryActionKeys, expirationDate, serviceContext);
	}

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		Bundle bundle = FrameworkUtil.getBundle(SharingEntryServiceImpl.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SharingPermissionChecker.class,
			"(model.class.name=*)",
			(serviceReference, emitter) -> {
				emitter.emit(
					classNameLocalService.getClassNameId(
						(String)serviceReference.getProperty(
							"model.class.name")));
			});
	}

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public void destroy() {
		super.destroy();

		_serviceTrackerMap.close();
	}

	@Override
	public SharingEntry updateSharingEntry(
			long sharingEntryId,
			Collection<SharingEntryActionKey> sharingEntryActionKeys,
			boolean shareable, Date expirationDate,
			ServiceContext serviceContext)
		throws PortalException {

		SharingEntry sharingEntry = sharingEntryPersistence.findByPrimaryKey(
			sharingEntryId);

		_checkSharingPermission(
			getUserId(), sharingEntry.getClassNameId(),
			sharingEntry.getClassPK(), sharingEntry.getGroupId(),
			sharingEntryActionKeys);

		return sharingEntryLocalService.updateSharingEntry(
			sharingEntryId, sharingEntryActionKeys, shareable, expirationDate,
			serviceContext);
	}

	@ServiceReference(type = ClassNameLocalService.class)
	protected ClassNameLocalService classNameLocalService;

	private void _checkSharingPermission(
			long fromUserId, long classNameId, long classPK, long groupId,
			Collection<SharingEntryActionKey> sharingEntryActionKeys)
		throws PortalException {

		SharingPermissionChecker sharingPermissionChecker =
			_serviceTrackerMap.getService(classNameId);

		if (sharingPermissionChecker == null) {
			throw new PrincipalException(
				"sharing permission checker is null for class name id " +
					classNameId);
		}

		PermissionChecker permissionChecker = getPermissionChecker();

		if (sharingPermissionChecker.hasPermission(
				permissionChecker, classPK, groupId, sharingEntryActionKeys)) {

			return;
		}

		Stream<SharingEntryActionKey> sharingEntryActionKeyStream =
			sharingEntryActionKeys.stream();

		if (sharingEntryActionKeyStream.allMatch(
				sharingEntryActionKey ->
					sharingEntryLocalService.hasShareableSharingPermission(
						permissionChecker.getUserId(), classNameId, classPK,
						sharingEntryActionKey))) {

			return;
		}

		ClassName className = classNameLocalService.fetchByClassNameId(
			classNameId);

		String resourceName = String.valueOf(classNameId);

		if (className != null) {
			resourceName = className.getClassName();
		}

		sharingEntryActionKeyStream = sharingEntryActionKeys.stream();

		String[] actionIds = sharingEntryActionKeyStream.map(
			SharingEntryActionKey::getActionId
		).toArray(
			String[]::new
		);

		throw new PrincipalException.MustHavePermission(
			fromUserId, resourceName, classPK, actionIds);
	}

	private ServiceTrackerMap<Long, SharingPermissionChecker>
		_serviceTrackerMap;

}