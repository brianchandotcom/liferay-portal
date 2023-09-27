/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.constants;

import com.liferay.portal.kernel.util.PortletKeys;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 */
public class KBPortletKeys extends PortletKeys {

	public static final String KNOWLEDGE_BASE_ADMIN =
		"com_liferay_knowledge_base_web_portlet_AdminPortlet";

	public static final String KNOWLEDGE_BASE_ARTICLE =
		"com_liferay_knowledge_base_web_portlet_ArticlePortlet";

	public static final String KNOWLEDGE_BASE_ARTICLE_DEFAULT_INSTANCE =
		KNOWLEDGE_BASE_ARTICLE + KBPortletKeys._INSTANCE_SEPARATOR + "0000";

	public static final String KNOWLEDGE_BASE_DISPLAY =
		"com_liferay_knowledge_base_web_portlet_DisplayPortlet";

	public static final String KNOWLEDGE_BASE_SEARCH =
		"com_liferay_knowledge_base_web_portlet_SearchPortlet";

	public static final String KNOWLEDGE_BASE_SECTION =
		"com_liferay_knowledge_base_web_portlet_SectionPortlet";

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

}