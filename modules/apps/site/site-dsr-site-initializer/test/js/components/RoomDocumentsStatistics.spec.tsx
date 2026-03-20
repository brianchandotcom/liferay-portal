/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

import DocumentsStatistics from '../../../src/main/resources/META-INF/resources/js/main_view/analytics/components/RoomDocumentsStatistics';

const mockData = [
	{
		download: 324,
		lastViewed: '2026-03-03T15:30:00Z',
		title: 'pdf_test',
		totalTimeViewingAsset: 500000,
		totalViews: 89,
		type: 'pdf',
		userInvolved: ['Sara', 'Lorenzo', 'Chiara', 'Mik'],
	},
];

const mockLiferayLanguageGet = jest.fn((key: string) => {
	if (key === 'x-hour') {
		return 'x hour';
	}

	if (key === 'x-hours') {
		return 'x hours';
	}

	if (key === 'x-minute') {
		return 'x minute';
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
			<DocumentsStatistics items={mockData} namespace="test-namespace" />
		);

		expect(baseElement).toMatchSnapshot();

		expect(screen.getByText('pdf_test')).toBeInTheDocument();
		expect(screen.getByText('89')).toBeInTheDocument();
		expect(screen.getByText('324')).toBeInTheDocument();
	});

	it('renders the correct average time', () => {
		render(
			<DocumentsStatistics items={mockData} namespace="test-namespace" />
		);

		expect(screen.getByText('1 hour 33 minutes')).toBeInTheDocument();
	});

	it('renders the correct last viewed date', () => {
		render(
			<DocumentsStatistics items={mockData} namespace="test-namespace" />
		);

		expect(screen.getByText('Mar 3, 2026')).toBeInTheDocument();
	});

	it('renders the correct user involved count', () => {
		render(
			<DocumentsStatistics items={mockData} namespace="test-namespace" />
		);

		expect(screen.getByText('4 users')).toBeInTheDocument();
	});

	it('handles duplicate users in user involved count', () => {
		const duplicateUserData = [
			{...mockData[0], userInvolved: ['Sara', 'Sara', 'Mik']},
		];

		render(
			<DocumentsStatistics
				items={duplicateUserData}
				namespace="test-namespace"
			/>
		);

		expect(screen.getByText('2 users')).toBeInTheDocument();
	});
});
