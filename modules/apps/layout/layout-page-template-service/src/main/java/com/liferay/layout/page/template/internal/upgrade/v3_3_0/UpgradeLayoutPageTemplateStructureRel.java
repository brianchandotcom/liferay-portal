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

package com.liferay.layout.page.template.internal.upgrade.v3_3_0;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.internal.upgrade.v3_3_0.util.EditableValuesTransformerUtil;
import com.liferay.layout.page.template.util.LayoutDataConverter;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Optional;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeLayoutPageTemplateStructureRel extends UpgradeProcess {

	public UpgradeLayoutPageTemplateStructureRel(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeLayoutPageTemplateStructureRel();
	}

	private Optional<PortletPreferences> _getPortletPreferencesOptional(
			String editableValues, long plid, long segmentsExperienceId)
		throws JSONException {

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		String instanceId = editableValuesJSONObject.getString("instanceId");
		String portletId = editableValuesJSONObject.getString("portletId");

		if (Validator.isNull(instanceId) || Validator.isNull(portletId)) {
			return Optional.empty();
		}

		try {
			return Optional.of(
				_portletPreferencesLocalService.getPortletPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
					PortletIdCodec.encode(
						portletId,
						_setSegmentsExperienceId(
							instanceId, segmentsExperienceId))));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return Optional.empty();
		}
	}

	private String _setSegmentsExperienceId(
		String instanceId, long segmentsExperienceId) {

		if (segmentsExperienceId == SegmentsExperienceConstants.ID_DEFAULT) {
			return instanceId;
		}

		int index = instanceId.indexOf(_SEGMENTS_EXPERIENCE_SEPARATOR);

		if (index == -1) {
			return instanceId + _SEGMENTS_EXPERIENCE_SEPARATOR +
				segmentsExperienceId;
		}

		return instanceId.substring(0, index) + _SEGMENTS_EXPERIENCE_SEPARATOR +
			segmentsExperienceId;
	}

	private String _upgradeLayoutData(String data, long segmentsExperienceId)
		throws PortalException {

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

		if (!LayoutDataConverter.isLatestVersion(dataJSONObject)) {
			data = LayoutDataConverter.convert(data);
		}

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			if (!(layoutStructureItem instanceof FragmentLayoutStructureItem)) {
				continue;
			}

			FragmentLayoutStructureItem fragmentLayoutStructureItem =
				(FragmentLayoutStructureItem)layoutStructureItem;

			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					fragmentLayoutStructureItem.getFragmentEntryLinkId());

			if (fragmentEntryLink == null) {
				continue;
			}

			Optional<PortletPreferences> portletPreferencesOptional =
				_getPortletPreferencesOptional(
					fragmentEntryLink.getEditableValues(),
					fragmentEntryLink.getClassPK(), segmentsExperienceId);

			if (portletPreferencesOptional.isPresent()) {
				fragmentEntryLink.setNamespace(StringUtil.randomId());

				PortletPreferences portletPreferences =
					portletPreferencesOptional.get();

				portletPreferences.setPortletId(
					PortletIdCodec.encode(
						PortletIdCodec.decodePortletName(
							portletPreferences.getPortletId()),
						fragmentEntryLink.getNamespace()));

				try {
					_portletPreferencesLocalService.updatePortletPreferences(
						portletPreferences);
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}

				fragmentEntryLink.setEditableValues(
					EditableValuesTransformerUtil.getEditableValues(
						fragmentEntryLink.getEditableValues(),
						fragmentEntryLink.getNamespace(),
						segmentsExperienceId));
			}
			else {
				fragmentEntryLink.setEditableValues(
					EditableValuesTransformerUtil.getEditableValues(
						fragmentEntryLink.getEditableValues(),
						segmentsExperienceId));
			}

			if (segmentsExperienceId ==
					SegmentsExperienceConstants.ID_DEFAULT) {

				_fragmentEntryLinkLocalService.updateFragmentEntryLink(
					fragmentEntryLink);

				continue;
			}

			FragmentEntryLink newFragmentEntryLink =
				_fragmentEntryLinkLocalService.addFragmentEntryLink(
					fragmentEntryLink.getUserId(),
					fragmentEntryLink.getGroupId(),
					fragmentEntryLink.getOriginalFragmentEntryLinkId(),
					fragmentEntryLink.getFragmentEntryId(),
					segmentsExperienceId, fragmentEntryLink.getClassNameId(),
					fragmentEntryLink.getClassPK(), fragmentEntryLink.getCss(),
					fragmentEntryLink.getHtml(), fragmentEntryLink.getJs(),
					fragmentEntryLink.getConfiguration(),
					fragmentEntryLink.getEditableValues(),
					fragmentEntryLink.getNamespace(),
					fragmentEntryLink.getPosition(),
					fragmentEntryLink.getRendererKey(), new ServiceContext());

			fragmentLayoutStructureItem.setFragmentEntryLinkId(
				newFragmentEntryLink.getFragmentEntryLinkId());
		}

		JSONObject layoutDataJSONObject = layoutStructure.toJSONObject();

		return layoutDataJSONObject.toJSONString();
	}

	private void _upgradeLayoutPageTemplateStructureRel() throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(
				"select lPageTemplateStructureRelId, segmentsExperienceId, " +
					"data_ from LayoutPageTemplateStructureRel order by " +
						"segmentsExperienceId desc");
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutPageTemplateStructureRel set data_ = ? " +
						"where lPageTemplateStructureRelId = ?"))) {

			while (rs.next()) {
				long layoutPageTemplateStructureRelId = rs.getLong(
					"lPageTemplateStructureRelId");

				long segmentsExperienceId = rs.getLong("segmentsExperienceId");

				String data = rs.getString("data_");

				ps.setString(1, _upgradeLayoutData(data, segmentsExperienceId));

				ps.setLong(2, layoutPageTemplateStructureRelId);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	private static final String _SEGMENTS_EXPERIENCE_SEPARATOR =
		"_SEGMENTS_EXPERIENCE_";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeLayoutPageTemplateStructureRel.class);

	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;

}