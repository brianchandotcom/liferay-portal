/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import EventScreen from '../../src/main/resources/META-INF/resources/screen/EventScreen';

describe('EventScreen', () => {
	beforeEach(() => {
		Liferay.SPA = {
			app: {
				timeout: 30000,
			},
		};
		Liferay.CSP = {};
	});

	const createScreen = (contentSecurityPolicy) => {
		const screen = new EventScreen();

		jest.spyOn(screen, 'getResponse').mockImplementation(() => {
			return {
				headers: {
					get: (name) => {
						if (name === 'content-security-policy') {
							return contentSecurityPolicy;
						}

						return null;
					},
				},
			};
		});

		return screen;
	};

	it('does not detect a mismatch when the response nonce matches the document nonce', () => {
		Liferay.CSP = {nonce: 'abc123'};

		const screen = createScreen(
			"default-src 'self'; script-src 'self' 'nonce-abc123'"
		);

		expect(screen.isContentSecurityPolicyMismatch()).toBe(false);
	});

	it('detects a mismatch when the response nonce differs from the document nonce', () => {
		Liferay.CSP = {nonce: 'abc123'};

		const screen = createScreen(
			"default-src 'self'; script-src 'self' 'nonce-xyz789'"
		);

		expect(screen.isContentSecurityPolicyMismatch()).toBe(true);
	});

	it('does not detect a mismatch when the response nonce keyword uses a different casing', () => {
		Liferay.CSP = {nonce: 'abc123'};

		const screen = createScreen(
			"default-src 'self'; script-src 'self' 'NONCE-abc123'"
		);

		expect(screen.isContentSecurityPolicyMismatch()).toBe(false);
	});

	it('detects a mismatch when navigating from a CSP page to an excluded page', () => {
		Liferay.CSP = {nonce: 'abc123'};

		const screen = createScreen(null);

		expect(screen.isContentSecurityPolicyMismatch()).toBe(true);
	});

	it('detects a mismatch when navigating from an excluded page to a CSP page', () => {
		Liferay.CSP = {nonce: ''};

		const screen = createScreen(
			"default-src 'self'; script-src 'self' 'nonce-abc123'"
		);

		expect(screen.isContentSecurityPolicyMismatch()).toBe(true);
	});

	it('does not detect a mismatch when neither page enforces CSP', () => {
		Liferay.CSP = {nonce: ''};

		const screen = createScreen(null);

		expect(screen.isContentSecurityPolicyMismatch()).toBe(false);
	});

	it('does not detect a mismatch when the response is not available', () => {
		Liferay.CSP = {nonce: 'abc123'};

		const screen = new EventScreen();

		jest.spyOn(screen, 'getResponse').mockImplementation(() => null);

		expect(screen.isContentSecurityPolicyMismatch()).toBe(false);
	});
});
