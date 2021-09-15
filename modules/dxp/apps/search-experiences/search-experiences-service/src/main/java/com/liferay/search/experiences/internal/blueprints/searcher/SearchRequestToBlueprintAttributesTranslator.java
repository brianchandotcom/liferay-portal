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

package com.liferay.search.experiences.internal.blueprints.searcher;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.search.experiences.internal.blueprints.attributes.BlueprintAttributes;
import com.liferay.search.experiences.internal.blueprints.attributes.BlueprintAttributesBuilder;
import com.liferay.search.experiences.internal.blueprints.attributes.BlueprintAttributesBuilderFactory;

import java.util.Locale;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 * @author André de Oliveira
 */
@Component(service = SearchRequestToBlueprintAttributesTranslator.class)
public class SearchRequestToBlueprintAttributesTranslator {

	public BlueprintAttributes translate(SearchRequest searchRequest) {
		BlueprintAttributesBuilder blueprintAttributesBuilder =
			_blueprintAttributesBuilderFactory.builder();

		blueprintAttributesBuilder.companyId(
			getCompanyId(searchRequest)
		).keywords(
			searchRequest.getQueryString()
		).locale(
			getLocale(searchRequest)
		).userId(
			getUserId(searchRequest)
		).addAttribute(
			"ip_address", getIpAddress(searchRequest)
		).addAttribute(
			"plid", getPlid(searchRequest)
		).addAttribute(
			"scope_group_id", getScopeGroupId(searchRequest)
		).addAttribute(
			"timezone_id", getTimezoneId(searchRequest)
		).addAttribute(
			"federatedSearchKey", searchRequest.getFederatedSearchKey()
		);

		addCommerceAttributes(searchRequest, blueprintAttributesBuilder);

		return blueprintAttributesBuilder.build();
	}

	protected void addCommerceAttributes(
		SearchRequest searchRequest,
		BlueprintAttributesBuilder blueprintAttributesBuilder) {

		long channelGroupId = getCommerceChannelGroupId(searchRequest);

		if (channelGroupId > 0) {
			blueprintAttributesBuilder.addAttribute(
				"channel_group_id", channelGroupId);
		}

		long[] accountGroupIds = getCommerceAccountGroupIds(searchRequest);

		if (accountGroupIds.length > 0) {
			blueprintAttributesBuilder.addAttribute(
				"account_group_ids", accountGroupIds);
		}
	}

	protected long[] getCommerceAccountGroupIds(SearchRequest searchRequest) {
		Object object = _searchRequestBuilderFactory.builder(
			searchRequest
		).withSearchContextGet(
			searchContext -> searchContext.getAttribute(
				"commerceAccountGroupIds")
		);

		return GetterUtil.getLongValues(object);
	}

	protected long getCommerceChannelGroupId(SearchRequest searchRequest) {
		Object object = _searchRequestBuilderFactory.builder(
			searchRequest
		).withSearchContextGet(
			searchContext -> searchContext.getAttribute(
				"commerceChannelGroupId")
		);

		return GetterUtil.getLong(object);
	}

	protected long getCompanyId(SearchRequest searchRequest) {
		return _searchRequestBuilderFactory.builder(
			searchRequest
		).withSearchContextGet(
			searchContext -> searchContext.getCompanyId()
		);
	}

	protected String getIpAddress(SearchRequest searchRequest) {
		return _searchRequestBuilderFactory.builder(
			searchRequest
		).withSearchContextGet(
			searchContext -> GetterUtil.getString(
				searchContext.getAttribute("ipAddress"))
		);
	}

	protected Layout getLayout(SearchRequest searchRequest) {
		return _searchRequestBuilderFactory.builder(
			searchRequest
		).withSearchContextGet(
			SearchContext::getLayout
		);
	}

	protected Locale getLocale(SearchRequest searchRequest) {
		return _searchRequestBuilderFactory.builder(
			searchRequest
		).withSearchContextGet(
			SearchContext::getLocale
		);
	}

	protected Long getPlid(SearchRequest searchRequest) {
		Layout layout = getLayout(searchRequest);

		if (layout == null) {
			return null;
		}

		return layout.getPlid();
	}

	protected Long getScopeGroupId(SearchRequest searchRequest) {
		Layout layout = getLayout(searchRequest);

		if (layout == null) {
			return null;
		}

		return layout.getGroupId();
	}

	protected String getTimezoneId(SearchRequest searchRequest) {
		TimeZone timeZone = _searchRequestBuilderFactory.builder(
			searchRequest
		).withSearchContextGet(
			SearchContext::getTimeZone
		);

		if (timeZone == null) {
			return null;
		}

		return timeZone.getID();
	}

	protected Long getUserId(SearchRequest searchRequest) {
		return _searchRequestBuilderFactory.builder(
			searchRequest
		).withSearchContextGet(
			searchContext -> {
				long userId = searchContext.getUserId();

				if (userId == 0) {
					return null;
				}

				return Long.valueOf(userId);
			}
		);
	}

	@Reference
	private BlueprintAttributesBuilderFactory
		_blueprintAttributesBuilderFactory;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}