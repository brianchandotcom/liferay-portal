/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.model;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Alexander Chow
 * @author Eduardo Lundgren
 */
public class DDMTemplateConstants {

	public static final String LANG_TYPE_FTL = "ftl";

	public static final String LANG_TYPE_VM = "vm";

	public static final String LANG_TYPE_XSD = "xsd";

	public static final String[] LANG_TYPES = PropsUtil.getArray(
		PropsKeys.DYNAMIC_DATA_MAPPING_TEMPLATE_LANGUAGE_TYPES);

	public static final String TEMPLATE_MODE_CREATE = "create";

	public static final String TEMPLATE_MODE_EDIT = "edit";

	public static final String TEMPLATE_TYPE_DISPLAY = "display";

	public static final String TEMPLATE_TYPE_FORM = "form";

}