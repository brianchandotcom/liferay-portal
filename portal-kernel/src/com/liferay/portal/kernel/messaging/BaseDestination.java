/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging;

import com.liferay.petra.string.StringPool;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public abstract class BaseDestination implements Destination {

	@Override
	public void close() {
		close(false);
	}

	@Override
	public void close(boolean force) {
	}

	@Override
	public void destroy() {
		close(true);
	}

	@Override
	public DestinationStatistics getDestinationStatistics() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDestinationType() {
		return _destinationType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void open() {
	}

	@Override
	public void send(Message message) {
		throw new UnsupportedOperationException();
	}

	public void setDestinationType(String destinationType) {
		_destinationType = destinationType;
	}

	public void setMessageListenerRegistry(
		MessageListenerRegistry messageListenerRegistry) {

		this.messageListenerRegistry = messageListenerRegistry;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected MessageListenerRegistry messageListenerRegistry;
	protected String name = StringPool.BLANK;

	private String _destinationType;

}