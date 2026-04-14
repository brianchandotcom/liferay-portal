/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

import DocumentsStatistics from '../../../src/main/resources/META-INF/resources/js/main_view/analytics/components/RoomDocumentsStatistics';

const mockLiferayLanguageGet = jest.fn((key: string) => {
	if (key === '1-hour') {
		return '1 hour';
	}

	if (key === '1-minute') {
		return '1 minute';
	}

	if (key === 'x-hours') {
		return 'x hours';
	}

	if (key === 'x-minutes') {
		return 'x minutes';
	}

	if (key === 'x-users') {
		return 'x users';
	}

	return key;
});

(global as any).Liferay = {
	...(global as any).Liferay,
	Language: {
		...(global as any).Liferay.Language,
		get: mockLiferayLanguageGet,
	},
};

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	sub: (str: string, ...args: any[]) => {
		args.forEach((arg) => {
			str = str.replace('x', String(arg));
		});

		return str;
	},
}));

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/common/hooks/useIsInViewport',
	() => ({
		__esModule: true,
		default: jest.fn(() => true),
	})
);

describe('RoomDocumentsStatistics', () => {
	beforeEach(() => {
		jest.fn();
	});

	afterEach(() => {
		cleanup();
		jest.clearAllMocks();
	});

	it('renders the component with provided data', () => {
		const {baseElement} = render(
			<DocumentsStatistics
				dsrDevEnvEnabled={true}
				namespace="test-namespace"
			/>
		);

		expect(baseElement).toMatchSnapshot();

		expect(screen.getByText('pdf_test')).toBeInTheDocument();
		expect(screen.getByText('89')).toBeInTheDocument();
		expect(screen.getAllByText('324')[0]).toBeInTheDocument();
	});

	it('renders the correct average time', () => {
		render(
			<DocumentsStatistics
				dsrDevEnvEnabled={true}
				namespace="test-namespace"
			/>
		);

		expect(screen.getByText('1 hour 33 minutes')).toBeInTheDocument();
	});

	it('renders the correct last viewed date', () => {
		render(
			<DocumentsStatistics
				dsrDevEnvEnabled={true}
				namespace="test-namespace"
			/>
		);

		const count = screen.getAllByText('Mar 3, 2026');

		expect(count.length).toBe(2);
	});

	it('renders the correct user involved count', () => {
		render(
			<DocumentsStatistics
				dsrDevEnvEnabled={true}
				namespace="test-namespace"
			/>
		);

		const count = screen.getAllByText('4 users');

		expect(count.length).toBe(3);
	});

	it('handles duplicate users in user involved count', () => {
		render(
			<DocumentsStatistics
				dsrDevEnvEnabled={true}
				namespace="test-namespace"
			/>
		);

		expect(screen.getByText('2 users')).toBeInTheDocument();
	});
});
