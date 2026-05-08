/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface TestrayFactor {

	public static final String[] FIELD_NAMES = {
		"dateCreated", "dateModified", "id", "name", "factorCategoryToFactors",
		"factorOptionToFactors"
	};

	public BaseTestrayFactor.Category getCategory();

	public Long getID();

	public JSONObject getJSONObject();

	public String getName();

	public BaseTestrayFactor.Option getOption();

	public static class Category {

		public static final String[] FIELD_NAMES = {
			"dateCreated", "dateModified", "id", "name"
		};

		public Long getID() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		protected Category(JSONObject jsonObject) {
			_id = jsonObject.getLong("id");
			_name = jsonObject.getString("name");
		}

		private final long _id;
		private final String _name;

	}

	public static class Option {

		public static final String[] FIELD_NAMES = {
			"dateCreated", "dateModified", "id", "name"
		};

		public Long getID() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		protected Option(JSONObject jsonObject) {
			_id = jsonObject.getLong("id");
			_name = jsonObject.getString("name");
		}

		private final long _id;
		private final String _name;

	}

}