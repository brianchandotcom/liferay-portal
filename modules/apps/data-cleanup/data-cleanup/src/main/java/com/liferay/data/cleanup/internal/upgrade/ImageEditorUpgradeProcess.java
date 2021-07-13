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

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.data.cleanup.internal.upgrade.util.LayoutTypeSettingsUtil;

/**
 * @author Adolfo Pérez
 */
public class ImageEditorUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		LayoutTypeSettingsUtil.removePortletId(connection, _PORTLET_ID);

		deleteFromPortlet(_PORTLET_ID);

		deleteFromPortletPreferences(_PORTLET_ID);

		deleteFromRelease(
			"com.liferay.frontend.image.editor.api",
			"com.liferay.frontend.image.editor.integration.document.library",
			"com.liferay.frontend.image.editor.web");

		deleteFromResourceAction(_PORTLET_ID);

		deleteFromResourcePermission(_PORTLET_ID);
	}

	private static final String _PORTLET_ID =
		"com_liferay_image_editor_web_portlet_ImageEditorPortlet";

}