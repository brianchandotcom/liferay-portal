/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging;

/**
 * @author Shuyang Zhou
 */
public class DestinationWrapper implements Destination {

	public DestinationWrapper(Destination destination) {
		this.destination = destination;
	}

	@Override
	public void close() {
		destination.close();
	}

	@Override
	public void close(boolean force) {
		destination.close(force);
	}

	@Override
	public void destroy() {
		destination.destroy();
	}

	@Override
	public DestinationStatistics getDestinationStatistics() {
		return destination.getDestinationStatistics();
	}

	@Override
	public String getDestinationType() {
		return destination.getDestinationType();
	}

	@Override
	public String getName() {
		return destination.getName();
	}

	@Override
	public void open() {
		destination.open();
	}

	@Override
	public void send(Message message) {
		destination.send(message);
	}

	protected Destination destination;

}