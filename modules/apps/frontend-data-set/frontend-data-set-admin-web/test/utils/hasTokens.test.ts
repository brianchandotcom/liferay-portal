/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import hasTokens from '../../src/main/resources/META-INF/resources/js/utils/hasTokens';

describe('hasTokens', () => {
	it('detects a token in the URL', () => {
		expect(
			hasTokens('/o/headless-delivery/v1.0/sites/{siteId}/keywords')
		).toBe(true);
		expect(
			hasTokens(
				'/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomy_vocabulary-id}/taxonomy-categories'
			)
		).toBe(true);
	});

	it('does not detect a token in a URL without one', () => {
		expect(hasTokens('/o/headless-admin-user/v1.0/sites')).toBe(false);
		expect(hasTokens(null)).toBe(false);
		expect(hasTokens(undefined)).toBe(false);
	});
});
