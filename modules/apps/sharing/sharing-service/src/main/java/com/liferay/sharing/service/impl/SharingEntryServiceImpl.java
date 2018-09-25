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
 * Provides the remove service for adding and updating sharing entries.
 *
 * <p>
 * This service is responsible of checking the permissions to ensure that a user
 * can share a resource with other users. If the permission check is successful
 * it invokes the local service {@link SharingEntryLocalServiceImpl} to perform
 * the operation in the database. The permission check is done using the
 * interface {@link SharingPermissionChecker} for the respective class name id.
 * </p>
 *
 * @author Sergio González
 * @review
 */
public class SharingEntryServiceImpl extends SharingEntryServiceBaseImpl {

	/**
	 * Adds a sharing entry in the database if it does not exist or it updates
	 * it if it exists.
	 *
	 * @param  toUserId the user id whose resource was shared
	 * @param  classNameId the class name ID of the resource being shared
	 * @param  classPK the primary key of the resource being shared
	 * @param  groupId the primary key of the group containing the resource
	 *         being shared
	 * @param  shareable whether the to user id can share the resource as well
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  expirationDate the date when the sharing entry expires
	 * @return the sharing entry
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if the user does not have permission to share the
	 *         resource or sharing entry actions are invalid (it is empty, it
	 *         doesn't contain {@link SharingEntryAction#VIEW,} or it
	 *         contains a <code>null</code> value) or from user id and to user
	 *         id are the same or the expiration date is a value in the past.
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
	 * Adds a sharing entry in the database.
	 *
	 * @param  toUserId the user id whose resource was shared
	 * @param  classNameId the class name ID of the resource being shared
	 * @param  classPK the primary key of the resource being shared
	 * @param  groupId the primary key of the group containing the resource
	 *         being shared
	 * @param  shareable whether the to user id can share the resource as well
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  expirationDate the date when the sharing entry expires
	 * @return the sharing entry
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if the user does not have permission to share the
	 *         resource or there is already a sharing entry for the same from
	 *         user id, to user id and resource or the sharing entry actions are
	 *         invalid (it is empty, it doesn't contain
	 *         {@link SharingEntryAction#VIEW,} or it contains a
	 *         <code>null</code> value) or from user id and to user id are the
	 *         same or the expiration date is a value in the past.
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

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public void destroy() {
		super.destroy();

		_serviceTrackerMap.close();
	}

	/**
	 * Updates a sharing entry in the database.
	 *
	 * @param  sharingEntryId the primary key of the sharing entry
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  shareable whether the to user id can share the resource as well
	 * @param  expirationDate the date when the sharing entry expires
	 * @return the sharing entry
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if the sharing entry does not exist or sharing
	 *         entry actions are invalid (it is empty, it doesn't contain
	 *         {@link SharingEntryAction#VIEW,} or it contains a
	 *         <code>null</code> value) or the expiration date is a value in the
	 *         past.
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