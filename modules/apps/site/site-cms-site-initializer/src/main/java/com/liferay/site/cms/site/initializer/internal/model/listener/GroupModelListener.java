/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener;

import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterCreate(Group group) throws ModelListenerException {
		try {
			_onAfterCreate(group);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		try {
			_onBeforeRemove(group);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private void _onAfterCreate(Group group) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				group.getCompanyId(), "LPD-17564") ||
			!group.isDepot()) {

			return;
		}

		// TODO We need to protect L_ in ObjectEntryFolderLocalServiceImpl
		// via a thread local

		_objectEntryFolderLocalService.addObjectEntryFolder(
			"L_CONTENTS", group.getCreatorUserId(), group.getGroupId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, "Contents"
			).build(),
			"Contents", ServiceContextThreadLocal.getServiceContext());
		_objectEntryFolderLocalService.addObjectEntryFolder(
			"L_FILES", group.getCreatorUserId(), group.getGroupId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, "Files"
			).build(),
			"Files", ServiceContextThreadLocal.getServiceContext());
	}

	private void _onBeforeRemove(Group group) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				group.getCompanyId(), "LPD-17564") ||
			!group.isDepot()) {

			return;
		}

		_objectEntryFolderLocalService.deleteObjectEntryFolder(
			"L_CONTENTS", group.getGroupId(), group.getCompanyId());
		_objectEntryFolderLocalService.deleteObjectEntryFolder(
			"L_FILES", group.getGroupId(), group.getCompanyId());
	}

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}