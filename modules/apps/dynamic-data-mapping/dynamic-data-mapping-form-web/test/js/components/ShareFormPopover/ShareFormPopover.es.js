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

import ShareFormPopover from 'source/components/ShareFormPopover/ShareFormPopover.es';

const spritemap = 'spritemap';
const url = 'publish/url';

const props = {
	spritemap,
	strings: {
		'copied-to-clipboard': 'copied-to-clipboard'
	},
	url
};

let shareFormIcon;

describe('ShareFormPopover', () => {
	let component;

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	beforeEach(() => {
		shareFormIcon = document.createElement('span');
		shareFormIcon.classList.add('share-form-icon');

		document.querySelector('body').appendChild(shareFormIcon);

		jest.useFakeTimers();
		fetch.resetMocks();
	});

	it('should render the default markup', () => {
		component = new ShareFormPopover(props);
		expect(component).toMatchSnapshot();
	});

	it("should copy the sharable URL to user's clipboard", () => {
		component = new ShareFormPopover(props);
		component._clipboard.emit('success');

		jest.runAllTimers();

		expect(component.state.success).toBeTruthy();
		expect(component).toMatchSnapshot();
	});
});
