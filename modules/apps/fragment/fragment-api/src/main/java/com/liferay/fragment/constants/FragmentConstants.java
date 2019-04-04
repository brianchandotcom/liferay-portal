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
 * @author Preston Crary
 */
public class FragmentConstants {

	public static final String RESOURCE_NAME = "com.liferay.fragment";

	public static final String SERVICE_NAME = "com.liferay.fragment";

	public static final int TYPE_COMPONENT = 1;

	public static final String TYPE_COMPONENT_LABEL = "component";

	public static final int TYPE_SECTION = 0;

	public static final String TYPE_SECTION_LABEL = "section";

	public static int getTypeFromLabel(String label) {
		if (Objects.equals(TYPE_COMPONENT_LABEL, label)) {
			return TYPE_COMPONENT;
		}

		return TYPE_SECTION;
	}

	public static String getTypeLabel(int type) {
		if (type == TYPE_COMPONENT) {
			return TYPE_COMPONENT_LABEL;
		}

		return TYPE_SECTION_LABEL;
	}

}