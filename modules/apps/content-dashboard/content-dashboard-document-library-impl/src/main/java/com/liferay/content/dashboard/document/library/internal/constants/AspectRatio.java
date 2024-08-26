/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.constants;

import java.util.Objects;

/**
 * @author Mikel Lorza
 */
public enum AspectRatio {

	SQUARE("square"), TALL("tall"), WIDE("wide");

	public static AspectRatio parse(String type) {
		for (AspectRatio aspectRatio : values()) {
			if (Objects.equals(aspectRatio.getType(), type)) {
				return aspectRatio;
			}
		}

		return null;
	}

	public String getType() {
		return _type;
	}

	@Override
	public String toString() {
		return _type;
	}

	private AspectRatio(String type) {
		_type = type;
	}

	private final String _type;

}