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

package com.liferay.powwow.provider.bbb;

import com.liferay.petra.content.ContentUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowParticipantConstants;
import com.liferay.powwow.model.PowwowServer;
import com.liferay.powwow.provider.BasePowwowServiceProvider;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Calderon
 */
public class BBBPowwowServiceProvider extends BasePowwowServiceProvider {

	@Override
	public int getAddPowwowMeetingStrategy() {
		return ADD_POWWOW_MEETING_STRATEGY_LAZY;
	}

	@Override
	public List<String> getBrandingFeatures() {
		if (_brandingFeatures != null) {
			return _brandingFeatures;
		}

		List<String> brandingFeatures = new ArrayList<>();

		brandingFeatures.add("highest-security");
		brandingFeatures.add("supports-windows-mac-and-linux");
		brandingFeatures.add(
			"includes-audio-video-chat-screen-sharing-and-document-sharing");

		_brandingFeatures = brandingFeatures;

		return _brandingFeatures;
	}

	@Override
	public String getBrandingLabel() {
		return "private-solution";
	}

	@Override
	public Map<String, String> getIndexFields(PowwowMeeting powwowMeeting) {
		return Collections.<String, String>emptyMap();
	}

	@Override
	public String getPowwowServiceProviderKey() {
		return "bbb";
	}

	@Override
	public String getPowwowServiceProviderName() {
		return "BigBlueButton";
	}

	@Override
	public boolean isFieldSecretRequired() {
		return true;
	}

	@Override
	public boolean isFieldURLRequired() {
		return true;
	}

