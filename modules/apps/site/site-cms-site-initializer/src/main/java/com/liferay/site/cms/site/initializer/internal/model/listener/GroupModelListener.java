/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.site.cms.site.initializer.util.CMSDefaultPermissionUtil;
import com.liferay.trash.TrashHelper;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterUpdate(Group originalGroup, Group group)
		throws ModelListenerException {

		try {
			_onAfterUpdate(originalGroup, group);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private Long[] _getDepotGroupIds(long companyId) {
		return TransformUtil.transformToArray(
			_depotEntryLocalService.getDepotEntries(
				companyId, DepotConstants.TYPE_SPACE),
			depotEntry -> {
				Group group = _groupLocalService.fetchGroup(
					depotEntry.getGroupId());

				if ((group == null) || !_trashHelper.isTrashEnabled(group)) {
					return null;
				}

				return group.getGroupId();
			},
			Long.class);
	}

	private void _onAfterUpdate(Group originalGroup, Group group)
		throws Exception {

		if (!group.isDepot()) {
			return;
		}

		DepotEntry depotEntry = _depotEntryLocalService.fetchGroupDepotEntry(
			group.getGroupId());

		if ((depotEntry == null) ||
			(depotEntry.getType() != DepotConstants.TYPE_SPACE) ||
			!FeatureFlagManagerUtil.isEnabled(
				group.getCompanyId(), "LPD-17564")) {

			return;
		}

		String externalReferenceCode = group.getExternalReferenceCode();
		String originalExternalReferenceCode =
			originalGroup.getExternalReferenceCode();

		if (!Objects.equals(
				externalReferenceCode, originalExternalReferenceCode)) {

			_updateCMSDefaultPermissionObjectEntry(
				externalReferenceCode, group, originalExternalReferenceCode);
		}

		if (Objects.equals(
				originalGroup.getTypeSettingsProperty("trashEnabled"),
				group.getTypeSettingsProperty("trashEnabled"))) {

			return;
		}

		Group siteGroup = _groupLocalService.fetchGroup(
			group.getCompanyId(), GroupConstants.CMS);

		if (siteGroup == null) {
			return;
		}

		Long[] groupIds = _getDepotGroupIds(group.getCompanyId());

		if (groupIds.length > 0) {
			_updateRecycleBinLayout(siteGroup, false);
		}
		else {
			_updateRecycleBinLayout(siteGroup, true);
		}
	}

	private void _updateCMSDefaultPermissionObjectEntry(
			String externalReferenceCode, Group group,
			String originalExternalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = CMSDefaultPermissionUtil.fetchObjectEntry(
			group.getCompanyId(), group.getCreatorUserId(),
			originalExternalReferenceCode, DepotEntry.class.getName(),
			_filterFactory);

		if (objectEntry == null) {
			return;
		}

		Map<String, Serializable> values = objectEntry.getValues();

		CMSDefaultPermissionUtil.addOrUpdateObjectEntry(
			objectEntry.getExternalReferenceCode(), group.getCompanyId(),
			group.getCreatorUserId(), externalReferenceCode,
			DepotEntry.class.getName(),
			_jsonFactory.createJSONObject(
				GetterUtil.getString(values.get("defaultPermissions"), "{}")),
			GetterUtil.getLong(values.get("depotGroupId")),
			GetterUtil.getString(values.get("treePath")));
	}

	private void _updateRecycleBinLayout(Group group, boolean hidden)
		throws Exception {

		Layout layout = _layoutLocalService.getLayoutByFriendlyURL(
			group.getGroupId(), false, "/recycle-bin");

		if (layout.isHidden() == hidden) {
			return;
		}

		layout.setHidden(hidden);

		_layoutLocalService.updateLayout(layout);
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private TrashHelper _trashHelper;

}