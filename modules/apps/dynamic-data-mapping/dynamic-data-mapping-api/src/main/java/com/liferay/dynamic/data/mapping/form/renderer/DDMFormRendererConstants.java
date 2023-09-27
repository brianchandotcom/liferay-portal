/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.renderer;

import com.liferay.petra.string.StringPool;

/**
 * @author     Marcellus Tavares
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.dynamic.data.mapping.form.renderer.constants.DDMFormRendererConstants}
 */
@Deprecated
public class DDMFormRendererConstants {

	public static final String DDM_FORM_FIELD_LANGUAGE_ID_SEPARATOR =
		StringPool.DOUBLE_DOLLAR;

	public static final String DDM_FORM_FIELD_NAME_PREFIX = "ddm$$";

	public static final String DDM_FORM_FIELD_PARTS_SEPARATOR =
		StringPool.DOLLAR;

	public static final String DDM_FORM_FIELDS_SEPARATOR = StringPool.POUND;

	private DDMFormRendererConstants() {
	}

}