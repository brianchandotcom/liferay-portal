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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.security.permission.SharingPermissionChecker;
import com.liferay.sharing.service.base.SharingEntryServiceBaseImpl;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * Provides the remote service for adding and updating sharing entries. This
 * service checks permissions to ensure that a user can share a resource with
 * other users. If the permission check is successful, this service invokes the
 * local service {@link SharingEntryLocalServiceImpl} to perform the operation
 * in the database. The permission check is done using the interface {@code
 * SharingPermissionChecker} for the respective class name ID.
 *
 * @author Sergio González
 */
public class SharingEntryServiceImpl extends SharingEntryServiceBaseImpl {

	/**
	 * Adds a new sharing entry in the database or updates an existing one.
	 *
	 * @param  toUserId the ID of the user the resource is shared with
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the primary key of the resource
	 * @param  groupId the primary key of the resource's group
	 * @param  shareable whether the user specified by {@code toUserId} can
	 *         share the resource
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  expirationDate the date when the sharing entry expires
	 * @param  serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the user does not have permission to share the
	 *         resource, if the sharing entry actions are invalid (e.g., empty
	 *         don't contain {@code SharingEntryAction#VIEW}, or contain a
	 *         {@code null} value), if the to/from user IDs are the same, or if
	 *         the expiration date is a past value
	 */
	@Override
	public SharingEntry addOrUpdateSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		_checkSharingPermission(
			getUserId(), classNameId, classPK, groupId, sharingEntryActions);

		return sharingEntryLocalService.addOrUpdateSharingEntry(
			getUserId(), toUserId, classNameId, classPK, groupId, shareable,
			sharingEntryActions, expirationDate, serviceContext);
	}

	/**
	 * Adds a new sharing entry in the database.
	 *
	 * @param  toUserId the ID of the user the resource is shared with
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the primary key of the resource
	 * @param  groupId the primary key of the resource's group
	 * @param  shareable whether the user specified by {@code toUserId} can
	 *         share the resource
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  expirationDate the date when the sharing entry expires
	 * @param  serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the user does not have permission to share the
	 *         resource, if a sharing entry already exists for the to/from user
	 *         IDs, if the sharing entry actions are invalid (e.g., empty, do
	 *         not contain {@code SharingEntryAction#VIEW}, or contain a {@code
	 *         null} value), if the to/from user IDs are the same, or if the
	 *         expiration date is a past value
	 */
	@Override
	public SharingEntry addSharingEntry(
			long toUserId, long classNameId, long classPK, long groupId,
			boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		_checkSharingPermission(
			getUserId(), classNameId, classPK, groupId, sharingEntryActions);

		return sharingEntryLocalService.addSharingEntry(
			getUserId(), toUserId, classNameId, classPK, groupId, shareable,
			sharingEntryActions, expirationDate, serviceContext);
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

	@Override
	public SharingEntry deleteSharingEntry(
			long sharingEntryId, ServiceContext serviceContext)
		throws PortalException {

		SharingEntry sharingEntry = sharingEntryLocalService.getSharingEntry(
			sharingEntryId);

		if (getUserId() != sharingEntry.getFromUserId()) {
			throw new PrincipalException.MustHavePermission(
				getUserId(), sharingEntry.getModelClassName(), sharingEntryId,
				ActionKeys.DELETE);
		}

		return sharingEntryLocalService.deleteSharingEntry(sharingEntry);
	}

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public void destroy() {
		super.destroy();

		_serviceTrackerMap.close();
	}

	/**
	 * Updates the sharing entry in the database.
	 *
	 * @param  sharingEntryId the primary key of the sharing entry
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  shareable whether the user the resource is shared with can also
	 *         share it
	 * @param  expirationDate the date when the sharing entry expires
	 * @param  serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the sharing entry does not exist, if the
	 *         sharing entry actions are invalid (e.g., empty, don't contain
	 *         {@code SharingEntryAction#VIEW}, or contain a {@code null}
	 *         value), or if the expiration date is a past value
	 */
	@Override
	public SharingEntry updateSharingEntry(
			long sharingEntryId,
			Collection<SharingEntryAction> sharingEntryActions,
			boolean shareable, Date expirationDate,
			ServiceContext serviceContext)
		throws PortalException {

		SharingEntry sharingEntry = sharingEntryPersistence.findByPrimaryKey(
			sharingEntryId);

		_checkSharingPermission(
			getUserId(), sharingEntry.getClassNameId(),
			sharingEntry.getClassPK(), sharingEntry.getGroupId(),
			sharingEntryActions);

		return sharingEntryLocalService.updateSharingEntry(
			sharingEntryId, sharingEntryActions, shareable, expirationDate,
			serviceContext);
	}

	@ServiceReference(type = ClassNameLocalService.class)
	protected ClassNameLocalService classNameLocalService;

	private void _checkSharingPermission(
			long fromUserId, long classNameId, long classPK, long groupId,
			Collection<SharingEntryAction> sharingEntryActions)
		throws PortalException {

		SharingPermissionChecker sharingPermissionChecker =
			_serviceTrackerMap.getService(classNameId);

		if (sharingPermissionChecker == null) {
			throw new PrincipalException(
				"sharing permission checker is null for class name ID " +
					classNameId);
		}

		PermissionChecker permissionChecker = getPermissionChecker();

		if (sharingPermissionChecker.hasPermission(
				permissionChecker, classPK, groupId, sharingEntryActions)) {

			return;
		}

		Stream<SharingEntryAction> sharingEntryActionStream =
			sharingEntryActions.stream();

		if (sharingEntryActionStream.allMatch(
				sharingEntryAction ->
					sharingEntryLocalService.hasShareableSharingPermission(
						permissionChecker.getUserId(), classNameId, classPK,
						sharingEntryAction))) {

			return;
		}

		ClassName className = classNameLocalService.fetchByClassNameId(
			classNameId);

		String resourceName = String.valueOf(classNameId);

		if (className != null) {
			resourceName = className.getClassName();
		}

		sharingEntryActionStream = sharingEntryActions.stream();

		String[] actionIds = sharingEntryActionStream.map(
			SharingEntryAction::getActionId
		).toArray(
			String[]::new
		);

		throw new PrincipalException.MustHavePermission(
			fromUserId, resourceName, classPK, actionIds);
	}

	private ServiceTrackerMap<Long, SharingPermissionChecker>
		_serviceTrackerMap;

}