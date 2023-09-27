/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		String keyword, int position, int searchVolume, long traffic) {

		_keyword = keyword;
		_position = position;
		_searchVolume = searchVolume;
		_traffic = traffic;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SearchKeyword)) {
			return false;
		}

		SearchKeyword searchKeyword = (SearchKeyword)object;

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

	public long getTraffic() {
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

	public void setTraffic(long traffic) {
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
			"traffic", Math.toIntExact(getTraffic())
		);
	}

	private String _keyword;
	private int _position;
	private int _searchVolume;
	private long _traffic;

}