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

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Objects;

/**
 * @author David Arques
 */
public class SearchKeyword {

	public SearchKeyword() {
	}

	public SearchKeyword(
		String keyword, int position, int searchVolume, int traffic) {

		_keyword = keyword;
		_position = position;
		_searchVolume = searchVolume;
		_traffic = traffic;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SearchKeyword)) {
			return false;
		}

		SearchKeyword searchKeyword = (SearchKeyword)obj;

		if (Objects.equals(_keyword, searchKeyword._keyword) &&
			Objects.equals(_position, searchKeyword._position) &&
			Objects.equals(_searchVolume, searchKeyword._searchVolume) &&
			Objects.equals(_traffic, searchKeyword._traffic)) {

			return true;
		}

		return false;
	}

	public String getKeyword() {
		return _keyword;
	}

	public int getPosition() {
		return _position;
	}

	public int getSearchVolume() {
		return _searchVolume;
	}

	public int getTraffic() {
		return _traffic;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_keyword, _position, _searchVolume, _traffic);
	}

	public void setKeyword(String keyword) {
		_keyword = keyword;
	}

	public void setPosition(int position) {
		_position = position;
	}

	public void setSearchVolume(int searchVolume) {
		_searchVolume = searchVolume;
	}

	public void setTraffic(int traffic) {
		_traffic = traffic;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"keyword", getKeyword()
		).put(
			"position", getPosition()
		).put(
			"searchVolume", getSearchVolume()
		).put(
			"traffic", getTraffic()
		);
	}

	private String _keyword;
	private int _position;
	private int _searchVolume;
	private int _traffic;

}