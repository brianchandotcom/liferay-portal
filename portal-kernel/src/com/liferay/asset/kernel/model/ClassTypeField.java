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

package com.liferay.asset.kernel.model;

/**
 * @author Adolfo Pérez
 */
public class ClassTypeField {

	public ClassTypeField(
		String label, String name, String type, long classTypeId) {

		_label = label;
		_name = name;
		_type = type;
		_classTypeId = classTypeId;
	}

	public long getClassTypeId() {
		return _classTypeId;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	private final long _classTypeId;
	private final String _label;
	private final String _name;
	private final String _type;

}