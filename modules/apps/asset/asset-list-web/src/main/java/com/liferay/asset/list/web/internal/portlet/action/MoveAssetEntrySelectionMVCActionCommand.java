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

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.web.internal.contants.AssetListSelectionConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
		"mvc.command.name=/asset_list/move_asset_entry_selection"
	},
	service = MVCActionCommand.class
)
public class MoveAssetEntrySelectionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long assetListEntryId = ParamUtil.getLong(
			actionRequest, "assetListEntryId");

		int assetEntryOrder = ParamUtil.getInteger(
			actionRequest, "assetEntryOrder");

		String moveDirection = ParamUtil.getString(
			actionRequest, "moveDirection");

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(assetListEntryId);

		if (assetListEntry != null) {
			UnicodeProperties typeSettingsProperties = new UnicodeProperties(
				true);

			typeSettingsProperties.fastLoad(assetListEntry.getTypeSettings());

			if (Objects.equals(
					moveDirection, AssetListSelectionConstants.MOVE_DOWN)) {

				_moveSelectionDown(typeSettingsProperties, assetEntryOrder);
			}
			else if (Objects.equals(
						moveDirection, AssetListSelectionConstants.MOVE_UP)) {

				_moveSelectionUp(typeSettingsProperties, assetEntryOrder);
			}

			assetListEntry.setTypeSettings(typeSettingsProperties.toString());

			_assetListEntryLocalService.updateAssetListEntry(assetListEntry);
		}
	}

	private List<String> _getAssetEntryXmls(
		UnicodeProperties typeSettingsProperties) {

		String assetEntryXmlProperty = typeSettingsProperties.getProperty(
			"assetEntryXml");

		List<String> assetEntryXmls = new ArrayList<>();

		if (Validator.isNotNull(assetEntryXmlProperty)) {
			assetEntryXmls = StringUtil.split(assetEntryXmlProperty);
		}

		return assetEntryXmls;
	}

	private void _moveSelectionDown(
		UnicodeProperties typeSettingsProperties, int assetEntryOrder) {

		List<String> assetEntryXmls = _getAssetEntryXmls(
			typeSettingsProperties);

		if ((assetEntryOrder >= (assetEntryXmls.size() - 1)) ||
			(assetEntryOrder < 0)) {

			return;
		}

		Collections.swap(assetEntryXmls, assetEntryOrder, assetEntryOrder + 1);

		typeSettingsProperties.put(
			"assetEntryXml", String.join(StringPool.COMMA, assetEntryXmls));
	}

	private void _moveSelectionUp(
		UnicodeProperties typeSettingsProperties, int assetEntryOrder) {

		List<String> assetEntryXmls = _getAssetEntryXmls(
			typeSettingsProperties);

		if ((assetEntryOrder >= assetEntryXmls.size()) ||
			(assetEntryOrder <= 0)) {

			return;
		}

		Collections.swap(assetEntryXmls, assetEntryOrder - 1, assetEntryOrder);

		typeSettingsProperties.put(
			"assetEntryXml", String.join(StringPool.COMMA, assetEntryXmls));
	}

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

}