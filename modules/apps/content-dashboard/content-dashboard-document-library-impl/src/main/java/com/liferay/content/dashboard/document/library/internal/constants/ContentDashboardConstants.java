/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.constants;

import java.util.Objects;

/**
 * @author Mikel Lorza
 */
public class ContentDashboardConstants {

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

	public enum Resolution {

		LARGE(Long.MAX_VALUE, Long.MAX_VALUE, 769, 1025, "large"),
		MEDIUM(768, 1024, 301, 401, "medium"), SMALL(300, 400, 0, 0, "small");

		public static Resolution parse(String type) {
			for (Resolution resolution : values()) {
				if (Objects.equals(resolution.getType(), type)) {
					return resolution;
				}
			}

			return null;
		}

		public long getEndLengthValue() {
			return _endLengthValue;
		}

		public long getEndWidthValue() {
			return _endWidthValue;
		}

		public long getStartLengthValue() {
			return _startLengthValue;
		}

		public long getStartWidthValue() {
			return _startWidthValue;
		}

		public String getType() {
			return _type;
		}

		@Override
		public String toString() {
			return _type;
		}

		private Resolution(
			long endLengthValue, long endWidthValue, long startLengthValue,
			long startWidthValue, String type) {

			_endLengthValue = endLengthValue;
			_endWidthValue = endWidthValue;
			_startLengthValue = startLengthValue;
			_startWidthValue = startWidthValue;
			_type = type;
		}

		private final long _endLengthValue;
		private final long _endWidthValue;
		private final long _startLengthValue;
		private final long _startWidthValue;
		private final String _type;

	}

}