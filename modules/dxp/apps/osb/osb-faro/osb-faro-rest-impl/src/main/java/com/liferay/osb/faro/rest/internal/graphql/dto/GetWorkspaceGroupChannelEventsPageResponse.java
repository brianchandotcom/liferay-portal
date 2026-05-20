/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.graphql.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

/**
 * @author Leslie Wong
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWorkspaceGroupChannelEventsPageResponse {

	public EventBag getEventBag() {
		return _eventBag;
	}

	public void setEventBag(EventBag eventBag) {
		_eventBag = eventBag;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Event {

		public String getApplicationId() {
			return _applicationId;
		}

		public String getAssetTitle() {
			return _assetTitle;
		}

		public String getCanonicalUrl() {
			return _canonicalUrl;
		}

		@JsonFormat(pattern = "EEE MMM dd HH:mm:ss zzz yyyy")
		public Date getCreateDate() {
			return _createDate;
		}

		public String getEmailAddressHashed() {
			return _emailAddressHashed;
		}

		public String getName() {
			return _name;
		}

		public String getPageDescription() {
			return _pageDescription;
		}

		public String getPageKeywords() {
			return _pageKeywords;
		}

		public String getPageTitle() {
			return _pageTitle;
		}

		public List<Property> getProperties() {
			return _properties;
		}

		public String getReferrer() {
			return _referrer;
		}

		public String getUrl() {
			return _url;
		}

		public void setApplicationId(String applicationId) {
			_applicationId = applicationId;
		}

		public void setAssetTitle(String assetTitle) {
			_assetTitle = assetTitle;
		}

		public void setCanonicalUrl(String canonicalUrl) {
			_canonicalUrl = canonicalUrl;
		}

		public void setCreateDate(Date createDate) {
			_createDate = createDate;
		}

		public void setEmailAddressHashed(String emailAddressHashed) {
			_emailAddressHashed = emailAddressHashed;
		}

		public void setName(String name) {
			_name = name;
		}

		public void setPageDescription(String pageDescription) {
			_pageDescription = pageDescription;
		}

		public void setPageKeywords(String pageKeywords) {
			_pageKeywords = pageKeywords;
		}

		public void setPageTitle(String pageTitle) {
			_pageTitle = pageTitle;
		}

		public void setProperties(List<Property> properties) {
			_properties = properties;
		}

		public void setReferrer(String referrer) {
			_referrer = referrer;
		}

		public void setUrl(String url) {
			_url = url;
		}

		private String _applicationId;
		private String _assetTitle;
		private String _canonicalUrl;
		private Date _createDate;
		private String _emailAddressHashed;
		private String _name;
		private String _pageDescription;
		private String _pageKeywords;
		private String _pageTitle;
		private List<Property> _properties;
		private String _referrer;
		private String _url;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class EventBag {

		public List<Event> getEvents() {
			return _events;
		}

		public Integer getTotal() {
			return _total;
		}

		public void setEvents(List<Event> events) {
			_events = events;
		}

		public void setTotal(Integer total) {
			_total = total;
		}

		private List<Event> _events;
		private Integer _total;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Property {

		public String getName() {
			return _name;
		}

		public String getValue() {
			return _value;
		}

		public void setName(String name) {
			_name = name;
		}

		public void setValue(String value) {
			_value = value;
		}

		private String _name;
		private String _value;

	}

	private EventBag _eventBag;

}