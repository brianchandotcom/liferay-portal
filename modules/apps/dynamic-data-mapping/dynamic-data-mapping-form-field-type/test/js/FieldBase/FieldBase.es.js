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

import FieldBase from '../../../src/main/resources/META-INF/resources/FieldBase/FieldBase.es';

let component;
const spritemap = 'icons.svg';

describe('FieldBase', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('renders the default markup', () => {
		component = new FieldBase({
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('renders the FieldBase with required', () => {
		component = new FieldBase({
			required: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('renders the FieldBase with id', () => {
		component = new FieldBase({
			id: 'Id',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('renders the FieldBase with help text', () => {
		component = new FieldBase({
			spritemap,
			tip: 'Type something!'
		});

		expect(component).toMatchSnapshot();
	});

	it('renders the FieldBase with label', () => {
		component = new FieldBase({
			label: 'Text',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('does not render the label if showLabel is false', () => {
		component = new FieldBase({
			label: 'Text',
			showLabel: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('renders the FieldBase with contentRenderer', () => {
		component = new FieldBase({
			contentRenderer: `
                <div>
                    <h1>Foo bar</h1>
                </div>
            `,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});
});
