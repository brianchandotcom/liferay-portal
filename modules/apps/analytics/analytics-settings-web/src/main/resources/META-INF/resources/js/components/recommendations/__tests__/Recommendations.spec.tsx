/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetch from 'jest-fetch-mock';

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import React from 'react';

import {loadingElement} from '../../../utils/__tests__/helpers';
import Recommendations from '../Recommendations';

describe('Recommendations', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('renders recommendations table', async () => {
		fetch.mockResponseOnce(
			JSON.stringify({
				contentRecommenderMostPopularItemsEnabled: true,
				contentRecommenderUserPersonalizationEnabled: true,
			})
		);

		const {getAllByText, getByText} = render(<Recommendations />);

		await loadingElement();

		expect(getByText('most-popular-content')).toBeInTheDocument();
		expect(
			getByText(
				'recommends-content-based-on-popularity-among-all-users-without-considering-individual-user-behavior'
			)
		).toBeInTheDocument();

		expect(
			getByText(
				'recommends-content-based-on-individual-users-preferences-and-past-behavior'
			)
		).toBeInTheDocument();
		expect(
			getByText(
				'recommends-content-based-on-popularity-among-all-users-without-considering-individual-user-behavior'
			)
		).toBeInTheDocument();

		expect(getAllByText('content')).toHaveLength(2);
	});
});
