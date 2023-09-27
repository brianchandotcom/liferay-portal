/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ClusterForwardReceiver extends BaseClusterReceiver {

	public ClusterForwardReceiver(ClusterLinkImpl clusterLinkImpl) {
		super(clusterLinkImpl.getExecutorService());

		_clusterLinkImpl = clusterLinkImpl;
	}

	@Override
	protected void doReceive(Object messagePayload, Address srcAddress) {
		List<Address> localAddresses = _clusterLinkImpl.getLocalAddresses();

		if (localAddresses.contains(srcAddress)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Block received message " + messagePayload);
			}
		}
		else {
			_clusterLinkImpl.sendLocalMessage((Message)messagePayload);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterForwardReceiver.class);

	private final ClusterLinkImpl _clusterLinkImpl;

}