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

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author David Arques
 */
public class TrafficSource {

	public TrafficSource() {
	}

	public TrafficSource(
		String name, List<SearchKeyword> searchKeywords, int trafficAmount,
		double trafficShare) {

		_name = name;
		_searchKeywords = searchKeywords;
		_trafficAmount = trafficAmount;
		_trafficShare = trafficShare;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TrafficSource)) {
			return false;
		}

		TrafficSource trafficSource = (TrafficSource)obj;

		if (Objects.equals(_name, trafficSource._name) &&
			Objects.equals(_searchKeywords, trafficSource._searchKeywords) &&
			Objects.equals(_trafficAmount, trafficSource._trafficAmount) &&
			Objects.equals(_trafficShare, trafficSource._trafficShare)) {

			return true;
		}

		return false;
	}

	public String getName() {
		return _name;
	}

	@JsonProperty("keywords")
	public List<SearchKeyword> getSearchKeywords() {
		return _searchKeywords;
	}

	public int getTrafficAmount() {
		return _trafficAmount;
	}

	public double getTrafficShare() {
		return _trafficShare;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_name, _searchKeywords, _trafficAmount, _trafficShare);
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSearchKeywords(List<SearchKeyword> searchKeywords) {
		_searchKeywords = searchKeywords;
	}

	public void setTrafficAmount(int trafficAmount) {
		_trafficAmount = trafficAmount;
	}

	public void setTrafficShare(double trafficShare) {
		_trafficShare = trafficShare;
	}

	public JSONObject toJSONObject(String helpMessage, String title) {
		return JSONUtil.put(
			"helpMessage", helpMessage
		).put(
			"keywords", _getSearchKeywordsJSONArray()
		).put(
			"name", getName()
		).put(
			"share", getTrafficShare()
		).put(
			"title", title
		).put(
			"value", getTrafficAmount()
		);
	}

	private JSONArray _getSearchKeywordsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (ListUtil.isEmpty(_searchKeywords)) {
			return jsonArray;
		}

		Stream<SearchKeyword> stream = _searchKeywords.stream();

		Comparator<SearchKeyword> comparator = Comparator.comparingInt(
			SearchKeyword::getTraffic);

		stream.sorted(
			comparator.reversed()
		).limit(
			5
		).forEachOrdered(
			searchKeyword -> jsonArray.put(searchKeyword.toJSONObject())
		);

		return jsonArray;
	}

	private String _name;
	private List<SearchKeyword> _searchKeywords;
	private int _trafficAmount;
	private double _trafficShare;

}