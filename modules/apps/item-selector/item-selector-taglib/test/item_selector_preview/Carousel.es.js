/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import Carousel from '../../src/main/resources/META-INF/resources/item_selector_preview/js/Carousel.es';

const itemTitle = 'test image.jpg';
const itemUrl = 'image1.jpg';

const basicMetadata = {
	groups: [
		{
			data: [
				{
					key: Liferay.Language.get('format'),
					value: 'jpg',
				},
				{
					key: Liferay.Language.get('name'),
					value: 'test image.jpg',
				},
			],
			title: 'file-info',
		},
	],
};

const carouselProps = {
	currentItem: {
		metadata: JSON.stringify(basicMetadata),
		returntype: 'returntype',
		title: itemTitle,
		url: itemUrl,
		value: itemUrl,
	},
	handleClickNext: jest.fn(),
	handleClickPrevious: jest.fn(),
	isImage: true,
};

const renderCarouselComponent = (props) => render(<Carousel {...props} />);

describe('Carousel', () => {
	afterEach(cleanup);

	it('renders the Carousel component with the sidenav-container class', () => {
		const {container} = renderCarouselComponent(carouselProps);

		expect(container.firstChild.classList).toContain('sidenav-container');
	});

	it('renders sidenav content inside the sidenav container', () => {
		const {container} = renderCarouselComponent(carouselProps);

		expect(
			container.firstChild.querySelector('.sidenav-content')
		).not.toBeNull();
	});

	it('renders an image with alt attribute', () => {
		const {getByRole} = renderCarouselComponent(carouselProps);

		expect(getByRole('img').alt).toEqual(itemTitle);
	});
});

describe('Arrow elements', () => {
	afterEach(cleanup);

	it('renders left and right arrows as buttons', () => {
		const {getAllByRole} = renderCarouselComponent(carouselProps);

		const arrowButtons = getAllByRole('button');

		expect(arrowButtons.length).toBe(2);

		expect(arrowButtons[0].classList).toContain('icon-arrow');
	});

	it('does not render arrows if prop "showArrows" is false', () => {
		const {container} = renderCarouselComponent({
			...carouselProps,
			showArrows: false,
		});

		expect(container.querySelector('.icon-arrow')).toBeNull();
	});

	it('calls to handleClickPrevious when left arrow is clicked', () => {
		const {getAllByRole} = renderCarouselComponent(carouselProps);

		fireEvent.click(getAllByRole('button')[0]);

		expect(carouselProps.handleClickPrevious).toHaveBeenCalled();
	});
});

describe('Infopanel', () => {
	afterEach(cleanup);

	it('renders the Carousel component with the info panel closed', () => {
		const {container} = renderCarouselComponent(carouselProps);

		expect(container.firstChild.classList).toContain('closed');
	});

	it('renders item data on the info panel', () => {
		const {getByText} = renderCarouselComponent(carouselProps);

		expect(getByText(itemTitle)).toBeTruthy();
	});
});
