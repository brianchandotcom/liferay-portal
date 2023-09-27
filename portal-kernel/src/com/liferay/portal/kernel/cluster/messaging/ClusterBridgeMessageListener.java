/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterLinkUtil;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Shuyang Zhou
 */
public class ClusterBridgeMessageListener extends BaseMessageListener {

	public void setPriority(Priority priority) {
		_priority = priority;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		if (!ClusterInvokeThreadLocal.isEnabled()) {
			return;
		}

		Address address = ClusterLinkUtil.getAddress(message);

		if (address == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Bridging cluster link multicast message " + message);
			}

			ClusterLinkUtil.sendMulticastMessage(message, _priority);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Bridging cluster link unicast message ", message,
						" to ", address));
			}

			ClusterLinkUtil.sendUnicastMessage(address, message, _priority);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterBridgeMessageListener.class);

	private Priority _priority;

}