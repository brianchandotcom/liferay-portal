/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

import MostActiveVisitors from '../../../src/main/resources/META-INF/resources/js/main_view/analytics/components/MostActiveVisitors';

const mockData = [
	{
		activitiesCount: 150,
		emailAddress: 'john.doe@liferay.com',
		firstName: 'John',
		lastName: 'Doe',
		logoURL: 'https://test.com/logo.png',
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

describe('MostActiveVisitors', () => {
	beforeEach(() => {
		jest.fn();
	});

	afterEach(() => {
		cleanup();
		jest.clearAllMocks();
	});

	it('renders the component with provided data', () => {
		const {baseElement} = render(
			<MostActiveVisitors items={mockData} namespace="test-namespace" />
		);

		expect(baseElement).toMatchSnapshot();

		expect(screen.getByText('John')).toBeInTheDocument();
		expect(screen.getByText('Doe')).toBeInTheDocument();
		expect(screen.getByText('150')).toBeInTheDocument();
		expect(screen.getByText('actions')).toBeInTheDocument();
		expect(screen.getByText('john.doe@liferay.com')).toBeInTheDocument();
	});

	it('renders correctly without a logoURL', () => {
		const dataWithoutLogo = [
			{
				activitiesCount: 42,
				emailAddress: 'jane.smith@liferay.com',
				firstName: 'Jane',
				lastName: 'Smith',
				logoURL: undefined,
			},
		];

		render(
			<MostActiveVisitors
				items={dataWithoutLogo}
				namespace="test-namespace"
			/>
		);

		expect(screen.getByText('Jane')).toBeInTheDocument();
		expect(screen.getByText('Smith')).toBeInTheDocument();
		expect(screen.getByText('42')).toBeInTheDocument();
		expect(screen.getByText('jane.smith@liferay.com')).toBeInTheDocument();
	});
});
