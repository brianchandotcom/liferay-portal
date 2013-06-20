/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetcategoryadmin.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyServiceUtil;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 * @author Juan Fernández
 */
public class EditVocabularyAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				jsonObject = updateVocabulary(actionRequest);
			}
		}
		catch (Exception e) {
			jsonObject.putException(e);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		ActionUtil.getVocabulary(renderRequest);

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.asset_category_admin.edit_vocabulary"));
	}

	protected UnicodeProperties getSettingsProperties(
		ActionRequest actionRequest) {

		UnicodeProperties settingsProperties = new UnicodeProperties();

		boolean multiValued = ParamUtil.getBoolean(
			actionRequest, "multiValued");

		settingsProperties.setProperty(
			"multiValued", String.valueOf(multiValued));

		int[] indexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "indexes"), 0);

		Set<Long> selectedClassNameIds = new LinkedHashSet<Long>();
		Set<Long> requiredClassNameIds = new LinkedHashSet<Long>();

		for (int index : indexes) {
			long classNameId = ParamUtil.getLong(
				actionRequest, "classNameId" + index);

			boolean required = ParamUtil.getBoolean(
				actionRequest, "required" + index);

			if (classNameId == AssetCategoryConstants.ALL_CLASS_NAME_IDS) {
				selectedClassNameIds.clear();
				selectedClassNameIds.add(classNameId);

				if (required) {
					requiredClassNameIds.clear();
					requiredClassNameIds.add(classNameId);
				}

				break;
			}
			else {
				selectedClassNameIds.add(classNameId);

				if (required) {
					requiredClassNameIds.add(classNameId);
				}
			}
		}

		settingsProperties.setProperty(
			"selectedClassNameIds", StringUtil.merge(selectedClassNameIds));
		settingsProperties.setProperty(
			"requiredClassNameIds", StringUtil.merge(requiredClassNameIds));

		return settingsProperties;
	}

	protected JSONObject updateVocabulary(ActionRequest actionRequest)
		throws Exception {

		long vocabularyId = ParamUtil.getLong(actionRequest, "vocabularyId");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		UnicodeProperties settingsProperties = getSettingsProperties(
			actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetVocabulary.class.getName(), actionRequest);

		AssetVocabulary vocabulary = null;

		if (vocabularyId <= 0) {

			// Add vocabulary

			vocabulary = AssetVocabularyServiceUtil.addVocabulary(
				StringPool.BLANK, titleMap, descriptionMap,
				settingsProperties.toString(), serviceContext);
		}
		else {

			// Update vocabulary

			vocabulary = AssetVocabularyServiceUtil.updateVocabulary(
				vocabularyId, StringPool.BLANK, titleMap, descriptionMap,
				settingsProperties.toString(), serviceContext);
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("vocabularyId", vocabulary.getVocabularyId());

		return jsonObject;
	}

}