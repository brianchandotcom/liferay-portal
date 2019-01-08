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
		TemplateConstants.LANG_TYPE_XML, TemplateConstants.LANG_TYPE_XSL
	};

	public static final String BUNDLE_SEPARATOR = "_BUNDLE_CONTEXT_";

	public static final String CLASS_LOADER_SEPARATOR =
		"_CLASS_LOADER_CONTEXT_";

	public static final String CLASS_NAME_ID = "class_name_id";

	public static final String DEFAUT_ENCODING = StringPool.UTF8;

	/**
	 * @deprecated As of Newton (6.2.x)
	 */
	@Deprecated
	public static final String JOURNAL_SEPARATOR = "_JOURNAL_CONTEXT_";

	public static final String LANG_TYPE_CSS = "css";

	public static final String LANG_TYPE_FTL = "ftl";

	public static final String LANG_TYPE_JSON = "json";

	public static final String LANG_TYPE_SOY = "soy";

	public static final String LANG_TYPE_TPL = "tpl";

	public static final String LANG_TYPE_VM = "vm";

	public static final String LANG_TYPE_XML = "xml";

	public static final String LANG_TYPE_XSL = "xsl";

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