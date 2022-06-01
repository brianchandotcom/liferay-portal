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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

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
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/edit_fragment_entry"
	},
	service = AopService.class
)
public class EditFragmentEntryMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		FragmentEntry fragmentEntry = _fragmentEntryService.fetchFragmentEntry(
			fragmentEntryId);

		if (fragmentEntry == null) {
			throw new NoSuchEntryException();
		}

		FragmentEntry draftFragmentEntry = null;

		if (fragmentEntry.isDraft()) {
			draftFragmentEntry = fragmentEntry;
		}
		else {
			draftFragmentEntry = _fragmentEntryService.fetchDraft(
				fragmentEntryId);

			if (draftFragmentEntry == null) {
				draftFragmentEntry = _fragmentEntryService.getDraft(
					fragmentEntryId);

				draftFragmentEntry = _fragmentEntryService.updateDraft(
					draftFragmentEntry);
			}
		}

		String name = ParamUtil.getString(actionRequest, "name");
		String css = ParamUtil.getString(actionRequest, "cssContent");
		String html = ParamUtil.getString(actionRequest, "htmlContent");
		String js = ParamUtil.getString(actionRequest, "jsContent");
		boolean cacheable = ParamUtil.getBoolean(actionRequest, "cacheable");
		String configuration = ParamUtil.getString(
			actionRequest, "configurationContent");
		int status = ParamUtil.getInteger(actionRequest, "status");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		draftFragmentEntry.setName(name);
		draftFragmentEntry.setCss(css);
		draftFragmentEntry.setHtml(html);
		draftFragmentEntry.setJs(js);
		draftFragmentEntry.setCacheable(cacheable);
		draftFragmentEntry.setConfiguration(configuration);
		draftFragmentEntry.setStatus(status);

		if (draftFragmentEntry.getType() == FragmentConstants.TYPE_INPUT) {
			String fieldTypes = ParamUtil.getString(
				actionRequest, "fieldTypes");

			JSONArray fieldTypesJSONArray = JSONFactoryUtil.createJSONArray();

			if (Validator.isNotNull(fieldTypes)) {
				fieldTypesJSONArray = JSONFactoryUtil.createJSONArray(
					fieldTypes.split(StringPool.COMMA));
			}

			JSONObject typeOptionsJSONObject = JSONFactoryUtil.createJSONObject(
				draftFragmentEntry.getTypeOptions());

			typeOptionsJSONObject.put("fieldTypes", fieldTypesJSONArray);

			draftFragmentEntry.setTypeOptions(typeOptionsJSONObject.toString());
		}

		try {
			_fragmentEntryService.updateDraft(draftFragmentEntry);
		}
		catch (PortalException portalException) {
			hideDefaultErrorMessage(actionRequest);

			jsonObject.put("error", portalException.getLocalizedMessage());
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	@Reference
	private FragmentEntryService _fragmentEntryService;

}