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

package com.liferay.talend.common.oas;

import com.liferay.talend.common.util.StringUtil;

/**
 * @author Ivica Cardic
 */
public class OASParameter {

	public OASParameter(String name, String type) {
		_name = name;
		_type = Type.valueOf(StringUtil.toUpperCase(type));
	}

	public String getName() {
		return _name;
	}

	public Type getType() {
		return _type;
	}

	public boolean isRequired() {
		return _required;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public void setType(Type type) {
		_type = type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("(name=");
		sb.append(_name);
		sb.append(", required=");
		sb.append(_required);
		sb.append(", type=");
		sb.append(_type);
		sb.append("}");

		return sb.toString();
	}

	public enum Type {

		PATH, QUERY

	}

	private String _name;
	private boolean _required;
	private Type _type;

}