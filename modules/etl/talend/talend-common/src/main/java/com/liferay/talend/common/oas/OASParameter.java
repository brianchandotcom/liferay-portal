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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class OASParameter {

	public static final List<OASParameter> liferayOASParameters =
		new ArrayList<OASParameter>() {
			{
				add(new OASParameter("fields", "query"));
				add(new OASParameter("nestedFields", "query"));
			}
		};
	public static final List<OASParameter> proxyParameters =
		new ArrayList<OASParameter>() {
			{
				add(new OASParameter("proxyIdentityId", "header"));
				add(new OASParameter("proxyIdentitySecret", "header"));
			}
		};

	public OASParameter(String name, String location) {
		_name = name;
		_location = Location.valueOf(StringUtil.toUpperCase(location));

		if (isLocationPath()) {
			_required = true;
		}
	}

	public Location getLocation() {
		return _location;
	}

	public String getName() {
		return _name;
	}

	public boolean isLocationPath() {
		return _location.isPath();
	}

	public boolean isRequired() {
		return _required;
	}

	public void setLocation(Location location) {
		_location = location;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{location=");
		sb.append(_location);
		sb.append(", name=");
		sb.append(_name);
		sb.append(", required=");
		sb.append(_required);
		sb.append("}");

		return sb.toString();
	}

	public enum Location {

		HEADER, PATH, QUERY;

		public boolean isPath() {
			if (this == PATH) {
				return true;
			}

			return false;
		}

	}

	private Location _location;
	private String _name;
	private boolean _required;

}