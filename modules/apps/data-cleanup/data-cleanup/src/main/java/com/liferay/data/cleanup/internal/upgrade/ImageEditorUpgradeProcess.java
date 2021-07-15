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

/**
 * @author Adolfo Pérez
 */
public class ImageEditorUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removePortletData(
			new String[] {
				"com.liferay.frontend.image.editor.api",
				"com.liferay.frontend.image.editor.integration.document." +
					"library",
				"com.liferay.frontend.image.editor.web"
			},
			null,
			new String[] {
				"com_liferay_image_editor_web_portlet_ImageEditorPortlet"
			});
	}

}