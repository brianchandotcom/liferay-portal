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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterReceiver;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.List;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

/**
 * @author Tina Tian
 */
public class JGroupsReceiver extends ReceiverAdapter {

	public JGroupsReceiver(ClusterReceiver clusterReceiver) {
		if (clusterReceiver == null) {
			throw new NullPointerException("Cluster receiver is null");
		}

		_clusterReceiver = clusterReceiver;
	}

	@Override
	public void receive(Message message) {
		Object object = message.getObject();

		if (object == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Message content is null");
			}

			return;
		}

		_clusterReceiver.receive(
			object, _wrapJGroupsAddress(message.getSrc()),
			_wrapJGroupsAddress(message.getDest()));
	}

	@Override
	public void viewAccepted(View view) {
		if (_log.isInfoEnabled()) {
			_log.info("Accepted view " + view);
		}

		List<Address> members = new ArrayList<>();

		for (org.jgroups.Address address : view.getMembers()) {
			members.add(new AddressImpl(address));
		}

		_clusterReceiver.addressesUpdated(members);
	}

	private Address _wrapJGroupsAddress(org.jgroups.Address address) {
		if (address == null) {
			return null;
		}

		return new AddressImpl(address);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JGroupsReceiver.class);

	private final ClusterReceiver _clusterReceiver;

}