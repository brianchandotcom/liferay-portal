/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

import LatestActivity from '../../../src/main/resources/META-INF/resources/js/main_view/analytics/components/LatestActivity';

const mockData = [
	{
		action: 'created a new document',
		createDate: '2026-03-26T14:30:00Z',
		logoURL: 'https://test.com/logo.png',
		name: 'John Doe',
	},
];

const mockLiferayLanguageGet = jest.fn((key: string) => {
	return key;
});

(global as any).Liferay = {
	...(global as any).Liferay,
	Language: {
		...(global as any).Liferay.Language,
		get: mockLiferayLanguageGet,
	},
};

jest.mock('moment/moment', () => {
	return () => ({
		fromNow: () => '2 hours ago',
	});
});

describe('LatestActivity', () => {
	beforeEach(() => {
		jest.fn();
	});

	afterEach(() => {
		cleanup();
		jest.clearAllMocks();
	});

	it('renders the component with provided data', () => {
		const {baseElement} = render(
			<LatestActivity items={mockData} namespace="test-namespace" />
		);

		expect(baseElement).toMatchSnapshot();

		expect(screen.getByText('John Doe')).toBeInTheDocument();
		expect(screen.getByText('created a new document')).toBeInTheDocument();
	});

	it('renders the correct timestamp representation from moment', () => {
		render(<LatestActivity items={mockData} namespace="test-namespace" />);

		expect(screen.getByText('2 hours ago')).toBeInTheDocument();
	});

	it('renders correctly without a logoURL', () => {
		const dataWithoutLogo = [
			{
				action: 'updated a document',
				createDate: '2026-03-26T14:30:00Z',
				name: 'Jane Doe',
			},
		];

		render(
			<LatestActivity
				items={dataWithoutLogo}
				namespace="test-namespace"
			/>
		);

		expect(screen.getByText('Jane Doe')).toBeInTheDocument();
		expect(screen.getByText('updated a document')).toBeInTheDocument();
	});
});
