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

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.constants.LayoutPageTemplateAdminWebKeys;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.util.LayoutPageTemplateImportEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template/import_layout_page_template_entry"
	},
	service = MVCActionCommand.class
)
public class ImportLayoutPageTemplateEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		List<LayoutPageTemplateImportEntry> layoutPageTemplateImportEntries =
			(List)actionRequest.getAttribute(
				LayoutPageTemplateAdminWebKeys.
					LAYOUT_PAGE_TEMPLATE_IMPORT_ENTRIES);

		if (layoutPageTemplateImportEntries == null) {
			return;
		}

		String successMessage = StringPool.BLANK;

		if (ListUtil.isEmpty(layoutPageTemplateImportEntries)) {
			successMessage = LanguageUtil.get(
				_portal.getHttpServletRequest(actionRequest),
				"no-file-was-imported");
		}
		else {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(actionRequest);

			List<String> formattedMessages = new ArrayList<>();

			Stream<LayoutPageTemplateImportEntry> stream =
				layoutPageTemplateImportEntries.stream();

			Map<LayoutPageTemplateImportEntry.Status, Long>
				layoutPageTemplateImportEntriesMap = stream.collect(
					Collectors.groupingBy(
						LayoutPageTemplateImportEntry::getStatus,
						Collectors.counting()));

			for (Map.Entry<LayoutPageTemplateImportEntry.Status, Long>
					entrySet : layoutPageTemplateImportEntriesMap.entrySet()) {

				LayoutPageTemplateImportEntry.Status status = entrySet.getKey();

				formattedMessages.add(
					LanguageUtil.format(
						httpServletRequest, "x-file-x",
						new Object[] {entrySet.getValue(), status.getLabel()}));
			}

			successMessage = LanguageUtil.format(
				httpServletRequest, "import-result-x",
				StringUtil.merge(formattedMessages, StringPool.NEW_LINE));
		}

		SessionMessages.add(actionRequest, "requestProcessed", successMessage);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long layoutPageTemplateCollectionId = ParamUtil.getLong(
			actionRequest, "layoutPageTemplateCollectionId");

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		File file = uploadPortletRequest.getFile("file");

		boolean overwrite = ParamUtil.getBoolean(
			actionRequest, "overwrite", true);

		try {
			List<LayoutPageTemplateImportEntry>
				layoutPageTemplateImportEntries =
					_layoutPageTemplatesImporter.importFile(
						themeDisplay.getUserId(),
						themeDisplay.getScopeGroupId(),
						layoutPageTemplateCollectionId, file, overwrite);

			actionRequest.setAttribute(
				LayoutPageTemplateAdminWebKeys.
					LAYOUT_PAGE_TEMPLATE_IMPORT_ENTRIES,
				layoutPageTemplateImportEntries);

			SessionMessages.add(actionRequest, "success");
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass(), exception);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Reference
	private Portal _portal;

}