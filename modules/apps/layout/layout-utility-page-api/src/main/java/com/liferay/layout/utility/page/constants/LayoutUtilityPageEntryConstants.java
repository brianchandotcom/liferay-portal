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

package com.liferay.layout.utility.page.constants;

/**
 * @author Eudaldo Alonso
 */
public class LayoutUtilityPageEntryConstants {

	public static Type parse(int type) {
		if (Type.ERROR_404.getType() == type) {
			return Type.ERROR_404;
		}
		else if (Type.TERMS_OF_USE.getType() == type) {
			return Type.TERMS_OF_USE;
		}

		throw new IllegalArgumentException("Invalid type " + type);
	}

	public enum Type {

		ERROR_404("404", 1), TERMS_OF_USE("terms-of-use", 2);

		public String getLabel() {
			return _label;
		}

		public int getType() {
			return _type;
		}

		public void setLabel(String label) {
			_label = label;
		}

		public void setType(int type) {
			_type = type;
		}

		private Type(String label, int type) {
			_label = label;
			_type = type;
		}

		private String _label;
		private int _type;

	}

}