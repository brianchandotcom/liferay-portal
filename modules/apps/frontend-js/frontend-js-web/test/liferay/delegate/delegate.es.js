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

import delegate from '../../../src/main/resources/META-INF/resources/liferay/delegate/delegate.es';
import {getByTestId} from '@testing-library/dom';
import userEvent from '@testing-library/user-event';

describe('delegate', function () {
	afterEach(() => {
		document.body.innerHTML = '';
	});

	it('should trigger delegate listener for matched elements', function () {
		document.body.innerHTML =
			'<div class="nomatch" data-testid="nomatch"></div>' +
			'<div class="match" data-testid="match"></div>';

		let listener = jest.fn();

		delegate(document, 'click', '.match', listener);

		userEvent.click(getByTestId(document, 'nomatch'));

		expect(listener).not.toHaveBeenCalled();

		userEvent.click(getByTestId(document, 'match'));

		expect(listener).toHaveBeenCalled();
	});

	it('should trigger delegate listener for element provided', function () {
		document.body.innerHTML = `<div class="nomatch" data-testid="nomatch"></div>
			<div class="match" data-testid="match"></div>`;

		let listener = jest.fn();

		delegate(document, 'click', document.querySelector('.match'), listener);

		userEvent.click(getByTestId(document, 'nomatch'));

		expect(listener).not.toHaveBeenCalled();

		userEvent.click(getByTestId(document, 'match'));

		expect(listener).toHaveBeenCalled();
	});

	it('should not trigger delegate event for parents of given element', function () {
		document.body.innerHTML = `<div class="match">
				<div data-testid="nomatch"></div>
			</div>`;

		let listener = jest.fn();

		delegate(document, 'click', '.match', listener);

		userEvent.click(getByTestId(document, 'nomatch'));

		expect(listener).not.toHaveBeenCalled();
	});

	it('should stop triggering event if stopPropagation is called', function () {
		document.body.innerHTML = `<div class="match" data-testid="match"></div>`;

		let listener = jest.fn();

		delegate(document, 'click', '.match', listener);

		expect(listener).not.toHaveBeenCalled();

		userEvent.click(getByTestId(document, 'match'));

		expect(listener).toHaveBeenCalledTimes(1);

		document
			.querySelector('.match')
			.addEventListener('click', (e) => e.stopPropagation());

		userEvent.click(getByTestId(document, 'match'));

		expect(listener).toHaveBeenCalledTimes(1);
	});

	it('should only trigger delegate event at initial target', function () {
		document.body.innerHTML = `<div>
			<div class="match" data-testid="match">
				<div class="match" data-testid="match2"></div>
			</div>
		</div>`;

		let listener = jest.fn();

		delegate(document, 'click', '.match', listener);

		userEvent.click(getByTestId(document, 'match'));

		expect(listener).toHaveBeenCalledTimes(1);

		userEvent.click(getByTestId(document, 'match2'));

		expect(listener).toHaveBeenCalledTimes(2);
	});

	it('should trigger listener twice when two ancestors are delegating', function () {
		document.body.innerHTML = `<div>
			<div>
				<div class="match" data-testid="match"></div>
			</div>
		</div>`;

		let listener = jest.fn();

		delegate(document, 'click', '.match', listener);
		delegate(document.body, 'click', '.match', listener);

		userEvent.click(getByTestId(document, 'match'));

		expect(listener).toHaveBeenCalledTimes(2);
	});

	it('should remove listener through returned handle', function () {
		document.body.innerHTML = `<div class="nomatch" data-testid="nomatch"></div>
			<div class="match" data-testid="match"></div>`;

		let listener = jest.fn();

		const removeDelegate = delegate(document, 'click', '.match', listener);

		userEvent.click(getByTestId(document, 'match'));

		expect(listener).toHaveBeenCalledTimes(1);

		removeDelegate();

		userEvent.click(getByTestId(document, 'match'));

		expect(listener).toHaveBeenCalledTimes(1);
	});

	it('should not run click event listeners for disabled elements', function () {
		document.body.innerHTML = `<div class="root">
			<button disabled class="match" data-testid="match"></button>
		</div>`;

		const listener = jest.fn();

		delegate(document, 'click', '.match', listener);

		userEvent.click(getByTestId(document, 'match'));

		expect(listener).not.toHaveBeenCalled();
	});

	it('should not run click event listeners for elements with a disabled parent', function () {
		document.body.innerHTML = `<button disabled class="root">
			<div class="match" data-testid="match"></div>
		</button>`;

		const listener = jest.fn();

		delegate(document, 'click', '.match', listener);

		userEvent.click(getByTestId(document, 'match'));

		expect(listener).not.toHaveBeenCalled();
	});
});
