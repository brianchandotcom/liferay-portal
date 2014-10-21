/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.poller;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.poller.PollerHeader;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.BrowserTrackerLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.util.Encryptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public class PollerRequestHandlerImpl
	implements PollerRequestHandler, MessageListener {

	@Override
	public PollerHeader getPollerHeader(String pollerRequestString) {
		if (Validator.isNull(pollerRequestString)) {
			return null;
		}

		Map<String, Object>[] pollerRequestChunks =
			parsePollerRequestParameters(pollerRequestString);

		return parsePollerRequestHeader(pollerRequestChunks);
	}

	@Override
	public JSONObject processRequest(
			HttpServletRequest request, String pollerRequestString)
		throws Exception {

		if (Validator.isNull(pollerRequestString)) {
			return null;
		}

		Map<String, Object>[] pollerRequestChunks =
			parsePollerRequestParameters(pollerRequestString);

		PollerHeader pollerHeader = parsePollerRequestHeader(
			pollerRequestChunks);

		if (!isValidPollerHeader(pollerHeader)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Invalid poller header for request " + pollerRequestString);
			}

			return null;
		}

		boolean receiveRequest = isReceiveRequest(request.getPathInfo());

		String pollerSessionId = getPollerSessionId(pollerHeader);

		PollerSession pollerSession = null;

		synchronized (_pollerSessions) {
			pollerSession = _pollerSessions.get(pollerSessionId);

			if ((pollerSession == null) && receiveRequest) {
				pollerSession = new PollerSession(pollerSessionId);

				_pollerSessions.put(pollerSessionId, pollerSession);
			}
		}

		List<PollerRequest> pollerRequests = createPollerRequests(
			pollerHeader, pollerRequestChunks, receiveRequest);

		executePollerRequests(pollerSession, pollerRequests);

		if (receiveRequest) {
			return createPollerResponseHeader(pollerHeader);
		}
		else {
			return null;
		}
	}

	@Override
	public void receive(Message message) {
		Object messagePayload = message.getPayload();

		if (!(messagePayload instanceof PollerResponse)) {
			return;
		}

		PollerResponse pollerResponse = (PollerResponse)messagePayload;

		PollerHeader pollerHeader = pollerResponse.getPollerHeader();

		String pollerSessionId = getPollerSessionId(pollerHeader);

		synchronized (_pollerSessions) {
			PollerSession pollerSession = _pollerSessions.get(pollerSessionId);

			if ((pollerSession != null) &&
				pollerSession.completePortletProcessing(
					pollerResponse.getPortletId(), message.getResponseId())) {

				_pollerSessions.remove(pollerSessionId);
			}
		}
	}

	protected PollerRequest createPollerRequest(
			PollerHeader pollerHeader, String portletId, boolean receiveRequest)
		throws Exception {

		return createPollerRequest(
			pollerHeader, portletId, new HashMap<String, String>(), null,
			receiveRequest);
	}

	protected PollerRequest createPollerRequest(
			PollerHeader pollerHeader, String portletId,
			Map<String, String> parameterMap, String chunkId,
			boolean receiveRequest)
		throws Exception {

		PollerProcessor pollerProcessor =
			PollerProcessorUtil.getPollerProcessor(portletId);

		if (pollerProcessor == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Poller processor not found for portlet " + portletId);
			}

			return null;
		}

		return new PollerRequest(
			pollerHeader, portletId, parameterMap, chunkId, receiveRequest);
	}

	protected List<PollerRequest> createPollerRequests(
			PollerHeader pollerHeader,
			Map<String, Object>[] pollerRequestChunks, boolean receiveRequest)
		throws Exception {

		Map<String, Boolean> portletIdsMap = pollerHeader.getPortletIdsMap();

		List<PollerRequest> pollerRequests = new ArrayList<PollerRequest>(
			portletIdsMap.size());

		Set<String> receiveRequestPortletIds = null;

		if (receiveRequest) {
			receiveRequestPortletIds = new HashSet<String>(
				(int)(pollerRequestChunks.length / 0.75) + 1);
		}

		for (int i = 1; i < pollerRequestChunks.length; i++) {
			Map<String, Object> pollerRequestChunk = pollerRequestChunks[i];

			String portletId = (String)pollerRequestChunk.get("portletId");
			Map<String, String> parameterMap = parseData(pollerRequestChunk);
			String chunkId = (String)pollerRequestChunk.get("chunkId");

			try {
				PollerRequest pollerRequest = createPollerRequest(
					pollerHeader, portletId, parameterMap, chunkId,
					receiveRequest);

				pollerRequests.add(pollerRequest);

				if (receiveRequest) {
					receiveRequestPortletIds.add(portletId);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (receiveRequest) {
			Set<String> portletIds = portletIdsMap.keySet();

			for (String portletId : portletIds) {
				if (receiveRequestPortletIds.contains(portletId)) {
					continue;
				}

				try {
					PollerRequest pollerRequest = createPollerRequest(
						pollerHeader, portletId, receiveRequest);

					pollerRequests.add(pollerRequest);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}

		return pollerRequests;
	}

	protected JSONObject createPollerResponseHeader(PollerHeader pollerHeader) {
		if (pollerHeader == null) {
			return null;
		}

		boolean suspendPolling = false;

		if (pollerHeader.isStartPolling()) {
			BrowserTrackerLocalServiceUtil.updateBrowserTracker(
				pollerHeader.getUserId(), pollerHeader.getBrowserKey());
		}
		else {
			BrowserTracker browserTracker =
				BrowserTrackerLocalServiceUtil.getBrowserTracker(
					pollerHeader.getUserId(), pollerHeader.getBrowserKey());

			if (browserTracker.getBrowserKey() !=
					pollerHeader.getBrowserKey()) {

				suspendPolling = true;
			}
		}

		JSONObject pollerResponseHeaderJSONObject =
			JSONFactoryUtil.createJSONObject();

		pollerResponseHeaderJSONObject.put("userId", pollerHeader.getUserId());
		pollerResponseHeaderJSONObject.put("suspendPolling", suspendPolling);

		return pollerResponseHeaderJSONObject;
	}

	protected void executePollerRequests(
		PollerSession pollerSession, List<PollerRequest> pollerRequests) {

		for (PollerRequest pollerRequest : pollerRequests) {
			String responseId = null;

			if (pollerRequest.isReceiveRequest()) {
				responseId = PortalUUIDUtil.generate();

				if (!pollerSession.beginPortletProcessing(
						pollerRequest, responseId)) {

					continue;
				}
			}

			Message message = new Message();

			message.setPayload(pollerRequest);

			if (pollerRequest.isReceiveRequest()) {
				message.setResponseId(responseId);

				message.setResponseDestinationName(
					DestinationNames.POLLER_RESPONSE);
			}

			MessageBusUtil.sendMessage(DestinationNames.POLLER, message);
		}
	}

	protected String fixPollerRequestString(String pollerRequestString) {
		if (Validator.isNull(pollerRequestString)) {
			return null;
		}

		return StringUtil.replace(
			pollerRequestString,
			new String[] {
				StringPool.OPEN_CURLY_BRACE, StringPool.CLOSE_CURLY_BRACE,
				_ESCAPED_OPEN_CURLY_BRACE, _ESCAPED_CLOSE_CURLY_BRACE
			},
			new String[] {
				_OPEN_HASH_MAP_WRAPPER, StringPool.DOUBLE_CLOSE_CURLY_BRACE,
				StringPool.OPEN_CURLY_BRACE, StringPool.CLOSE_CURLY_BRACE
			});
	}

	protected String getPollerSessionId(PollerHeader pollerHeader) {
		return String.valueOf(pollerHeader.getUserId());
	}

	protected long getUserId(long companyId, String userIdString) {
		long userId = 0;

		try {
			Company company = CompanyLocalServiceUtil.getCompany(companyId);

			userId = GetterUtil.getLong(
				Encryptor.decrypt(company.getKeyObj(), userIdString));
		}
		catch (Exception e) {
			_log.error(
				"Invalid credentials for company id " + companyId +
					" and user id " + userIdString);
		}

		return userId;
	}

	protected boolean isReceiveRequest(String path) {
		if ((path != null) && path.endsWith(_PATH_RECEIVE)) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isValidPollerHeader(PollerHeader pollerHeader) {
		if (pollerHeader == null) {
			return false;
		}

		Map<String, Boolean> portletIdsMap = pollerHeader.getPortletIdsMap();

		if ((portletIdsMap == null) || portletIdsMap.isEmpty()) {
			return false;
		}

		return true;
	}

	protected Map<String, String> parseData(
			Map<String, Object> pollerRequestChunk)
		throws Exception {

		Map<String, Object> oldParameterMap =
			(Map<String, Object>)pollerRequestChunk.get("data");

		Map<String, String> newParameterMap = new HashMap<String, String>();

		if (oldParameterMap == null) {
			return newParameterMap;
		}

		for (Map.Entry<String, Object> entry : oldParameterMap.entrySet()) {
			newParameterMap.put(
				entry.getKey(), String.valueOf(entry.getValue()));
		}

		return newParameterMap;
	}

	protected PollerHeader parsePollerRequestHeader(
		Map<String, Object>[] pollerRequestChunks) {

		if ((pollerRequestChunks == null) || (pollerRequestChunks.length < 1)) {
			return null;
		}

		Map<String, Object> pollerRequestChunk = pollerRequestChunks[0];

		long browserKey = GetterUtil.getLong(
			String.valueOf(pollerRequestChunk.get("browserKey")));
		long companyId = GetterUtil.getLong(
			String.valueOf(pollerRequestChunk.get("companyId")));
		Map<String, Boolean> portletIdsMap =
			(Map<String, Boolean>)pollerRequestChunk.get("portletIdsMap");
		boolean startPolling = GetterUtil.getBoolean(
			String.valueOf(pollerRequestChunk.get("startPolling")));
		String userIdString = GetterUtil.getString(
			String.valueOf(pollerRequestChunk.get("userId")));

		long userId = getUserId(companyId, userIdString);

		if (userId == 0) {
			return null;
		}

		return new PollerHeader(
			companyId, userId, browserKey, portletIdsMap, startPolling);
	}

	protected Map<String, Object>[] parsePollerRequestParameters(
		String pollerRequestString) {

		String fixedPollerRequestString = fixPollerRequestString(
			pollerRequestString);

		return (Map<String, Object>[])JSONFactoryUtil.deserialize(
			fixedPollerRequestString);
	}

	private static final String _ESCAPED_CLOSE_CURLY_BRACE =
		"[$CLOSE_CURLY_BRACE$]";

	private static final String _ESCAPED_OPEN_CURLY_BRACE =
		"[$OPEN_CURLY_BRACE$]";

	private static final String _OPEN_HASH_MAP_WRAPPER =
		"{\"javaClass\":\"java.util.HashMap\",\"map\":{";

	private static final String _PATH_RECEIVE = "/receive";

	private static Log _log = LogFactoryUtil.getLog(
		PollerRequestHandlerImpl.class);

	private Map<String, PollerSession> _pollerSessions =
		new HashMap<String, PollerSession>();

}