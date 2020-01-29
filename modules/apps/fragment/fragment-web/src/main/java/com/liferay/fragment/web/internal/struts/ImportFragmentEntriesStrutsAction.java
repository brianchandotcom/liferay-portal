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

package com.liferay.fragment.web.internal.struts;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "path=/portal/fragment/import_fragment_entries",
	service = StrutsAction.class
)
public class ImportFragmentEntriesStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		UploadServletRequest uploadServletRequest =
			_portal.getUploadServletRequest(httpServletRequest);

		long groupId = ParamUtil.getLong(uploadServletRequest, "groupId");

		File file = uploadServletRequest.getFile("file");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (file == null) {
			jsonObject.put(
				"error",
				LanguageUtil.get(
					httpServletRequest,
					"the-selected-file-is-not-a-valid-zip-file"));
		}
		else {
			List<String> invalidFragmentEntryNames =
				_fragmentsImporter.importFile(
					themeDisplay.getUserId(), groupId, 0L, file, true);

			jsonObject.put(
				"invalidFragmentEntryNames", invalidFragmentEntryNames);
		}

		ServletResponseUtil.write(httpServletResponse, jsonObject.toString());

		return null;
	}

	@Reference
	private FragmentsImporter _fragmentsImporter;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + FragmentConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}