/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.results.builder;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;

/**
 * @author André de Oliveira
 * @author Bryan Engler
 */
public class RankingJSONBuilder {

	public JSONObject build() {
		return build(
			JSONUtil.put(
				"author", _document.getString(Field.USER_NAME)
			).put(
				"clicks", _document.getString("clicks")
			).put(
				"description", _document.getString(Field.DESCRIPTION)
			).put(
				"id", _document.getString(Field.UID)
			).put(
				"title", getTitle()
			).put(
				"type", _document.getString(Field.ENTRY_CLASS_NAME)
			));
	}

	public RankingJSONBuilder document(Document document) {
		_document = document;

		return this;
	}

	public RankingJSONBuilder hidden(boolean hidden) {
		_hidden = hidden;

		return this;
	}

	public RankingJSONBuilder pinned(boolean pinned) {
		_pinned = pinned;

		return this;
	}

	protected JSONObject build(JSONObject jsonObject) {
		if (_hidden) {
			jsonObject.put("hidden", true);
		}

		if (_pinned) {
			jsonObject.put("pinned", true);
		}

		return jsonObject;
	}

	protected String getTitle() {
		String title = _document.getString(Field.TITLE + "_en_US");

		if (!Validator.isBlank(title)) {
			return title;
		}

		return _document.getString(Field.TITLE);
	}

	private Document _document;
	private boolean _hidden;
	private boolean _pinned;

}