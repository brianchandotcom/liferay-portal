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

import launcher from '../../../src/main/resources/META-INF/resources/components/account_selector/entry';

import '../../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('account_selector', 'account-selector', {
	createNewOrderURL: '/asdasdasd',
	currentAccount: {
		id: 42332,
		name: 'My AccountName',
	},
	currentOrder: {
		id: '34234',
		orderStatusInfo: {
			label_i18n: 'Completed',
		},
	},
	selectOrderURL: '/test-url/{id}',
	setCurrentAccountURL: '/account-selector/setCurrentAccounts',
	spritemap: './assets/icons.svg',
});
