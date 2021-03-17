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

package com.liferay.message.boards.editor.configuration.internal;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.editor.configuration.EditorOptions;
import com.liferay.portal.kernel.editor.configuration.EditorOptionsContributor;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = {
		"editor.name=ckeditor", "editor.name=ckeditor_bbcode",
		"editor.name=ckeditor_classic",
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN
	},
	service = EditorOptionsContributor.class
)
public class MBEditorOptionsContributor implements EditorOptionsContributor {

	@Override
	public void populateEditorOptions(
		EditorOptions editorOptions,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		editorOptions.setUploadURL(
			PortletURLBuilder.create(
				requestBackedPortletURLFactory.createActionURL(
					PortletKeys.MESSAGE_BOARDS)
			).setActionName(
				"/message_boards/upload_temp_image"
			).setParameter(
				"categoryId",
				() -> {
					Map<String, String> fileBrowserParamsMap =
						(Map<String, String>)inputEditorTaglibAttributes.get(
							"liferay-ui:input-editor:fileBrowserParams");

					long categoryId = 0;

					if (fileBrowserParamsMap != null) {
						categoryId = GetterUtil.getLong(
							fileBrowserParamsMap.get("categoryId"));
					}

					return categoryId;
				}
			).buildString());
	}

}