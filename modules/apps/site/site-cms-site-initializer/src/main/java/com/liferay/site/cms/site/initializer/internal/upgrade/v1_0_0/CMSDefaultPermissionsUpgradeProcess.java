/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.upgrade.v1_0_0;

import com.liferay.depot.model.DepotEntry;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.site.cms.site.initializer.util.CMSDefaultPermissionUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class CMSDefaultPermissionsUpgradeProcess extends UpgradeProcess {

	public CMSDefaultPermissionsUpgradeProcess(
		FilterFactory<Predicate> filterFactory,
		GroupLocalService groupLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryFolderLocalService objectEntryFolderLocalService) {

		_filterFactory = filterFactory;
		_groupLocalService = groupLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryFolderLocalService = objectEntryFolderLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_objectEntryFolderLocalService.getActionableDynamicQuery();

		Set<Long> groupIds = new HashSet<>();

		actionableDynamicQuery.setPerformActionMethod(
			(ObjectEntryFolder objectEntryFolder) -> {
				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.
						fetchObjectDefinitionByExternalReferenceCode(
							"L_CMS_DEFAULT_PERMISSION",
							objectEntryFolder.getCompanyId());

				if (objectDefinition == null) {
					return;
				}

				Group group = _groupLocalService.fetchGroup(
					objectEntryFolder.getGroupId());

				if (group == null) {
					return;
				}

				if (groupIds.add(objectEntryFolder.getGroupId())) {
					_updateCMSDefaultPermissionExternalReferenceCode(group);
				}

				if (Objects.equals(
						objectEntryFolder.getExternalReferenceCode(),
						ObjectEntryFolderConstants.
							EXTERNAL_REFERENCE_CODE_CONTENTS) ||
					Objects.equals(
						objectEntryFolder.getExternalReferenceCode(),
						ObjectEntryFolderConstants.
							EXTERNAL_REFERENCE_CODE_FILES)) {

					return;
				}

				ObjectEntry objectEntry =
					CMSDefaultPermissionUtil.fetchObjectEntry(
						objectEntryFolder.getCompanyId(),
						objectEntryFolder.getUserId(),
						objectEntryFolder.getExternalReferenceCode(),
						objectEntryFolder.getModelClassName(), _filterFactory);

				if (objectEntry != null) {
					return;
				}

				CMSDefaultPermissionUtil.addCMSDefaultPermissions(
					objectEntryFolder, _filterFactory);
			});

		actionableDynamicQuery.performActions();
	}

	private void _updateCMSDefaultPermissionExternalReferenceCode(Group group)
		throws PortalException {

		ObjectEntry objectEntry =
			CMSDefaultPermissionUtil.fetchObjectEntryByDepotGroupId(
				group.getCompanyId(), group.getCreatorUserId(),
				group.getGroupId(), DepotEntry.class.getName(), _filterFactory);

		if (objectEntry == null) {
			return;
		}

		CMSDefaultPermissionUtil.updateClassExternalReferenceCode(
			objectEntry, group.getExternalReferenceCode(),
			group.getCreatorUserId());
	}

	private final FilterFactory<Predicate> _filterFactory;
	private final GroupLocalService _groupLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}