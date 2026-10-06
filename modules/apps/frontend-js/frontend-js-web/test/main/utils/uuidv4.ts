/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import uuidv4 from '../../../src/main/resources/META-INF/resources/main/utils/uuidv4';

describe('uuidv4', () => {
	it('returns a version 4 UUID in canonical form', () => {
		expect(uuidv4()).toMatch(
			/^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/
		);
	});

	it('returns a different UUID on every call', () => {
		const uuids = new Set(Array.from({length: 1000}, uuidv4));

		expect(uuids.size).toBe(1000);
	});

	it('sets the version and variant bits on every UUID', () => {
		for (let i = 0; i < 1000; i++) {
			const uuid = uuidv4();

			expect(uuid[14]).toBe('4');
			expect('89ab').toContain(uuid[19]);
		}
	});
});
