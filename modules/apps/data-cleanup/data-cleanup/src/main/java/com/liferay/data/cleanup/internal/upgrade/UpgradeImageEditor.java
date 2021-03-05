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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Adolfo Pérez
 */
public class UpgradeImageEditor extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"delete from Portlet where portletId = " +
				_QUOTED_IMAGE_EDITOR_PORTLET_NAME);

		runSQL(
			"delete from PortletPreferences where portletId = " +
				_QUOTED_IMAGE_EDITOR_PORTLET_NAME);

		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.frontend.image.editor.api'");
		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.frontend.image.editor.integration.document." +
					"library'");
		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.frontend.image.editor.web'");

		runSQL(
			"delete from ResourceAction where name = " +
				_QUOTED_IMAGE_EDITOR_PORTLET_NAME);

		runSQL(
			"delete from ResourcePermission where name = " +
				_QUOTED_IMAGE_EDITOR_PORTLET_NAME);

		LayoutTypeSettingsUtil.removePortletId(
			connection, _IMAGE_EDITOR_PORTLET_NAME);
	}

	private static final String _IMAGE_EDITOR_PORTLET_NAME =
		"com_liferay_image_editor_web_portlet_ImageEditorPortlet";

	private static final String _QUOTED_IMAGE_EDITOR_PORTLET_NAME =
		StringPool.APOSTROPHE + _IMAGE_EDITOR_PORTLET_NAME +
			StringPool.APOSTROPHE;

}