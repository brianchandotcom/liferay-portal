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

import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/add_form_item"
	},
	service = MVCActionCommand.class
)
public class AddFormItemMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		String classTypeId = ParamUtil.getString(actionRequest, "classTypeId");

		String className = _portal.getClassName(classNameId);

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, className);

		if (infoItemFormProvider == null) {
			return;
		}

		InfoForm infoForm = infoItemFormProvider.getInfoForm(classTypeId, themeDisplay.getScopeGroupId());

		List<InfoField> infoFields = infoForm.getAllInfoFields();

		Map<String, Serializable> values = new HashMap<>();

		for (InfoField infoField : infoFields) {
			String name = infoField.getName();

			String value = ParamUtil.getString(actionRequest, name);

			if (Validator.isNotNull(value)) {
				values.put(name, value);
			}
		}

		String objectEntryId = StringUtil.split(className, StringPool.POUND)[1];

		_objectEntryService.addObjectEntry(
			0, Long.parseLong(objectEntryId),
			values, ServiceContextFactory.getInstance(actionRequest));


		sendRedirect(
			actionRequest, actionResponse, _portal.getLayoutRelativeURL(themeDisplay.getLayout(), themeDisplay));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddFormItemMVCActionCommand.class);

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Portal _portal;

}