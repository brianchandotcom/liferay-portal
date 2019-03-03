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

import com.liferay.asset.kernel.exception.DuplicateQueryRuleException;
import com.liferay.asset.kernel.model.AssetQueryRule;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.list.constants.AssetListFormConstants;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MutableRenderParameters;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
		"mvc.command.name=/asset_list/edit_asset_list_entry_filters"
	},
	service = MVCActionCommand.class
)
public class EditAssetListEntryFiltersMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long assetListEntryId = ParamUtil.getLong(
			actionRequest, "assetListEntryId");

		AssetListEntry assetListEntry =
			_assetListEntryService.fetchAssetListEntry(assetListEntryId);

		if (assetListEntry == null) {
			return;
		}

		try {
			UnicodeProperties properties = new UnicodeProperties(true);

			properties.fastLoad(assetListEntry.getTypeSettings());

			updateQueryLogic(actionRequest, properties);

			_assetListEntryService.updateAssetListEntryTypeSettings(
				assetListEntryId, properties.toString());
		}
		catch (DuplicateQueryRuleException dqre) {
			if (_log.isDebugEnabled()) {
				_log.debug(dqre, dqre);
			}

			SessionErrors.add(actionRequest, dqre.getClass(), dqre);

			MutableRenderParameters renderParameters =
				actionResponse.getRenderParameters();

			Map<String, String[]> parameters = actionRequest.getParameterMap();

			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				renderParameters.setValues(entry.getKey(), entry.getValue());
			}

			renderParameters.setValue("mvcPath", "/edit_asset_list_entry.jsp");
			renderParameters.setValue(
				"screenNavigationCategoryKey",
				AssetListFormConstants.ENTRY_KEY_FILTER);
			renderParameters.setValue(
				"screenNavigationEntryKey",
				AssetListFormConstants.ENTRY_KEY_FILTER);
		}
	}

	protected AssetQueryRule getQueryRule(
		ActionRequest actionRequest, int index) {

		boolean contains = ParamUtil.getBoolean(
			actionRequest, "queryContains" + index);
		boolean andOperator = ParamUtil.getBoolean(
			actionRequest, "queryAndOperator" + index);

		String name = ParamUtil.getString(actionRequest, "queryName" + index);

		String[] values = null;

		if (name.equals("assetTags")) {
			values = ParamUtil.getStringValues(
				actionRequest, "queryTagNames" + index);
		}
		else {
			values = StringUtil.split(
				ParamUtil.getString(actionRequest, "queryCategoryIds" + index));
		}

		return new AssetQueryRule(contains, andOperator, name, values);
	}

	protected void updateQueryLogic(
			ActionRequest actionRequest, UnicodeProperties properties)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getSiteGroupId();

		int[] queryRulesIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "queryLogicIndexes"), 0);

		int i = 0;

		List<AssetQueryRule> queryRules = new ArrayList<>();

		for (int queryRulesIndex : queryRulesIndexes) {
			AssetQueryRule queryRule = getQueryRule(
				actionRequest, queryRulesIndex);

			validateQueryRule(userId, groupId, queryRules, queryRule);

			queryRules.add(queryRule);

			properties.put(
				"queryContains" + i, String.valueOf(queryRule.isContains()));
			properties.put(
				"queryAndOperator" + i,
				String.valueOf(queryRule.isAndOperator()));
			properties.put("queryName" + i, queryRule.getName());
			properties.put(
				"queryValues" + i, StringUtil.merge(queryRule.getValues()));

			i++;
		}

		// Clear previous preferences that are now blank

		String value = properties.getProperty("queryValues" + i);

		while (Validator.isNotNull(value)) {
			properties.remove("queryContains" + i);
			properties.remove("queryAndOperator" + i);
			properties.remove("queryName" + i);
			properties.remove("queryValues" + i);

			i++;

			value = properties.getProperty("queryValues" + i);
		}
	}

	protected void validateQueryRule(
			long userId, long groupId, List<AssetQueryRule> queryRules,
			AssetQueryRule queryRule)
		throws Exception {

		String name = queryRule.getName();

		if (name.equals("assetTags")) {
			_assetTagLocalService.checkTags(
				userId, groupId, queryRule.getValues());
		}

		if (queryRules.contains(queryRule)) {
			throw new DuplicateQueryRuleException(
				queryRule.isContains(), queryRule.isAndOperator(),
				queryRule.getName());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditAssetListEntryFiltersMVCActionCommand.class);

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

}