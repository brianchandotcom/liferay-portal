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

import BasicInformation from '../../../src/main/resources/META-INF/resources/js/components/BasicInformation';

import '@testing-library/jest-dom/extend-expect';

describe('BasicInformation', () => {
	afterEach(cleanup);

	it('renders author, publish date and title', () => {
		const testProps = {
			author: {
				authorId: '',
				name: 'John Tester',
				url: '',
			},
			canonicalURL:
				'http://localhost:8080/en/web/guest/-/basic-web-content',
			onSelectedLanguageClick: () => {},
			publishDate: 'Thu Feb 17 08:17:57 GMT 2020',
			title: 'A testing page',
			viewURLs: [
				{
					default: true,
					languageId: 'en-US',
					languageLabel: 'English (United States)',
					selected: true,
					viewURL:
						'http://localhost:8080/en/web/guest/-/basic-web-content',
				},
				{
					default: false,
					languageId: 'es-ES',
					languageLabel: 'Spanish (Spain)',
					selected: false,
					viewURL:
						'http://localhost:8080/es/web/guest/-/contenido-web-basico',
				},
			],
		};

		const {getByText} = render(<BasicInformation {...testProps} />);

		expect(getByText(testProps.title)).toBeInTheDocument();

		expect(getByText(testProps.canonicalURL)).toBeInTheDocument();

		const formattedPublishDate = 'February 17, 2020';
		expect(
			getByText('published-on-' + formattedPublishDate)
		).toBeInTheDocument();

		expect(
			getByText('authored-by-' + testProps.author.name)
		).toBeInTheDocument();
	});
});
