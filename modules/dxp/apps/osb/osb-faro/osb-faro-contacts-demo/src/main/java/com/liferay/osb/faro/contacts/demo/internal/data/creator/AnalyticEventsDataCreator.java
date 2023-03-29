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

package com.liferay.osb.faro.contacts.demo.internal.data.creator;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Matthew Kong
 */
public class AnalyticEventsDataCreator extends DataCreator {

	public AnalyticEventsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		PageContextsDataCreator pageContextsDataCreator) {

		super(
			contactsEngineClient, faroProject, "osbasahcerebroraw",
			"analytics-events");

		_createEvents();

		_pageContextsDataCreator = pageContextsDataCreator;
	}

	public long getActivitiesCount() {
		return _activitiesCount;
	}

	@Override
	protected void addData(List<Map<String, Object>> objects) {
		Http.Options options = new Http.Options();

		Map<String, String> headers = new HashMap<>();

		headers.put("Content-Type", ContentTypes.APPLICATION_JSON);
		headers.put("X-Forwarded-For", internet.publicIpV4Address());

		options.setHeaders(headers);

		options.setLocation(_OSB_ASAH_PUBLISHER_URL);
		options.setPost(true);

		for (Map<String, Object> object : objects) {
			try {
				options.setBody(
					_objectMapper.writeValueAsString(object),
					ContentTypes.APPLICATION_JSON,
					StandardCharsets.UTF_8.name());

				HttpUtil.URLtoString(options);
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> analyticEvent = new HashMap<>();

		Map<String, Object> context = new HashMap<>(
			_pageContextsDataCreator.getRandom());

		if (bool.bool()) {
			String encodePath = HttpUtil.encodePath(
				_SEARCH_TERMS[random.nextInt(_SEARCH_TERMS.length)]);

			context.put("url", context.get("url") + "?q=" + encodePath);
		}

		analyticEvent.put("context", context);

		analyticEvent.put("dataSourceId", params[0]);

		List<Map<String, Object>> events = new ArrayList<>();

		String eventDateString = formatDate(
			dateAndTime.past(30, TimeUnit.DAYS));

		if (bool.bool()) {
			Map<String, Object> assetEvent = new HashMap<>(
				_assetEvents.get(random.nextInt(_assetEvents.size())));

			assetEvent.put("eventDate", eventDateString);

			events.add(assetEvent);
		}

		Map<String, Object> pageEvent = new HashMap<>(_pageEvent);

		pageEvent.put("eventDate", eventDateString);

		events.add(pageEvent);

		analyticEvent.put("events", events);

		_activitiesCount += events.size();

		analyticEvent.put("protocolVersion", "1.0");

		Object userId = params[1];

		if (userId == null) {
			userId = number.randomNumber(8, false);
		}

		analyticEvent.put("userId", userId);

		return analyticEvent;
	}

	private Map<String, Object> _createEvent(
		String applicationId, String eventId, Map<String, Object> properties) {

		Map<String, Object> event = new HashMap<>();

		event.put("applicationId", applicationId);
		event.put("eventId", eventId);
		event.put("properties", properties);

		return event;
	}

	private void _createEvents() {
		for (String blogTitle : _BLOG_TITLES) {
			long blogEntryId = number.randomNumber(8, false);

			Map<String, Object> blogAssetEvent = new HashMap<String, Object>() {
				{
					put("classPK", blogEntryId);
					put("depth", number.randomNumber(2, false));
					put("entryId", blogEntryId);
					put("numberOfWords", number.randomNumber());
					put("score", number.randomDouble(1, 0, 1));
					put("title", blogTitle);
				}
			};

			_assetEvents.add(
				_createEvent("Blog", "blogDepthReached", blogAssetEvent));
			_assetEvents.add(
				_createEvent("Blog", "blogViewed", blogAssetEvent));
			_assetEvents.add(_createEvent("Blog", "posted", blogAssetEvent));
			_assetEvents.add(_createEvent("Blog", "VOTE", blogAssetEvent));
		}

		for (int i = 0; i < 10; i++) {
			Map<String, Object> documentAssetEvent =
				new HashMap<String, Object>() {
					{
						put("fileEntryId", number.randomNumber(8, false));
						put("fileEntryUUID", internet.uuid());
						put("title", company.bs());
						put("version", "1.0");
					}
				};

			_assetEvents.add(
				_createEvent(
					"Document", "documentDownloaded", documentAssetEvent));
			_assetEvents.add(
				_createEvent(
					"Document", "documentPreviewed", documentAssetEvent));
		}

		for (String formTitle : _FORM_TITLES) {
			Map<String, Object> formAssetEvent = new HashMap<String, Object>() {
				{
					put("formId", number.randomNumber(8, false));
					put("title", formTitle);
				}
			};

			_assetEvents.add(
				_createEvent("Form", "formSubmitted", formAssetEvent));
			_assetEvents.add(
				_createEvent("Form", "formViewed", formAssetEvent));
		}

		for (int i = 0; i < 10; i++) {
			_assetEvents.add(
				_createEvent(
					"WebContent", "webContentViewed",
					new HashMap<String, Object>() {
						{
							put("articleId", number.randomNumber(8, false));
							put("numberOfWords", number.randomNumber());
							put("title", company.bs());
						}
					}));
		}

		_pageEvent = _createEvent(
			"Page", "pageViewed",
			new HashMap<String, Object>() {
				{
					put("page", "/web/guest/home");
				}
			});
	}

	private static final String[] _BLOG_TITLES = {
		"How Automation Can Prevent Nutrient Deficiencies",
		"Vendor Highlight: Noble Irrigation Supply",
		"Vertical Farms, Vertical Profits", "Weathering the Drought",
		"Way Up North"
	};

	private static final String[] _FORM_TITLES = {
		"Beryl Irrigation Newsletter", "Beryl Processing Newsletter",
		"Beryl Promotions Newsletter", "Beryl Urban Farming Newsletter",
		"Check out", "Loyalty Program"
	};

	private static final String _OSB_ASAH_PUBLISHER_URL = System.getenv(
		"OSB_ASAH_PUBLISHER_URL");

	private static final String[] _SEARCH_TERMS = {
		"FF-2100 Owners Manual", "Hydroponics", "MX-350 Rebate",
		"MX-350 Truck Parts", "Tractor Leasing"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticEventsDataCreator.class);

	private long _activitiesCount;
	private final List<Map<String, Object>> _assetEvents = new ArrayList<>();
	private final ObjectMapper _objectMapper = new ObjectMapper();
	private final PageContextsDataCreator _pageContextsDataCreator;
	private Map<String, Object> _pageEvent = new HashMap<>();

}