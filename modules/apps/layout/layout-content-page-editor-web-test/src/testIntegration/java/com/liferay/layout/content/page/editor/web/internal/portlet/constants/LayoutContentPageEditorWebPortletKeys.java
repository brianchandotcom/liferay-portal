/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.constants;

import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutContentPageEditorWebPortletKeys {

	public static final String
		LAYOUT_CONTENT_PAGE_EDITOR_WEB_NONINSTANCEABLE_TEST_PORTLET =
			"com_liferay_layout_content_page_editor_web_internal_portlet_" +
				"LayoutContentPageEditorWebNoninstanceableTestPortlet";

	public static final String
		LAYOUT_CONTENT_PAGE_EDITOR_WEB_NONINSTANCEABLE_TEST_PORTLET_ALIAS =
			StringUtil.toLowerCase(
				RandomTestUtil.randomString(
					NumericStringRandomizerBumper.INSTANCE,
					UniqueStringRandomizerBumper.INSTANCE));

	public static final String LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET =
		"com_liferay_layout_content_page_editor_web_internal_portlet_" +
			"LayoutContentPageEditorWebTestPortlet";

	public static final String
		LAYOUT_CONTENT_PAGE_EDITOR_WEB_TEST_PORTLET_ALIAS =
			StringUtil.toLowerCase(
				RandomTestUtil.randomString(
					NumericStringRandomizerBumper.INSTANCE,
					UniqueStringRandomizerBumper.INSTANCE));

}