/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import normalizeI18n from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/normalizeI18n';

describe('normalizeI18n', () => {
	it('normalizes name with "-"', () => {
		const result = normalizeI18n({'ca-ES': 'name1', 'en-US': 'name1'});

		expect(result).toStrictEqual({ca_ES: 'name1', en_US: 'name1'});
	});

	it('normalizes name with "_"', () => {
		const result = normalizeI18n({ca_ES: 'name1', en_US: 'name1'});

		expect(result).toStrictEqual({ca_ES: 'name1', en_US: 'name1'});
	});
});
