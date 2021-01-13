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

package com.liferay.journal.internal.upgrade.v0_0_5;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeJournalArticles extends BaseUpgradePortletId {

	public UpgradeJournalArticles(
		AssetCategoryLocalService assetCategoryLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		GroupLocalService groupLocalService,
		LayoutLocalService layoutLocalService,
		PortletPreferenceValueLocalService portletPreferenceValueLocalService,
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_groupLocalService = groupLocalService;
		_layoutLocalService = layoutLocalService;
		_portletPreferenceValueLocalService =
			portletPreferenceValueLocalService;
		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	protected long getCategoryId(long companyId, String type) throws Exception {
		List<AssetCategory> assetCategories = _assetCategoryLocalService.search(
			companyId, type, new String[0], QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		if (!assetCategories.isEmpty()) {
			AssetCategory assetCategory = assetCategories.get(0);

			return assetCategory.getCategoryId();
		}

		return 0;
	}

	protected PortletPreferences getNewPreferences(
			PortletPreferences oldPortletPreferences, long plid,
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		String ddmStructureKey = oldPortletPreferences.getValue(
			"ddmStructureKey", StringPool.BLANK);
		long groupId = GetterUtil.getLong(
			oldPortletPreferences.getValue("groupId", StringPool.BLANK));
		String orderByCol = oldPortletPreferences.getValue(
			"orderByCol", StringPool.BLANK);
		String orderByType = oldPortletPreferences.getValue(
			"orderByType", StringPool.BLANK);
		int pageDelta = GetterUtil.getInteger(
			oldPortletPreferences.getValue("pageDelta", StringPool.BLANK));
		String pageUrl = oldPortletPreferences.getValue(
			"pageUrl", StringPool.BLANK);
		String portletSetupCss = oldPortletPreferences.getValue(
			"portletSetupCss", StringPool.BLANK);
		String type = oldPortletPreferences.getValue("type", StringPool.BLANK);

		PortletPreferences newPortletPreferences = new PortletPreferencesImpl();

		newPortletPreferences.setValue(
			"anyAssetType",
			String.valueOf(
				PortalUtil.getClassNameId(JournalArticle.class.getName())));

		Layout layout = _layoutLocalService.getLayout(plid);

		long structureId = getStructureId(
			layout.getCompanyId(), layout.getGroupId(), ddmStructureKey);

		if (structureId > 0) {
			newPortletPreferences.setValue(
				"anyClassTypeJournalArticleAssetRendererFactory",
				String.valueOf(structureId));
		}

		String assetLinkBehavior = "showFullContent";

		if (pageUrl.equals("viewInContext")) {
			assetLinkBehavior = "viewInPortlet";
		}

		newPortletPreferences.setValue("assetLinkBehavior", assetLinkBehavior);

		if (structureId > 0) {
			newPortletPreferences.setValue(
				"classTypeIds", String.valueOf(structureId));
		}

		newPortletPreferences.setValue("delta", String.valueOf(pageDelta));
		newPortletPreferences.setValue("displayStyle", "table");
		newPortletPreferences.setValue("metadataFields", "publish-date,author");
		newPortletPreferences.setValue("orderByColumn1", orderByCol);
		newPortletPreferences.setValue("orderByType1", orderByType);
		newPortletPreferences.setValue("paginationType", "none");

		portletSetupCss = StringUtil.replace(
			portletSetupCss,
			new String[] {
				"#p_p_id_" + oldRootPortletId, "#portlet_" + oldRootPortletId
			},
			new String[] {
				"#p_p_id_" + newRootPortletId, "#portlet_" + newRootPortletId
			});

		newPortletPreferences.setValue("portletSetupCss", portletSetupCss);

		long categoryId = getCategoryId(layout.getCompanyId(), type);

		if (categoryId > 0) {
			newPortletPreferences.setValue(
				"queryAndOperator0", Boolean.TRUE.toString());
			newPortletPreferences.setValue(
				"queryContains0", Boolean.TRUE.toString());
			newPortletPreferences.setValue("queryName0", "assetCategories");
			newPortletPreferences.setValue(
				"queryValues0", String.valueOf(categoryId));
		}

		newPortletPreferences.setValue(
			"showAddContentButton", Boolean.FALSE.toString());

		String groupName = String.valueOf(groupId);

		if (groupId == layout.getGroupId()) {
			groupName = "default";
		}

		newPortletPreferences.setValue("scopeIds", "Group_" + groupName);

		return newPortletPreferences;
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{_PORTLET_ID_JOURNAL_CONTENT_LIST, _PORTLET_ID_ASSET_PUBLISHER}
		};
	}

	protected long getStructureId(
			long companyId, long groupId, String ddmStructureKey)
		throws Exception {

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			groupId, PortalUtil.getClassNameId(JournalArticle.class.getName()),
			ddmStructureKey);

		if (ddmStructure == null) {
			Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

			_ddmStructureLocalService.fetchStructure(
				companyGroup.getGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class.getName()),
				ddmStructureKey);
		}

		if (ddmStructure != null) {
			return ddmStructure.getStructureId();
		}

		return 0;
	}

	@Override
	protected void updateInstanceablePortletPreferences(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		StringBundler sb = new StringBundler(11);

		sb.append("select distinct PortletPreferences.portletPreferencesId ");
		sb.append("from PortletPreferences inner join PortletPreferenceValue ");
		sb.append("on PortletPreferenceValue.portletPreferencesId = ");
		sb.append("PortletPreferences.portletPreferencesId where portletId = ");
		sb.append("'");
		sb.append(oldRootPortletId);
		sb.append("' OR portletId like '");
		sb.append(oldRootPortletId);
		sb.append("_INSTANCE_%' OR portletId like '");
		sb.append(oldRootPortletId);
		sb.append("_USER_%_INSTANCE_%'");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");

				com.liferay.portal.kernel.model.PortletPreferences
					portletPreferences =
						_portletPreferencesLocalService.getPortletPreferences(
							portletPreferencesId);

				long plid = portletPreferences.getPlid();

				String portletId = portletPreferences.getPortletId();

				long userId = PortletIdCodec.decodeUserId(portletId);
				String instanceId = PortletIdCodec.decodeInstanceId(portletId);

				String newPortletId = PortletIdCodec.encode(
					_PORTLET_ID_ASSET_PUBLISHER, userId, instanceId);

				portletPreferences.setPortletId(newPortletId);

				portletPreferences =
					_portletPreferencesLocalService.updatePortletPreferences(
						portletPreferences);

				PortletPreferences oldPortletPreferences =
					_portletPreferenceValueLocalService.getPreferences(
						portletPreferences);

				PortletPreferences newPreferences = getNewPreferences(
					oldPortletPreferences, plid, oldRootPortletId,
					newRootPortletId);

				_portletPreferencesLocalService.updatePreferences(
					portletPreferences.getOwnerId(),
					portletPreferences.getOwnerType(),
					portletPreferences.getPlid(),
					portletPreferences.getPortletId(), newPreferences);
			}
		}
	}

	@Override
	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		try {
			updateResourcePermission(oldRootPortletId, newRootPortletId, true);

			updateInstanceablePortletPreferences(
				oldRootPortletId, newRootPortletId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}
	}

	private static final String _PORTLET_ID_ASSET_PUBLISHER =
		"com_liferay_asset_publisher_web_AssetPublisherPortlet";

	private static final String _PORTLET_ID_JOURNAL_CONTENT_LIST = "62";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeJournalArticles.class);

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final GroupLocalService _groupLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private final PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

}