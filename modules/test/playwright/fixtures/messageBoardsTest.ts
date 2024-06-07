/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {MessageBoardsEditThreadPage} from '../pages/message-boards/MessageBoardsEditThreadPage';
import {MessageBoardsPage} from '../pages/message-boards/MessageBoardsPage';
import {MessageBoardsWidgetPage} from '../pages/message-boards/MessageBoardsWidgetPage';

const messageBoardsPagesTest = test.extend<{
	messageBoardsEditThreadPage: MessageBoardsEditThreadPage;
	messageBoardsPage: MessageBoardsPage;
	messageBoardsWidgetPage: MessageBoardsWidgetPage;
}>({
	messageBoardsEditThreadPage: async ({page}, use) => {
		await use(new MessageBoardsEditThreadPage(page));
	},
	messageBoardsPage: async ({page}, use) => {
		await use(new MessageBoardsPage(page));
	},
	messageBoardsWidgetPage: async ({page}, use) => {
		await use(new MessageBoardsWidgetPage(page));
	},
});

export {messageBoardsPagesTest};
