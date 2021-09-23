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

package com.liferay.search.experiences.web.internal.blueprint.display.context;

import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class EntryDisplayContext {

	public Map<String, Object> getData() {
		return _data;
	}

	public long getId() {
		return _id;
	}

	public String getPageTitle() {
		return _pageTitle;
	}

	public String getRedirect() {
		return _redirect;
	}

	public int getType() {
		return _type;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setPageTitle(String pageTitle) {
		_pageTitle = pageTitle;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	public void setType(int type) {
		_type = type;
	}

	private Map<String, Object> _data;
	private long _id;
	private String _pageTitle;
	private String _redirect;
	private int _type;

}