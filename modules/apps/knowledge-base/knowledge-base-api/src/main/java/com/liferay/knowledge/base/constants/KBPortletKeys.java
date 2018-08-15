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