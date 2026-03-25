/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.site.cms.site.initializer.util.CMSDefaultPermissionUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = ModelListener.class)
public class ResourcePermissionModelListener
	extends BaseModelListener<ResourcePermission> {

	@Override
	public void onAfterCreate(ResourcePermission resourcePermission)
		throws ModelListenerException {

		try {
			_onAfterCreate(resourcePermission);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onAfterUpdate(
			ResourcePermission originalResourcePermission,
			ResourcePermission resourcePermission)
		throws ModelListenerException {

		try {
			_onAfterUpdate(originalResourcePermission, resourcePermission);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private boolean _isObjectEntryFolderPermission(
		ResourcePermission resourcePermission) {

		if (!FeatureFlagManagerUtil.isEnabled(
				resourcePermission.getCompanyId(), "LPD-17564") ||
			(resourcePermission.getScope() !=
				ResourceConstants.SCOPE_INDIVIDUAL)) {

			return false;
		}

		if (Objects.equals(
				resourcePermission.getName(), DepotEntry.class.getName()) ||
			Objects.equals(
				resourcePermission.getName(),
				ObjectEntryFolder.class.getName())) {

			return true;
		}

		return false;
	}

	private void _onAfterCreate(ResourcePermission resourcePermission)
		throws PortalException {

		if (!_isObjectEntryFolderPermission(resourcePermission) ||
			!resourcePermission.hasActionId(ActionKeys.PERMISSIONS)) {

			return;
		}

		_setResourcePermissions(
			new String[] {"DELETE", "UPDATE", "VIEW"}, resourcePermission);
	}

	private void _onAfterUpdate(
			ResourcePermission originalResourcePermission,
			ResourcePermission resourcePermission)
		throws PortalException {

		if (!_isObjectEntryFolderPermission(resourcePermission)) {
			return;
		}

		boolean hasPermissions = resourcePermission.hasActionId(
			ActionKeys.PERMISSIONS);

		if (hasPermissions == originalResourcePermission.hasActionId(
				ActionKeys.PERMISSIONS)) {

			return;
		}

		if (hasPermissions) {
			_setResourcePermissions(
				new String[] {"DELETE", "UPDATE", "VIEW"}, resourcePermission);
		}
		else {
			_setResourcePermissions(new String[0], resourcePermission);
		}
	}

	private void _setResourcePermissions(
			String[] actionIds, ResourcePermission resourcePermission)
		throws PortalException {

		ObjectEntry objectEntry = null;

		if (Objects.equals(
				resourcePermission.getName(),
				ObjectEntryFolder.class.getName())) {

			ObjectEntryFolder objectEntryFolder =
				_objectEntryFolderLocalService.fetchObjectEntryFolder(
					resourcePermission.getPrimKeyId());

			if (objectEntryFolder == null) {
				return;
			}

			objectEntry = CMSDefaultPermissionUtil.fetchObjectEntry(
				objectEntryFolder.getCompanyId(), objectEntryFolder.getUserId(),
				objectEntryFolder.getExternalReferenceCode(),
				ObjectEntryFolder.class.getName(), _filterFactory);
		}
		else {
			DepotEntry depotEntry = _depotEntryLocalService.fetchDepotEntry(
				resourcePermission.getPrimKeyId());

			if ((depotEntry == null) ||
				(depotEntry.getType() != DepotConstants.TYPE_SPACE)) {

				return;
			}

			Group group = depotEntry.getGroup();

			objectEntry = CMSDefaultPermissionUtil.fetchObjectEntry(
				depotEntry.getCompanyId(), depotEntry.getUserId(),
				group.getExternalReferenceCode(), DepotEntry.class.getName(),
				_filterFactory);
		}

		if (objectEntry == null) {
			return;
		}

		_resourcePermissionLocalService.setResourcePermissions(
			objectEntry.getCompanyId(), objectEntry.getModelClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntry.getObjectEntryId()),
			resourcePermission.getRoleId(), actionIds);
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}