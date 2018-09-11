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

package com.liferay.asset.list.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
		"mvc.command.name=/asset_list/add_asset_entry_selection"
	},
	service = MVCActionCommand.class
)
public class AddAssetEntrySelectionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long assetListEntryId = ParamUtil.getLong(
			actionRequest, "assetListEntryId");

		long assetEntryId = ParamUtil.getLong(actionRequest, "assetEntryId");
		String assetEntryType = ParamUtil.getString(
			actionRequest, "assetEntryType");

		AssetListEntry assetListEntry =
			_assetListEntryService.fetchAssetListEntry(assetListEntryId);

		if (assetListEntry != null) {
			UnicodeProperties typeSettingsProperties = new UnicodeProperties(
				true);

			typeSettingsProperties.fastLoad(assetListEntry.getTypeSettings());

			_addSelection(typeSettingsProperties, assetEntryId, assetEntryType);

			_assetListEntryService.updateAssetListEntrySettings(
				assetListEntryId, typeSettingsProperties.toString());
		}
	}

	private void _addSelection(
		UnicodeProperties typeSettingsProperties, long assetEntryId,
		String assetEntryType) {

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetEntryId);

		String assetEntryXmlProperty = typeSettingsProperties.getProperty(
			"assetEntryXml");

		List<String> assetEntryXmls = new ArrayList<>();

		if (Validator.isNotNull(assetEntryXmlProperty)) {
			assetEntryXmls = StringUtil.split(assetEntryXmlProperty);
		}

		String assetEntryXml = _getAssetEntryXml(
			assetEntry.getGroupId(), assetEntryType, assetEntry.getClassUuid());

		if (!assetEntryXmls.contains(assetEntryXml)) {
			assetEntryXmls.add(assetEntryXml);

			typeSettingsProperties.put(
				"assetEntryXml", String.join(StringPool.COMMA, assetEntryXmls));
		}
	}

	private String _getAssetEntryXml(
		long assetEntryGroupId, String assetEntryType, String assetEntryUuid) {

		String xml = null;

		try {
			Document document = SAXReaderUtil.createDocument(StringPool.UTF8);

			Element assetEntryElement = document.addElement("asset-entry");

			Element assetEntryGroupIdElement = assetEntryElement.addElement(
				"asset-entry-group-id");

			assetEntryGroupIdElement.addText(String.valueOf(assetEntryGroupId));

			Element assetEntryTypeElement = assetEntryElement.addElement(
				"asset-entry-type");

			assetEntryTypeElement.addText(assetEntryType);

			Element assetEntryUuidElement = assetEntryElement.addElement(
				"asset-entry-uuid");

			assetEntryUuidElement.addText(assetEntryUuid);

			xml = document.formattedString(StringPool.BLANK);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}
		}

		return xml;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddAssetEntrySelectionMVCActionCommand.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

}