	@Override
	public boolean isServerActive(PowwowServer powwowServer) {
		try {
			Document document = execute(
				powwowServer, "getMeetings", StringPool.BLANK);

			Element element = document.getRootElement();

			String returnCode = getText(element, "returncode");

			if (returnCode.equals("SUCCESS")) {
				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	public boolean isSupportsPresettingParticipantName() {
		return true;
	}

	@Override
	protected Map<String, Serializable> addPowwowMeeting(
		User user, PowwowServer powwowServer, long powwowMeetingId, String name,
		Map<String, String> options) {

		StringBundler sb = new StringBundler(6);

		sb.append("meetingID=");
		sb.append(powwowMeetingId);
		sb.append("&name=");
		sb.append(HtmlUtil.escapeURL(name));
		sb.append("&welcome=");

		String welcomeMessage = ContentUtil.get(
			BBBPowwowServiceProvider.class.getClassLoader(),
			"com/liferay/powwow/dependencies/meeting_welcome_message.tmpl");

		sb.append(HtmlUtil.escapeURL(welcomeMessage));

		Document document = execute(powwowServer, "create", sb.toString());

		Element element = document.getRootElement();

		String returnCode = getText(element, "returncode");

		if (returnCode.equals("FAILED")) {
			throw new SystemException("Unable to add BBB meeting");
		}

		Map<String, Serializable> providerTypeMetadataMap = new HashMap<>();

		providerTypeMetadataMap.put(
			"attendeePW", getText(element, "attendeePW"));
		providerTypeMetadataMap.put(
			"moderatorPW", getText(element, "moderatorPW"));

		return providerTypeMetadataMap;
	}

	@Override
	protected boolean deletePowwowMeeting(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting) {

		return endPowwowMeeting(powwowServer, powwowMeeting);
	}

	@Override
	protected boolean endPowwowMeeting(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting) {

		StringBundler sb = new StringBundler(4);

		sb.append("meetingID=");
		sb.append(powwowMeeting.getPowwowMeetingId());
		sb.append("&password=");

		Map<String, Serializable> providerTypeMetadataMap =
			powwowMeeting.getProviderTypeMetadataMap();

		sb.append(
			HtmlUtil.escapeURL(
				String.valueOf(providerTypeMetadataMap.get("moderatorPW"))));

		Document document = execute(powwowServer, "end", sb.toString());

		Element element = document.getRootElement();

		String messageKey = getText(element, "messageKey");

		if (messageKey.equals("sentEndMeetingRequest")) {
			return true;
		}

		return false;
	}

	protected Document execute(
		PowwowServer powwowServer, String action, String queryString) {

		try {
			String url = getURL(powwowServer, action, queryString);

			Http.Options options = new Http.Options();

			options.addHeader("Content-Type", "text/xml");
			options.setLocation(url);

			String response = sendRequest(options);

			Document document = SAXReaderUtil.read(response);

			Element element = document.getRootElement();

			String returnCode = getText(element, "returncode");

			if (!returnCode.equals("FAILED") && !returnCode.equals("SUCCESS")) {
				throw new SystemException(
					"Invalid response from BBB Server: " + response);
			}

			return document;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	protected long getJoinByPhoneAccessCode(PowwowMeeting powwowMeeting) {
		return 0;
	}

	@Override
	protected String getJoinPowwowMeetingURL(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting, String name,
		int type) {

		StringBundler sb = new StringBundler(6);

		sb.append("fullName=");
		sb.append(name);
		sb.append("&meetingID=");
		sb.append(powwowMeeting.getPowwowMeetingId());
		sb.append("&password=");

		Map<String, Serializable> providerTypeMetadataMap =
			powwowMeeting.getProviderTypeMetadataMap();

		if (type == PowwowParticipantConstants.TYPE_HOST) {
			sb.append(
				HtmlUtil.escapeURL(
					String.valueOf(
						providerTypeMetadataMap.get("moderatorPW"))));
		}
		else {
			sb.append(
				HtmlUtil.escapeURL(
					String.valueOf(providerTypeMetadataMap.get("attendeePW"))));
		}

		return getURL(powwowServer, "join", sb.toString());
	}

	@Override
	protected boolean getOptionAutoStartVideo(PowwowMeeting powwowMeeting) {
		return false;
	}

	@Override
	protected String getOptionPassword(PowwowMeeting powwowMeeting) {
		return StringPool.BLANK;
	}

	protected String getText(Element parentElement, String name) {
		Element element = parentElement.element(name);

		if (element == null) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(element.getText());
	}

	protected String getURL(
		PowwowServer powwowServer, String action, String queryString) {

		StringBundler sb = new StringBundler(7);

		sb.append(powwowServer.getUrl());
		sb.append(action);
		sb.append(StringPool.QUESTION);
		sb.append(queryString);

		if (!queryString.equals(StringPool.BLANK)) {
			sb.append("&");
		}

		sb.append("checksum=");

		String checksum = DigesterUtil.digestHex(
			Digester.SHA_1, action + queryString + powwowServer.getSecret());

		sb.append(checksum);

		return sb.toString();
	}

	@Override
	protected boolean isPowwowMeetingCreated(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting) {

		StringBundler sb = new StringBundler(4);

		sb.append("meetingID=");
		sb.append(powwowMeeting.getPowwowMeetingId());
		sb.append("&password=");

		Map<String, Serializable> providerTypeMetadataMap =
			powwowMeeting.getProviderTypeMetadataMap();

		sb.append(
			HtmlUtil.escapeURL(
				String.valueOf(providerTypeMetadataMap.get("moderatorPW"))));

		Document document = execute(
			powwowServer, "getMeetingInfo", sb.toString());

		Element element = document.getRootElement();

		String returnCode = getText(element, "returncode");

		if (returnCode.equals("FAILED")) {
			String messageKey = getText(element, "messageKey");

			if (messageKey.equals("notFound")) {
				return false;
			}

			throw new SystemException(
				"Invalid response from BBB Server: " + document.asXML());
		}

		String createTime = getText(element, "createTime");

		if (createTime.equals(StringPool.BLANK)) {
			return false;
		}

		return true;
	}

	@Override
	protected boolean isPowwowMeetingRunning(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting) {

		try {
			Document document = execute(
				powwowServer, "isMeetingRunning",
				"meetingID=" + powwowMeeting.getPowwowMeetingId());

			Element element = document.getRootElement();

			String returnCode = getText(element, "returncode");

			if (returnCode.equals("FAILED")) {
				return false;
			}

			String running = getText(element, "running");

			return GetterUtil.getBoolean(running);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	protected Map<String, Serializable> updatePowwowMeeting(
		PowwowServer powwowServer, PowwowMeeting powwowMeeting, String name,
		User user, Map<String, String> options) {

		if (isPowwowMeetingCreated(powwowServer, powwowMeeting) &&
			!endPowwowMeeting(powwowServer, powwowMeeting)) {

			throw new SystemException("Unable to update BBB meeting");
		}

		return addPowwowMeeting(
			user, powwowServer, powwowMeeting.getPowwowMeetingId(), name,
			options);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BBBPowwowServiceProvider.class);

	private List<String> _brandingFeatures;

}