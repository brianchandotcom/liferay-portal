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

import '../../__fixtures__/MockField.es';

import {dom as MetalTestUtil} from 'metal-dom';

import PageRenderer from '../../../src/main/resources/META-INF/resources/js/components/Page/PageRenderer.es';
import mockPages from '../../__mock__/mockPages.es';

const spritemap = 'icons.svg';
let component;
let componentProps = null;
let page = null;

describe('PageRenderer', () => {
	beforeEach(() => {
		page = {...mockPages[0]};

		componentProps = {
			activePage: 0,
			contentRenderer: 'grid',
			editable: true,
			page,
			pageId: 0,
			spritemap,
			total: 1
		};

		jest.useFakeTimers();
	});

	it('displays empty drag message when there are rows with no columns specified', () => {
		component = new PageRenderer({
			...componentProps,
			page: {
				rows: [{}]
			}
		});
		expect(component).toMatchSnapshot();
	});

	it('changes the page title', () => {
		component = new PageRenderer({
			...componentProps
		});

		const pageTitle = component.element.querySelector(
			'.form-builder-page-header-title'
		);
		const spy = jest.spyOn(component, 'emit');

		pageTitle.value = 'Page Title';

		jest.runAllTimers();
		MetalTestUtil.triggerEvent(pageTitle, 'keyup', {});

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('updatePage', expect.any(Object));

		expect(component).toMatchSnapshot();
	});

	it('changes the page title', () => {
		component = new PageRenderer({
			...componentProps
		});

		const pageDescription = component.element.querySelector(
			'.form-builder-page-header-description'
		);
		const spy = jest.spyOn(component, 'emit');

		pageDescription.value = 'Page Description';

		jest.runAllTimers();
		MetalTestUtil.triggerEvent(pageDescription, 'keyup', {});

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('updatePage', expect.any(Object));

		expect(component).toMatchSnapshot();
	});

	it('renders a layout and emit an event when delete button is clicked', () => {
		component = new PageRenderer({
			...componentProps
		});

		const spy = jest.spyOn(component, 'emit');

		component.element.querySelector("button[aria-label='trash']").click();

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith(
			'deleteButtonClicked',
			expect.any(Object)
		);
	});

	it('renders a layout and emit an event when duplicate button is clicked', () => {
		component = new PageRenderer({
			...componentProps
		});

		const spy = jest.spyOn(component, 'emit');

		component.element.querySelector("button[aria-label='paste']").click();

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith(
			'duplicateButtonClicked',
			expect.any(Object)
		);
	});

	it('renders a layout with emit an field clicked event', () => {
		component = new PageRenderer({
			...componentProps
		});

		const spy = jest.spyOn(component, 'emit');

		component.element.querySelector('.ddm-drag').click();

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldClicked', expect.any(Object));
	});

	it('emits a fieldClicked event with the field location', () => {
		component = new PageRenderer({
			...componentProps,
			dragAndDropDisabled: true
		});

		const spy = jest.spyOn(component, 'emit');

		component.element.querySelector('.ddm-drag').click();

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith(
			'fieldClicked',
			expect.objectContaining({
				columnIndex: expect.anything(),
				pageIndex: expect.any(Number),
				rowIndex: expect.any(Number)
			})
		);
	});
});
