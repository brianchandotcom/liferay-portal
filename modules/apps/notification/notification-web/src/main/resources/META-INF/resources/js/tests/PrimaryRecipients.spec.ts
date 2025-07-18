/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';

import {
	getSubscribersDefaultRole,
	resetRecipientValue,
} from '../components/SettingsContainer/PrimaryRecipients';

describe('getSubscribersDefaultRole()', () => {
	it('returns [%EMAIL_RECIPIENT_ADDRESS%]', () => {
		expect(getSubscribersDefaultRole()).toBe('[%EMAIL_RECIPIENT_ADDRESS%]');
	});
});

describe('resetRecipientValue(value)', () => {
	it('returns [%EMAIL_RECIPIENT_ADDRESS%] if the recipient type is subscribers', () => {
		expect(resetRecipientValue('subscribers')).toBe(
			'[%EMAIL_RECIPIENT_ADDRESS%]'
		);
	});

	it('returns an empty array if the recipient type is not subscribers', () => {
		expect(resetRecipientValue('role')).toStrictEqual([]);
		expect(resetRecipientValue('email')).toStrictEqual([]);
	});
});
