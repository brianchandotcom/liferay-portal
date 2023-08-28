/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.opensaml.saml.common.messaging.context.SAMLBindingContext;

/**
 * @author Michael Bowerman
 */
public class RelayStateUtil {

	public static String getRelayState(SAMLBindingContext samlBindingContext) {
		String relayState = _relayStates.remove(
			samlBindingContext.getRelayState());

		if ((relayState == null) && _log.isWarnEnabled()) {
			_log.warn(
				"Unable to get relay state for key " +
					samlBindingContext.getRelayState());
		}

		return relayState;
	}

	public static void setRelayState(
		SAMLBindingContext samlBindingContext, String relayState) {

		String uuid = PortalUUIDUtil.generate();

		_relayStates.put(uuid, relayState);

		samlBindingContext.setRelayState(uuid);
	}

	private static final Log _log = LogFactoryUtil.getLog(RelayStateUtil.class);

	private static final ConcurrentMap<String, String> _relayStates =
		new ConcurrentHashMap<>();

}