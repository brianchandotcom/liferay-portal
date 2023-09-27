/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static final int TYPE_INPUT = 3;

	public static final String TYPE_INPUT_LABEL = "input";

	public static final int TYPE_PORTLET = 4;

	public static final String TYPE_PORTLET_LABEL = "widget";

	public static final int TYPE_REACT = 2;

	public static final String TYPE_REACT_LABEL = "react";

	public static final int TYPE_SECTION = 0;

	public static final String TYPE_SECTION_LABEL = "section";

	public static int getTypeFromLabel(String label) {
		if (Objects.equals(TYPE_COMPONENT_LABEL, label)) {
			return TYPE_COMPONENT;
		}

		if (Objects.equals(TYPE_INPUT_LABEL, label)) {
			return TYPE_INPUT;
		}

		if (Objects.equals(TYPE_PORTLET_LABEL, label)) {
			return TYPE_PORTLET;
		}

		if (Objects.equals(TYPE_REACT_LABEL, label)) {
			return TYPE_REACT;
		}

		return TYPE_SECTION;
	}

	public static String getTypeLabel(int type) {
		if (type == TYPE_COMPONENT) {
			return TYPE_COMPONENT_LABEL;
		}

		if (type == TYPE_INPUT) {
			return TYPE_INPUT_LABEL;
		}

		if (type == TYPE_PORTLET) {
			return TYPE_PORTLET_LABEL;
		}

		if (type == TYPE_REACT) {
			return TYPE_REACT_LABEL;
		}

		return TYPE_SECTION_LABEL;
	}

}