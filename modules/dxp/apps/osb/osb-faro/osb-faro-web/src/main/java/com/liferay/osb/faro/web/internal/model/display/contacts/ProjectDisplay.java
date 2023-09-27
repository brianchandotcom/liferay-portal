/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.fasterxml.jackson.core.type.TypeReference;

import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.engine.client.CerebroEngineClient;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.provisioning.client.ProvisioningClient;
import com.liferay.osb.faro.provisioning.client.model.OSBAccountEntry;
import com.liferay.osb.faro.service.FaroProjectLocalServiceUtil;
import com.liferay.osb.faro.service.FaroUserLocalServiceUtil;
import com.liferay.osb.faro.web.internal.model.display.main.FaroSubscriptionDisplay;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.osb.faro.web.internal.util.TimeZoneUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ProjectDisplay {

	public ProjectDisplay() {
	}

	public ProjectDisplay(FaroProject faroProject) {
		this(faroProject, null);
	}

	public ProjectDisplay(
		FaroProject faroProject, CerebroEngineClient cerebroEngineClient,
		ContactsEngineClient contactsEngineClient,
		ProvisioningClient provisioningClient) {

		this(faroProject);

		_faroSubscriptionDisplay = getFaroSubscriptionDisplay(
			faroProject, cerebroEngineClient, contactsEngineClient,
			provisioningClient);
	}

	public ProjectDisplay(FaroProject faroProject, String friendlyURL) {
		_accountKey = faroProject.getAccountKey();
		_accountName = faroProject.getAccountName();
		_corpProjectName = faroProject.getCorpProjectName();
		_corpProjectUuid = faroProject.getCorpProjectUuid();

		try {
			_faroSubscriptionDisplay = JSONUtil.readValue(
				faroProject.getSubscription(), FaroSubscriptionDisplay.class);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		_friendlyURL = friendlyURL;

		_groupId = faroProject.getGroupId();

		try {
			_incidentReportEmailAddresses = JSONUtil.readValue(
				faroProject.getIncidentReportEmailAddresses(),
				new TypeReference<List<String>>() {
				});
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		_name = faroProject.getName();

		FaroUser faroUser = FaroUserLocalServiceUtil.fetchOwnerFaroUser(
			faroProject.getGroupId());

		if (faroUser != null) {
			_ownerEmailAddress = faroUser.getEmailAddress();
		}

		_recommendationsEnabled = faroProject.isRecommendationsEnabled();
		_serverLocation = faroProject.getServerLocation();
		_state = faroProject.getState();
		_timeZoneDisplay = TimeZoneUtil.getTimeZoneDisplay(
			faroProject.getTimeZoneId());
		_userId = faroProject.getUserId();
	}

	public ProjectDisplay(OSBAccountEntry osbAccountEntry) {
		_accountKey = osbAccountEntry.getDossieraAccountKey();
		_accountName = osbAccountEntry.getCorpEntryName();
		_corpProjectName = osbAccountEntry.getName();
		_corpProjectUuid = osbAccountEntry.getCorpProjectUuid();
		_faroSubscriptionDisplay = new FaroSubscriptionDisplay(osbAccountEntry);
	}

	public String getCorpProjectUuid() {
		return _corpProjectUuid;
	}

	public FaroSubscriptionDisplay getFaroSubscriptionDisplay() {
		return _faroSubscriptionDisplay;
	}

	public String getState() {
		return _state;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setStateEndDate(Date stateEndDate) {
		if (stateEndDate != null) {
			_stateEndDate = new Date(stateEndDate.getTime());
		}
	}

	public void setStateStartDate(Date stateStartDate) {
		if (stateStartDate != null) {
			_stateStartDate = new Date(stateStartDate.getTime());
		}
	}

	protected FaroSubscriptionDisplay getFaroSubscriptionDisplay(
		FaroProject faroProject, CerebroEngineClient cerebroEngineClient,
		ContactsEngineClient contactsEngineClient,
		ProvisioningClient provisioningClient) {

		FaroSubscriptionDisplay faroSubscriptionDisplay = null;

		if (Validator.isNotNull(faroProject.getCorpProjectUuid()) &&
			(Validator.isNull(faroProject.getSubscription()) ||
			 ((System.currentTimeMillis() - faroProject.getModifiedTime()) >
				 Time.DAY))) {

			try {
				faroSubscriptionDisplay = new FaroSubscriptionDisplay(
					provisioningClient.getOSBAccountEntry(
						faroProject.getCorpProjectUuid()));

				FaroProjectLocalServiceUtil.updateSubscription(
					faroProject.getFaroProjectId(),
					JSONUtil.writeValueAsString(faroSubscriptionDisplay));
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		if (faroSubscriptionDisplay == null) {
			try {
				faroSubscriptionDisplay = JSONUtil.readValue(
					faroProject.getSubscription(),
					FaroSubscriptionDisplay.class);
			}
			catch (Exception exception) {
				_log.error(exception);

				return null;
			}
		}

		try {
			faroSubscriptionDisplay.setCounts(
				faroProject, cerebroEngineClient, contactsEngineClient);
		}
		catch (Exception exception) {
			_log.error(exception);

			_state = FaroProjectConstants.STATE_UNAVAILABLE;
		}

		return faroSubscriptionDisplay;
	}

	private static final Log _log = LogFactoryUtil.getLog(ProjectDisplay.class);

	private String _accountKey;
	private String _accountName;
	private String _corpProjectName;
	private String _corpProjectUuid;
	private FaroSubscriptionDisplay _faroSubscriptionDisplay;
	private String _friendlyURL;
	private long _groupId;
	private List<String> _incidentReportEmailAddresses;
	private String _name;
	private String _ownerEmailAddress;
	private boolean _recommendationsEnabled;
	private String _serverLocation;
	private String _state;
	private Date _stateEndDate;
	private Date _stateStartDate;
	private TimeZoneDisplay _timeZoneDisplay;
	private long _userId;

}