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

package com.liferay.app.builder.constants;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Bruno Farache
 * @author Gabriel Albuquerque
 */
public class AppBuilderAppConstants {

	public static final int STATUS_DEPLOYED = 0;

	public static final int STATUS_UNDEPLOYED = 1;

	public enum Status {

		DEPLOYED(STATUS_DEPLOYED, "deployed"),
		UNDEPLOYED(STATUS_UNDEPLOYED, "undeployed");

		public static Status parse(int value) {
			for (Status status : values()) {
				if (status.getValue() == value) {
					return status;
				}
			}

			return null;
		}

		public static Status parse(String label) {
			for (Status status : values()) {
				if (StringUtil.equalsIgnoreCase(label, status.getLabel())) {
					return status;
				}
			}

			return null;
		}

		public String getLabel() {
			return _label;
		}

		public int getValue() {
			return _value;
		}

		private Status(int value, String label) {
			_value = value;
			_label = label;
		}

		private final String _label;
		private final int _value;

	}

}