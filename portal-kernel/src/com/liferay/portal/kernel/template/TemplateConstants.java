/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

import com.liferay.petra.string.StringPool;

/**
 * @author Tina Tian
 */
public interface TemplateConstants {

	public static final String[] ALLOWED_LANG_TYPES = {
		TemplateConstants.LANG_TYPE_CSS, TemplateConstants.LANG_TYPE_FTL,
		TemplateConstants.LANG_TYPE_JSON, TemplateConstants.LANG_TYPE_SOY,
		TemplateConstants.LANG_TYPE_TPL, TemplateConstants.LANG_TYPE_VM,
		TemplateConstants.LANG_TYPE_XML
	};

	public static final String BUNDLE_SEPARATOR = "_BUNDLE_CONTEXT_";

	public static final String CLASS_LOADER_SEPARATOR =
		"_CLASS_LOADER_CONTEXT_";

	public static final String CLASS_NAME_ID = "class_name_id";

	public static final String DEFAUT_ENCODING = StringPool.UTF8;

	public static final String LANG_TYPE_CSS = "css";

	public static final String LANG_TYPE_FTL = "ftl";

	public static final String LANG_TYPE_JSON = "json";

	public static final String LANG_TYPE_SOY = "soy";

	public static final String LANG_TYPE_TPL = "tpl";

	public static final String LANG_TYPE_VM = "vm";

	public static final String LANG_TYPE_XML = "xml";

	public static final String NAMESPACE = "namespace";

	public static final String RENDER_STRICT = "render_strict";

	public static final String SERVLET_SEPARATOR = "_SERVLET_CONTEXT_";

	public static final String TEMPLATE_ID = "template_id";

	public static final String TEMPLATE_RESOURCE_UUID_PREFIX =
		"TEMPLATE_RESOURCE_UUID";

	public static final String TEMPLATE_SEPARATOR = "_TEMPLATE_CONTEXT_";

	public static final String THEME_LOADER_SEPARATOR =
		"_THEME_LOADER_CONTEXT_";

	public static final String WRITER = "writer";

}