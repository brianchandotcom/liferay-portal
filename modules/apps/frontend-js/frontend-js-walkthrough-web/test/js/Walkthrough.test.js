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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render, screen} from '@testing-library/react';
import userEvents from '@testing-library/user-event';
import {navigate} from 'frontend-js-web';
import React from 'react';

import Walkthrough from '../../src/main/resources/META-INF/resources/Walkthrough';
import {
	BOX_SHADOW_ELEMENT_MOCK,
	DOM_STRUCTURE_FOR_PLACING_STEPS,
	INVALID_NODE_SELECTOR_MOCK,
	MULTI_PAGES_MOCK,
	PAGE_MOCK,
} from './__fixtures__/walkthroughMock';

function setupDocument() {
	const domStructureReference = document.createElement('div');

	domStructureReference.innerHTML = DOM_STRUCTURE_FOR_PLACING_STEPS;

	domStructureReference.id = 'test-structure';

	document.body.appendChild(domStructureReference);

	return () => {
		window.localStorage.clear();

		const nodeToBeRemoved = document.getElementById('test-structure');

		document.body.removeChild(nodeToBeRemoved);

		nodeToBeRemoved.remove();
	};
}

function renderWalkthrough(props) {
	return render(<Walkthrough {...props} />, {
		baseElement: document.getElementById('app_root'),
	});
}

jest.mock('frontend-js-web', () => ({
	navigate: jest.fn((url) => url),
}));

const USER_ID = 42;

describe('Walkthrough', () => {
	window.themeDisplay = {
		getLayoutRelativeURL: jest.fn(() => '/home'),
		getUserId: jest.fn(() => USER_ID),
	};

	afterEach(() => {
		cleanup();
	});

	beforeEach(() => {
		jest.restoreAllMocks();
	});

	it('renders', () => {
		const cleanUp = setupDocument();

		const {container} = renderWalkthrough(PAGE_MOCK);

		expect(container).toBeInTheDocument();

		cleanUp();
	});

	it(`when clicking on Next, it navigates to the given URL of 'next'`, () => {
		const cleanUp = setupDocument();

		const {getByLabelText} = renderWalkthrough(MULTI_PAGES_MOCK);

		const hotspot = getByLabelText('start-the-walkthrough');

		userEvents.click(hotspot);

		userEvents.click(screen.getByText('ok'));

		expect(navigate).toBeCalledWith(MULTI_PAGES_MOCK.steps[0].next);

		cleanUp();
	});

	xit(`when clicking on Previous, it navigates to the given url of 'previous'`, () => {
		const cleanUp = setupDocument();

		const {getByLabelText} = renderWalkthrough(MULTI_PAGES_MOCK);

		const hotspot = getByLabelText('start-the-walkthrough');

		userEvents.click(hotspot);

		userEvents.click(screen.getByText('ok'));

		// Change the location to '/home-2' and then make the Walkthrough render on this new page :/
		// I consider this inviable. Maybe we should force the step 2 to be on the current page but IDK
		// yet how to do it

		// userEvents.click(screen.getByText('previous'));
		// expect(navigate).toBeCalledWith(MULTI_PAGES_MOCK.steps[1].previous);

		cleanUp();
	});

	it(`when clicking on Next, without passing 'next', it shows the next Walkthrough without navigating`, () => {
		const cleanUp = setupDocument();

		const {getByLabelText} = renderWalkthrough(PAGE_MOCK);

		const hotspot = getByLabelText('start-the-walkthrough');

		userEvents.click(hotspot);

		const okButton = screen.getByText('ok');

		userEvents.click(okButton);

		expect(screen.queryByText('Hello2')).toBeInTheDocument();

		cleanUp();
	});

	it(`when clicking on Previous, without passing 'previous', it shows the previous Walkthrough without navigating`, () => {
		const cleanUp = setupDocument();

		const {getByLabelText} = renderWalkthrough(PAGE_MOCK);

		const hotspot = getByLabelText('start-the-walkthrough');

		userEvents.click(hotspot);

		const okButton = screen.getByText('ok');

		userEvents.click(okButton);

		expect(screen.queryByText('Hello2')).toBeInTheDocument();

		userEvents.click(screen.getByText('previous'));

		expect(screen.queryByText('Hello1')).toBeInTheDocument();

		cleanUp();
	});

	it('throws an error when the provided selector is not defined', () => {
		const cleanUp = setupDocument();

		const errorSpy = jest.spyOn(console, 'error').mockImplementation();

		renderWalkthrough(INVALID_NODE_SELECTOR_MOCK);

		expect(errorSpy).toHaveBeenCalled();
		expect(errorSpy).toHaveBeenCalledTimes(1);

		cleanUp();
	});

	it(`when 'darkbg' is set to false adds a 'lfr-walkthrough-element-shadow' to the nodeToHighlight'`, () => {
		const cleanUp = setupDocument();

		const {getByLabelText} = renderWalkthrough(BOX_SHADOW_ELEMENT_MOCK);

		const hotspot = getByLabelText('start-the-walkthrough');

		userEvents.click(hotspot);

		const elementToBeHighlighted = screen.getByTestId(
			'external-react-tree-button'
		);

		expect(elementToBeHighlighted).toHaveClass(
			'lfr-walkthrough-element-shadow'
		);

		cleanUp();
	});

	it(`given the following layoutRelativeURLs and the value of '/home' the component needs to be rendered`, () => {
		const POSSIBLE_LAYOUT_RELATIVE_URLS = [
			'/undefined',
			'/es/web/home/undefined',
			'/es/web/home?redirectURL=/es/web/guest/home/abc',
			'/home',
			'/es/home',
			'/es/web/guest/home',
			'/home/abc',
			'/es/home/abc',
			'/es/web/guest/home/abc',
			'/home/abc/home',
			'/es/home/abc/home',
			'/es/web/guest/home/abc/home',
		];

		POSSIBLE_LAYOUT_RELATIVE_URLS.forEach((url) => {
			const errorSpy = jest.spyOn(console, 'error').mockImplementation();

			window.themeDisplay.getLayoutRelativeURL = jest.fn(() => url);

			const cleanUp = setupDocument();

			const {container} = renderWalkthrough(PAGE_MOCK);

			expect(errorSpy).not.toHaveBeenCalled();
			expect(container).toBeInTheDocument();

			cleanUp();
		});
	});

	it('persists the current step of a determined page on localStorage', () => {
		const cleanUp = setupDocument();

		const {getByLabelText} = renderWalkthrough(PAGE_MOCK);

		const hotspot = getByLabelText('start-the-walkthrough');

		userEvents.click(hotspot);

		const okButton = screen.getByText('ok');

		userEvents.click(okButton);

		expect(
			localStorage.getItem(`${USER_ID}-walkthrough-popover-visible`)
		).toBe('true');

		expect(
			localStorage.getItem(`${USER_ID}-walkthrough-current-step`)
		).toBe('1');

		cleanUp();
	});
});
