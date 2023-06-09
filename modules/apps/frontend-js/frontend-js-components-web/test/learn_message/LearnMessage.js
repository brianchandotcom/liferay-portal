/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {render} from '@testing-library/react';
import React from 'react';

import LearnMessage, {
	LearnResourcesContext,
} from '../../src/main/resources/META-INF/resources/learn_message/LearnMessage';

const MESSAGE = 'Learn more.';
const URL =
	'https://learn.liferay.com/dxp/latest/en/using-search/search-pages-and-widgets/search-bar-suggestions.html';

const LEARN_RESOURCES_MOCK_DATA = {
	'portal-search-web': {
		'search-bar-suggestions': {
			en_US: {
				message: MESSAGE,
				url: URL,
			},
		},
	},
};

const LearnMessageWithContext = (props) => {
	return (
		<LearnResourcesContext.Provider value={LEARN_RESOURCES_MOCK_DATA}>
			<LearnMessage {...props} />
		</LearnResourcesContext.Provider>
	);
};

describe('LearnMessage', () => {
	it('displays the localized message string and url', () => {
		const {getByText} = render(
			<LearnMessageWithContext
				resource="portal-search-web"
				resourceKey="search-bar-suggestions"
			/>
		);

		const element = getByText(MESSAGE);

		expect(element).not.toBeNull();

		expect(element.href).toEqual(URL);
	});

	it('displays nothing if LearnResourcesContext is missing', () => {
		const {container} = render(
			<LearnMessage
				resource="portal-search-web"
				resourceKey="search-bar-suggestions"
			/>
		);

		expect(container.firstChild).toBeNull();
	});

	it('displays nothing if resource and resourceKey props are not defined', () => {
		const {container} = render(<LearnMessageWithContext />);

		expect(container.firstChild).toBeNull();
	});

	it('displays nothing if resource is not defined', () => {
		const {container} = render(
			<LearnMessageWithContext resourceKey="search-bar-suggestions" />
		);

		expect(container.firstChild).toBeNull();
	});

	it('displays nothing if resourceKey is not defined', () => {
		const {container} = render(
			<LearnMessageWithContext resource="portal-search-web" />
		);

		expect(container.firstChild).toBeNull();
	});
});
