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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import BasicInformation from '../../../src/main/resources/META-INF/resources/js/components/BasicInformation.es';

import '@testing-library/jest-dom/extend-expect';

describe('BasicInformation', () => {
	afterEach(cleanup);

	it('renders author, publish date and title', () => {
		const testProps = {
			authorName: 'John Tester',
			publishDate: '13, July 2019',
			title: 'A testing page'
		};

		const {getByText} = render(
			<BasicInformation
				authorName={testProps.authorName}
				publishDate={testProps.publishDate}
				title={testProps.title}
			/>
		);

		expect(getByText(testProps.title)).toBeInTheDocument();

		expect(
			getByText('authored-by-' + testProps.authorName)
		).toBeInTheDocument();

		expect(
			getByText('published-on-' + testProps.publishDate)
		).toBeInTheDocument();
	});
});
