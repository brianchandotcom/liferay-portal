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

package com.liferay.powwow.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowServer;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Marco Calderon
 */
public interface PowwowServiceProvider {

	public static final int ADD_POWWOW_MEETING_STRATEGY_EAGER = 1;

	public static final int ADD_POWWOW_MEETING_STRATEGY_LAZY = 2;

	public Map<String, Serializable> addPowwowMeeting(
			long userId, long powwowServerId, long powwowMeetingId, String name,
			Map<String, String> startOptions)
		throws PortalException;

	public PowwowMeeting deletePowwowMeeting(long powwowMeetingId)
		throws PortalException;

	public PowwowMeeting endPowwowMeeting(long powwowMeetingId)
		throws PortalException;

	public int getAddPowwowMeetingStrategy();

	public List<String> getBrandingFeatures();

	public String getBrandingLabel();

	public Map<String, String> getIndexFields(long powwowMeetingId)
		throws PortalException;

	public long getJoinByPhoneAccessCode(long powwowMeetingId);

	public String getJoinByPhoneAccessCodeLabel();

	public List<String> getJoinByPhoneDefaultNumbers();

	public Map<String, List<String>> getJoinByPhoneInternationalNumbers();

	public String getJoinPowwowMeetingURL(
		long powwowMeetingId, String name, int type);

	public boolean getOptionAutoStartVideo(long powwowMeetingId)
		throws PortalException;

	public String getOptionPassword(long powwowMeetingId)
		throws PortalException;

	public long getPowwowServerId(String providerType);

	public String getPowwowServiceProviderKey();

	public String getPowwowServiceProviderName();

	public boolean isFieldAPIKeyRequired();

	public boolean isFieldSecretRequired();

	public boolean isFieldURLRequired();

	public boolean isPowwowMeetingCreated(long powwowMeetingId)
		throws PortalException;

	public boolean isPowwowMeetingRunning(long powwowMeetingId)
		throws PortalException;

	public boolean isServerActive(PowwowServer powwowServer);

	public boolean isSupportsJoinByPhone();

	public boolean isSupportsOptionAutoStartVideo();

	public boolean isSupportsOptionPassword();

	public boolean isSupportsPresettingParticipantName();

	public Map<String, Serializable> updatePowwowMeeting(
			long powwowMeetingId, String name, long userId,
			Map<String, String> startOptions)
		throws PortalException;

}