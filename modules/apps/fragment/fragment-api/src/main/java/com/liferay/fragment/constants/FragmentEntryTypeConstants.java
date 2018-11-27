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

package com.liferay.fragment.constants;

import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class FragmentEntryTypeConstants {

	public static final int TYPE_ELEMENT = 1;

	public static final String TYPE_ELEMENT_LABEL = "element";

	public static final int TYPE_SECTION = 0;

	public static final String TYPE_SECTION_LABEL = "section";

	public static int getTypeFromLabel(String label) {
		if (Objects.equals(TYPE_ELEMENT_LABEL, label)) {
			return TYPE_ELEMENT;
		}

		return TYPE_SECTION;
	}

	public static String getTypeLabel(int type) {
		if (type == TYPE_ELEMENT) {
			return TYPE_ELEMENT_LABEL;
		}

		return TYPE_SECTION_LABEL;
	}

}