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

package com.liferay.portal.kernel.pop;

import java.util.List;

import javax.mail.Message;

/**
 * @author Brian Wing Shun Chan
 */
public interface MessageListener {

	public boolean accept(
		String from, List<String> recipients, Message message);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #accept(String, List, Message)}
	 */
	@Deprecated
	public boolean accept(String from, String recipient, Message message);

	public void deliver(String from, List<String> recipients, Message message)
		throws MessageListenerException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #deliver(String, List, Message)}
	 */
	@Deprecated
	public void deliver(String from, String recipient, Message message)
		throws MessageListenerException;

	public String getId();

}