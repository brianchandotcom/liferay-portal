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

import fetcher from '../fetcher';
import {Liferay} from '../liferay';

class LiferayMessageBoardImpl {
	public getMessagesByThreadIdURL(threadId: number) {
		return `/message-board-threads/${threadId}/message-board-messages`;
	}

	public getMessagesIdURL(messageId: number) {
		return `/message-board-messages/${messageId}`;
	}

	public createMbThread(threadName: string) {
		return fetcher.post(
			`/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/message-board-threads`,
			{
				articleBody: threadName,
				headline: threadName,
			}
		);
	}

	public createMbMessage(message: string, threadId: number) {
		return fetcher.post(
			`/message-board-threads/${threadId}/message-board-messages`,
			{
				articleBody: message,
				encodingFormat: 'html',
				headline: message,
			}
		);
	}
}

const liferayMessageBoardImpl = new LiferayMessageBoardImpl();

export {liferayMessageBoardImpl};
