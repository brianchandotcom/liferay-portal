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

package com.liferay.jenkins.results.parser.spira;

import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseSpiraArtifact implements SpiraArtifact{

	public abstract int getID();

	public String getName() {
		return jsonObject.getString("Name");
	}

	@Override
	public String toString() {
		return jsonObject.toString();
	}

	protected BaseSpiraArtifact(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public SpiraProject getSpiraProject() {
		if (this instanceof SpiraProject) {
			return (SpiraProject)this;
		}

		return SpiraProject.getSpiraProjectByID(jsonObject.getInt("ProjectId"));
	}

	protected boolean matches(SearchParameter... searchParameters) {
		for (SearchParameter searchParameter : searchParameters) {
			if (!searchParameter.matches(jsonObject)) {
				return false;
			}
		}

		return true;
	}

	protected final JSONObject jsonObject;

	protected static class SearchParameter {

		public SearchParameter(String name, Object value) {
			_name = name;
			_value = value;
		}

		public String getName() {
			return _name;
		}

		public Object getValue() {
			return _value;
		}

		public boolean matches(JSONObject jsonObject) {
			if (!Objects.equals(getValue(), jsonObject.get(getName()))) {
				return false;
			}

			return true;
		}

		public JSONObject toFilterJSONObject() {
			JSONObject filterJSONObject = new JSONObject();

			filterJSONObject.put("PropertyName", _name);

			if (_value instanceof Integer) {
				Integer intValue = (Integer)_value;

				filterJSONObject.put("IntValue", intValue);
			}
			else if (_value instanceof String) {
				String stringValue = (String)_value;

				stringValue = stringValue.replaceAll("\\[", "[[]");

				filterJSONObject.put("StringValue", stringValue);
			}
			else {
				throw new RuntimeException("Invalid value type");
			}

			return filterJSONObject;
		}

		private final String _name;
		private final Object _value;

	}

}