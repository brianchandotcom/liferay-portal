/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.portal.kernel.cluster.Address;

import java.util.List;

/**
 * @author Tina Tian
 */
public interface ClusterReceiver {

	public void addressesUpdated(List<Address> addresses);

	public void coordinatorAddressUpdated(Address coordinatorAddress);

	public List<Address> getAddresses();

	public Address getCoordinatorAddress();

	public void openLatch();

	public void receive(Object messagePayload, Address srcAddress);

}