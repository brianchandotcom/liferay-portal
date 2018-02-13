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

package com.liferay.lcs.advisor;

import com.liferay.lcs.rest.client.LCSSubscriptionEntry;
import com.liferay.lcs.rest.client.LCSSubscriptionEntryClient;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.LCSPortletPreferencesUtil;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceSerializeException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Igor Beslic
 */
public class LCSPortletStateAdvisor {

	public LCSPortletState getLCSPortletState(boolean checkSubscription) {
		if (!_lcsConnectionManager.isLCSGatewayAvailable()) {
			return LCSPortletState.NO_CONNECTION;
		}

		LCSPortletState lcsPortletState = LCSPortletState.valueOf(
			LCSPortletPreferencesUtil.getValue(
				"lcsPortletState", LCSPortletState.NOT_REGISTERED.name()));

		if (!checkSubscription) {
			return lcsPortletState;
		}

		try {
			LCSSubscriptionEntry lcsSubscriptionEntry =
				_lcsSubscriptionEntryClient.fetchLCSSubscriptionEntry(
					_keyGenerator.getKey());

			if (lcsSubscriptionEntry == null) {
				return LCSPortletState.NO_SUBSCRIPTION;
			}

			return LCSPortletState.GOOD;
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (jsonwsie.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(8);

					sb.append("User permissions may be invalid. If ");
					sb.append("user who generated LCS activation token file ");
					sb.append("was removed from LCS project, or user ");
					sb.append("permissions were degraded, LCS portlet ");
					sb.append("will not be able to communicate to the LCS ");
					sb.append("platform after the server is rebooted or ");
					sb.append("after the LCS portlet is redeployed. The LCS ");
					sb.append("platform returned the following error ");
					sb.append("message: ");
					sb.append(jsonwsie.getMessage());

					_log.warn(sb.toString());
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Remote service unavailable", jsonwsie);
				}
				else {
					_log.error("Remote service unavailable");
				}
			}
		}
		catch (JSONWebServiceSerializeException jsonwsse) {
			if (_log.isDebugEnabled()) {
				_log.debug("Remote service unavailable", jsonwsse);
			}
			else {
				_log.error("Remote service unavailable");
			}
		}
		catch (JSONWebServiceTransportException jsonwste) {
			String message = jsonwste.getMessage();

			if ((message != null) && message.contains("Not authorized")) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(8);

					sb.append("OAuth credentials may be invalid. If ");
					sb.append("credentials were revoked, the LCS portlet ");
					sb.append("will not be able to communicate to the LCS ");
					sb.append("platform after the server is rebooted or ");
					sb.append("after the LCS portlet is redeployed. The LCS ");
					sb.append("platform returned the following error ");
					sb.append("message: ");
					sb.append(message);

					_log.warn(sb.toString());
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Remote service unavailable", jsonwste);
				}
				else {
					_log.error("Remote service unavailable");
				}
			}
		}

		return lcsPortletState;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	public void setLCSSubscriptionEntryClient(
		LCSSubscriptionEntryClient lcsSubscriptionEntryClient) {

		_lcsSubscriptionEntryClient = lcsSubscriptionEntryClient;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSPortletStateAdvisor.class);

	private KeyGenerator _keyGenerator;
	private LCSConnectionManager _lcsConnectionManager;
	private LCSSubscriptionEntryClient _lcsSubscriptionEntryClient;

}