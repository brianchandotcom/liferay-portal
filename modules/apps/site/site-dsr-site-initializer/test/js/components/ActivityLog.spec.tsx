/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

import ActivityLog from '../../../src/main/resources/META-INF/resources/js/main_view/analytics/components/ActivityLog';

const mockLiferayLanguageGet = jest.fn((key: string) => {
	if (key === 'commented-on') {
		return 'Commented on';
	}
	if (key === 'uploaded-a-x') {
		return 'Uploaded a {0}';
	}
	if (key === 'viewed-a-x') {
		return 'Viewed a {0}';
	}

	return key;
});

(global as any).Liferay = {
	Language: {
		get: mockLiferayLanguageGet,
	},
	ThemeDisplay: {
		...global.Liferay?.ThemeDisplay,
		getBCP47LanguageId: () => 'en-US',
	},
};

const {Liferay: originalLiferay} = global.window;

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	sub: (str: string, arg: string) => str.replace('{0}', arg),
}));

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/common/hooks/useIsInViewport',
	() => ({
		__esModule: true,
		default: jest.fn(() => true),
	})
);

describe('ActivityLog Component', () => {
	beforeAll(() => {
		window['Liferay'] = {
			...originalLiferay,

			detach: (name, fn): void => {
				window.removeEventListener(name as string, fn as EventListener);
			},
			fire: (name, payload) => {
				const event = document.createEvent('CustomEvent');

				event.initCustomEvent(name);

				if (payload) {
					Object.keys(payload).forEach((key: string) => {
						(event as any)[key] = payload[key];
					});
				}

				window.dispatchEvent(event);
			},
			on: (name, fn) => {
				if (fn) {
					window.addEventListener(
						name as string,
						fn as EventListener
					);
				}

				return {
					detach: () => {
						if (fn) {
							window.removeEventListener(
								name as string,
								fn as EventListener
							);
						}

						return 0;
					},
				};
			},
		};
	});

	afterAll(() => {
		cleanup();

		window.Liferay = originalLiferay;

		jest.resetAllMocks();
	});

	afterEach(() => {
		cleanup();
	});

	it('renders the correct number of date headers', () => {
		render(<ActivityLog dsrDevEnvEnabled={true} />);

		expect(screen.getByText('2026-03-06')).toBeInTheDocument();
		expect(screen.getByText('2026-03-07')).toBeInTheDocument();
	});

	it('groups consecutive logs for the same user', () => {
		render(<ActivityLog dsrDevEnvEnabled={true} />);

		const userNames = screen.getAllByText('John Doe');

		expect(userNames.length).toBe(1);
	});

	it('displays localized labels using the sub utility', () => {
		render(<ActivityLog dsrDevEnvEnabled={true} />);

		expect(screen.getAllByText('Commented on')[0]).toBeInTheDocument();
		expect(
			screen.getAllByText('Uploaded a document')[0]
		).toBeInTheDocument();
		expect(screen.getAllByText('Viewed a tab')[0]).toBeInTheDocument();
	});

	it('renders descriptions only when they exist', () => {
		render(<ActivityLog dsrDevEnvEnabled={true} />);

		const descriptions = screen.getAllByText(/Lorem ipsum/i);

		expect(descriptions.length).toBe(6);
	});
});
