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

import WithEvaluator from '../../../src/main/resources/META-INF/resources/js//components/Form/Evaluator.es';
import mockPages from '../__mock__/mockPages.es';

const fieldType = 'text';
let component;
let pages;

const EvaluatorComponent = WithEvaluator(() => <div>{'Liferay'}</div>);
const fieldInstance = {
	fieldName: 'Checkbox',
	isDisposed: () => false,
	value: false
};

describe('Evaluator', () => {
	beforeEach(() => {
		jest.useFakeTimers();
		fetch.resetMocks();

		pages = mockPages;
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('renders default markup', () => {
		component = new EvaluatorComponent({
			fieldType,
			formContext: {}
		});

		expect(component).toMatchSnapshot();
	});

	it('continues propagating the fieldEdited event', () => {
		component = new EvaluatorComponent({
			fieldType,
			formContext: {}
		});
		const event = {
			fieldInstance: {
				...fieldInstance,
				value: true
			}
		};
		const spy = jest.spyOn(component, 'emit');

		fetch.mockResponse(JSON.stringify({}), {
			status: 200
		});

		jest.runAllTimers();

		component._handleFieldEdited(event);

		expect(spy).toHaveBeenCalledWith('fieldEdited', event);
	});

	it('continues propagating the fieldEdited event when it is evaluable', () => {
		component = new EvaluatorComponent({
			fieldType,
			formContext: {
				pages
			}
		});
		const event = {
			fieldInstance: {
				...fieldInstance,
				evaluable: true,
				value: true
			}
		};
		const spy = jest.spyOn(component, 'emit');

		fetch.mockResponse(JSON.stringify(pages), {
			status: 200
		});

		component._handleFieldEdited(event);

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('fieldEdited', event);
	});

	it("updates the state of the evaluator's pages when it receives a new page as it props", () => {
		const newPages = [
			{
				rows: [
					{
						columns: [
							{
								fields: [
									{
										fieldName: 'radioField',
										label: 'Radio',
										type: 'radio'
									}
								],
								size: 3
							},
							{
								fields: [],
								size: 9
							}
						]
					}
				]
			}
		];

		component = new EvaluatorComponent({
			fieldType,
			formContext: {
				pages
			}
		});

		jest.runAllTimers();

		jest.useFakeTimers();

		component.willReceiveProps({
			formContext: {
				newVal: {
					pages: newPages
				}
			}
		});

		jest.runAllTimers();

		expect(component.state.pages).toEqual(newPages);
	});

	it("does not update the state of the evaluator's pages when it receives any property which is not pages", () => {
		component = new EvaluatorComponent({
			fieldType,
			formContext: {
				pages
			}
		});

		jest.runAllTimers();

		jest.useFakeTimers();

		component.willReceiveProps({
			someProperty: true
		});

		jest.runAllTimers();

		expect(component.state.pages).toEqual(pages);
	});
});
