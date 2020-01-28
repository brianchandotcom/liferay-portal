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

import {getNumberOfWords} from '../../src/utils/assets';

describe('getNumberOfWords()', () => {
	let document;

	beforeEach(() => {
		document = global.document;
	});

	it('returns the number of words', () => {
		const content = {
			description:
				'Build portals, intranets, websites and connected experiences on the most flexible platform around.',
			title: 'Digital Experience Software Tailored to Your Needs'
		};

		const markup = `<header class="header">
							<h2>${content.title}</h2>
							<p>${content.description}</p>
						</header>`;

		const element = document.createElement('div');

		setInnerHTML(element, markup);

		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).toBe(20);
	});

	it('returns 0 if the number of words is empty', () => {
		const element = document.createElement('div');

		element.innerText = '';

		const numberOfWords = getNumberOfWords(element);

		expect(numberOfWords).toBe(0);
	});
});
