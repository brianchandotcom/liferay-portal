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

package com.liferay.osb.faro.engine.client.internal;

import com.liferay.osb.faro.engine.client.CerebroEngineClient;
import com.liferay.osb.faro.engine.client.http.client.AuthenticationClientHttpRequestInterceptor;
import com.liferay.osb.faro.engine.client.model.GraphQLRequest;
import com.liferay.osb.faro.engine.client.util.EngineServiceURLUtil;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringBundler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = CerebroEngineClient.class)
public class CerebroEngineClientImpl implements CerebroEngineClient {

	@Override
	public long getPageViews(
			FaroProject faroProject, Optional<Date> fromDateOptional,
			Optional<Date> toDateOptional)
		throws Exception {

		ResponseEntity<String> responseEntity = _getResponseEntity(
			faroProject,
			_getPagesCountGraphQLRequestHttpEntity(
				fromDateOptional, toDateOptional));

		JSONObject rootJSONObject = _jsonFactory.createJSONObject(
			responseEntity.getBody());

		JSONObject dataJSONObject = rootJSONObject.getJSONObject("data");

		return dataJSONObject.getLong("pagesCount");
	}

	@Override
	public String getSiteMetrics(
			String channelId, FaroProject faroProject, String interval,
			int rangeKey)
		throws Exception {

		GraphQLRequest graphQLRequest = new GraphQLRequest();

		graphQLRequest.setOperationName("SiteMetrics");

		StringBundler sb = new StringBundler(10);

		sb.append("query SiteMetrics($channelId: String $interval: String! ");
		sb.append("$rangeKey: Int) {site(channelId: $channelId interval: ");
		sb.append("$interval rangeKey: $rangeKey) {bounceRateMetric {trend {");
		sb.append("percentage trendClassification} previousValue value} ");
		sb.append("sessionDurationMetric {trend {percentage ");
		sb.append("trendClassification} previousValue value} ");
		sb.append("sessionsPerVisitorMetric {trend {percentage ");
		sb.append("trendClassification} previousValue value} visitorsMetric {");
		sb.append("trend {percentage trendClassification} previousValue ");
		sb.append("value}}}");

		graphQLRequest.setQuery(sb.toString());

		graphQLRequest.setVariables(
			new HashMap<String, Object>() {
				{
					put("channelId", channelId);
					put("interval", interval);
					put("rangeKey", rangeKey);
				}
			});

		ResponseEntity<String> responseEntity = _getResponseEntity(
			faroProject, graphQLRequest);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			responseEntity.getBody());

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject siteJSONObject = dataJSONObject.getJSONObject("site");

		return siteJSONObject.toString();
	}

	@Override
	public boolean isCustomEventsLimitReached(FaroProject faroProject)
		throws Exception {

		GraphQLRequest graphQLRequest = new GraphQLRequest();

		graphQLRequest.setQuery("{customEventLimitReached}");

		ResponseEntity<String> responseEntity = _getResponseEntity(
			faroProject, graphQLRequest);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			responseEntity.getBody());

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		return dataJSONObject.getBoolean("customEventLimitReached");
	}

	@Override
	public void updateTimeZone(FaroProject faroProject) throws Exception {
		_getResponseEntity(
			faroProject,
			_getTimeZoneGraphQLRequestHttpEntity(faroProject.getTimeZoneId()));
	}

	private String _getDateTimeString(Optional<Date> dateOptional) {
		if (!dateOptional.isPresent()) {
			return StringPool.BLANK;
		}

		Date date = dateOptional.get();

		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

		return localDateTime.toString();
	}

	private GraphQLRequest _getPagesCountGraphQLRequestHttpEntity(
		Optional<Date> fromDateOptional, Optional<Date> toDateOptional) {

		GraphQLRequest graphQLRequest = new GraphQLRequest();

		StringBundler sb = new StringBundler(10);

		sb.append("{pagesCount");

		if (fromDateOptional.isPresent() || toDateOptional.isPresent()) {
			sb.append(StringPool.OPEN_PARENTHESIS);

			if (fromDateOptional.isPresent()) {
				sb.append(" fromDate: \"");
				sb.append(_getDateTimeString(fromDateOptional));
				sb.append(StringPool.QUOTE);
			}

			if (toDateOptional.isPresent()) {
				sb.append(" toDate: \"");
				sb.append(_getDateTimeString(toDateOptional));
				sb.append(StringPool.QUOTE);
			}

			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		graphQLRequest.setQuery(sb.toString());

		return graphQLRequest;
	}

	private ResponseEntity<String> _getResponseEntity(
			FaroProject faroProject, GraphQLRequest graphQLRequest)
		throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.setInterceptors(
			Collections.singletonList(
				new AuthenticationClientHttpRequestInterceptor(faroProject)));

		return restTemplate.exchange(
			EngineServiceURLUtil.getBackendURL(faroProject, "/graphql"),
			HttpMethod.POST, new HttpEntity<>(graphQLRequest), String.class);
	}

	private GraphQLRequest _getTimeZoneGraphQLRequestHttpEntity(String value) {
		GraphQLRequest graphQLRequest = new GraphQLRequest();

		graphQLRequest.setOperationName("TimeZone");
		graphQLRequest.setQuery(
			"mutation TimeZone($value: String!) {timeZone(value: $value)}");
		graphQLRequest.setVariables(Collections.singletonMap("value", value));

		return graphQLRequest;
	}

	@Reference
	private JSONFactory _jsonFactory;

}