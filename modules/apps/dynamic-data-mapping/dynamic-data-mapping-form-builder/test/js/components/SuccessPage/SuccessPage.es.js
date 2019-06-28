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

/* eslint no-only-tests/no-only-tests: "warn" */

import SuccessPage from 'source/components/SuccessPage/SuccessPage.es';
import SucessPageSettings from 'mock/mockSuccessPage.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
let successPageSettings;

describe('SuccessPage', () => {
	beforeEach(() => {
		successPageSettings = JSON.parse(JSON.stringify(SucessPageSettings));

		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}

		successPageSettings = null;
	});

	it('renders the default layour', () => {
		component = new SuccessPage({
			contentLabel: 'Content',
			successPageSettings,
			titleLabel: 'Title'
		});

		jest.runAllTimers();

		expect(component).toMatchSnapshot();
	});

	it('emits success page changed when success page title is changed', () => {
		const newPageSettings = {
			...successPageSettings,
			enabled: true
		};

		component = new SuccessPage({
			contentLabel: 'Content',
			successPageSettings: newPageSettings,
			titleLabel: 'Title'
		});
		const spy = jest.spyOn(component, 'emit');
		const titleNode = component.element.querySelector(
			'input[data-setting="title"]'
		);

		titleNode.value = 'Some title';

		jest.runAllTimers();

		MetalTestUtil.triggerEvent(titleNode, 'keyup', {});

		expect(spy).toHaveBeenCalledWith(
			'successPageChanged',
			expect.anything()
		);
	});

	it('emits success page changed when success page body is changed', () => {
		const newPageSettings = {
			...successPageSettings,
			enabled: true
		};

		component = new SuccessPage({
			contentLabel: 'Content',
			successPageSettings: newPageSettings,
			titleLabel: 'Title'
		});
		const spy = jest.spyOn(component, 'emit');
		const titleNode = component.element.querySelector(
			'input[data-setting="body"]'
		);

		titleNode.value = 'Some description';

		jest.runAllTimers();

		MetalTestUtil.triggerEvent(titleNode, 'keyup', {});

		expect(spy).toHaveBeenCalledWith(
			'successPageChanged',
			expect.anything()
		);
	});
});
