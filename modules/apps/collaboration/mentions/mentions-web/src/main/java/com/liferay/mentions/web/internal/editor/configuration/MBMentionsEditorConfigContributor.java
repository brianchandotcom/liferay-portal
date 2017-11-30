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

package com.liferay.mentions.web.internal.editor.configuration;

import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"editor.config.key=bodyEditor", "editor.config.key=replyMBEditor",
		"editor.name=alloyeditor", "editor.name=alloyeditor_bbcode",
		"editor.name=ckeditor", "editor.name=ckeditor_bbcode",
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"service.ranking:Integer=10"
	},
	service = EditorConfigContributor.class
)
public class MBMentionsEditorConfigContributor
	extends BaseMentionsEditorConfigContributor {
}