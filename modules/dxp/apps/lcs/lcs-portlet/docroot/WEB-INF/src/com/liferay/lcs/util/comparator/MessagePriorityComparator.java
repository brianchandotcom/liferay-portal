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

package com.liferay.lcs.util.comparator;

import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.Message;

import java.util.Comparator;

/**
 * @author Igor Beslic
 */
public class MessagePriorityComparator implements Comparator<Message> {

	@Override
	public int compare(Message message1, Message message2) {
		if (message1 instanceof CommandMessage) {
			CommandMessage commandMessage = (CommandMessage)message1;

			String commandType = commandMessage.getCommandType();

			if (commandType.equals(CommandMessage.COMMAND_TYPE_SEND_PATCHES) ||
				commandType.equals(
					CommandMessage.COMMAND_TYPE_SEND_PORTAL_PROPERTIES)) {

				return 1;
			}
		}

		return -1;
	}

}