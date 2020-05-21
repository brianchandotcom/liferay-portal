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

import Link from '../../../../src/main/resources/META-INF/resources/admin/js/components/ShareFormModal/Link.es';

const props = {
	spritemap: 'spritemap',
	url: 'publish/url',
};

describe('Link', () => {
	let component;

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('renders the default markup', () => {
		component = new Link(props);
		expect(component).toMatchSnapshot();
	});

	it("copies the sharable URL to user's clipboard", () => {
		component = new Link(props);
		component._clipboard.emit('success');

		jest.runAllTimers();

		expect(component.state.success).toBeTruthy();
		expect(component).toMatchSnapshot();

		document.querySelector('.ddm-copy-clipboard').click();
	});
});
