/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {render} from '@testing-library/react';
import React from 'react';

import Keywords from '../../../src/main/resources/META-INF/resources/js/components/Keywords';

import '@testing-library/jest-dom/extend-expect';

describe('Keywords', () => {
	it('renders message no best keywords when content was published today', () => {
		const mockCurrentPage = {
			data: {
				countryKeywords: [
					{
						countryCode: 'us',
						countryName: 'United States',
						keywords: [],
					},
				],
				helpMessage:
					'This number refers to the volume of people that find your page through a search engine.',
				name: 'organic',
				share: 0,
				title: 'Organic',
				value: 0,
			},
			view: 'organic',
		};

		const {getByText} = render(<Keywords currentPage={mockCurrentPage} />);

		expect(getByText('there-are-no-best-keywords-yet')).toBeInTheDocument();
	});
});
