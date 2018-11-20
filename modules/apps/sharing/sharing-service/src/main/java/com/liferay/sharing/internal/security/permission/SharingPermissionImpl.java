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

package com.liferay.sharing.internal.security.permission;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.security.permission.SharingPermissionChecker;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.Collection;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = SharingPermission.class)
public class SharingPermissionImpl implements SharingPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId, Collection<SharingEntryAction> sharingEntryActions)
		throws PortalException {

		if (!contains(
				permissionChecker, classNameId, classPK, groupId,
				sharingEntryActions)) {

			ClassName className = _classNameLocalService.fetchByClassNameId(
				classNameId);

			String resourceName = String.valueOf(classNameId);

			if (className != null) {
				resourceName = className.getClassName();
			}

			Stream<SharingEntryAction> sharingEntryActionStream =
				sharingEntryActions.stream();

			String[] actionIds = sharingEntryActionStream.map(
				SharingEntryAction::getActionId
			).toArray(
				String[]::new
			);

			throw new PrincipalException.MustHavePermission(
				permissionChecker.getUserId(), resourceName, classPK,
				actionIds);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId, Collection<SharingEntryAction> sharingEntryActions)
		throws PortalException {

		SharingPermissionChecker sharingPermissionChecker =
			_serviceTrackerMap.getService(classNameId);

		if (sharingPermissionChecker == null) {
			throw new PrincipalException(
				"sharing permission checker is null for class name ID " +
					classNameId);
		}

		if (sharingPermissionChecker.hasPermission(
				permissionChecker, classPK, groupId, sharingEntryActions)) {

			return true;
		}

		if (_sharingEntryLocalService == null) {
			return false;
		}

		Stream<SharingEntryAction> sharingEntryActionStream =
			sharingEntryActions.stream();

		if (sharingEntryActionStream.allMatch(
				sharingEntryAction ->
					_sharingEntryLocalService.hasShareableSharingPermission(
						permissionChecker.getUserId(), classNameId, classPK,
						sharingEntryAction))) {

			return true;
		}

		return false;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SharingPermissionChecker.class,
			"(model.class.name=*)",
			(serviceReference, emitter) -> emitter.emit(
				_classNameLocalService.getClassNameId(
					(String)serviceReference.getProperty("model.class.name"))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerMap<Long, SharingPermissionChecker>
		_serviceTrackerMap;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC
	)
	private volatile SharingEntryLocalService _sharingEntryLocalService;

}