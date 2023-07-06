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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemActionDetailsProvider;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_info_item_action_error_message"
	},
	service = MVCResourceCommand.class
)
public class GetInfoItemActionErrorMessageMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-169992")) {
			return;
		}

		try {
			InfoItemActionDetailsProvider<Object>
				infoItemActionDetailsProvider =
					_infoItemServiceRegistry.getFirstInfoItemService(
						InfoItemActionDetailsProvider.class,
						_portal.getClassName(
							ParamUtil.getLong(resourceRequest, "classNameId")));

			if (infoItemActionDetailsProvider == null) {
				_writeErrorJSON(resourceRequest, resourceResponse);

				return;
			}

			Map<String, String> messageMap = new HashMap<>();

			Map<Locale, String> actionErrorMessageMap =
				infoItemActionDetailsProvider.getInfoItemActionErrorMessageMap(
					ParamUtil.getString(resourceRequest, "fieldId"));

			for (Map.Entry<Locale, String> entry :
					actionErrorMessageMap.entrySet()) {

				messageMap.put(
					_language.getLanguageId(entry.getKey()),
					_language.get(entry.getKey(), entry.getValue()));
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put("message", messageMap));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			_writeErrorJSON(resourceRequest, resourceResponse);
		}
	}

	private void _writeErrorJSON(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Company company = _portal.getCompany(resourceRequest);

		Map<String, String> errorMap = new HashMap<>();

		for (Locale locale :
				_language.getCompanyAvailableLocales(company.getCompanyId())) {

			errorMap.put(
				_language.getLanguageId(locale),
				_language.get(locale, "your-request-failed-to-complete"));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, JSONUtil.put("error", errorMap));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetInfoItemActionErrorMessageMVCResourceCommand.class);

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}