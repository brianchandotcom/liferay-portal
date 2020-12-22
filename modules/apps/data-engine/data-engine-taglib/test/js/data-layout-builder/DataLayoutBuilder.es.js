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

import DataLayoutBuilder from '../../../src/main/resources/META-INF/resources/data_layout_builder/js/data-layout-builder/DataLayoutBuilder.es';

let component;
const props = {
	appContext: [
		{},
		(action) => {
			props.appContext[0].action = action;
		},
	],
	config: {
		allowFieldSets: true,
		allowMultiplePages: false,
		allowRules: false,
		allowSuccessPage: false,
		disabledProperties: ['predefinedValue'],
		disabledTabs: ['Autocomplete'],
		unimplementedProperties: [
			'fieldNamespace',
			'indexType',
			'readOnly',
			'validation',
			'visibilityExpression',
		],
	},
	context: {},
	dataLayoutBuilderId:
		'_com_liferay_journal_web_portlet_JournalPortlet_dataLayoutBuilder',
	fieldTypes: [],
	localizable: true,
	portletNamespace: 'com_liferay_journal_web_portlet_JournalPortlet',
};

describe('DataLayoutBuilder', () => {
	beforeEach(() => {
		jest.useFakeTimers();
		if (component && component.componentDidMount) {
			component.componentDidMount();
		}
	});

	afterEach(() => {
		if (component && component.dispose) {
			component.dispose();
		}
		else if (component && component.componentWillUnmount) {
			component.componentWillUnmount();
		}
	});

	it('is component custom property', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();

		expect(component._isCustomProperty('defaultValue')).toBe(false);
		expect(component._isCustomProperty('otherProperty')).toBe(true);
	});

	it('is rendering', () => {
		component = new DataLayoutBuilder(props);

		expect(component).toMatchSnapshot();
	});

	it('is rendering with localizable false', () => {
		component = new DataLayoutBuilder({...props, localizable: false});

		expect(component).toMatchSnapshot();
	});

	it('is dispatching message', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();
		const event = 'event:test';
		const payload = {test: 'dispatching'};

		component.eventEmitter.once(event, expect(payload).toBe);

		component.dispatch(event, payload);
	});

	it('is emetting message', () => {
		component = new DataLayoutBuilder(props);
		const event = 'event:test';
		const payload = {test: 'emeting'};

		component.eventEmitter.once(event, expect(payload).toBe);

		component.emit(event, payload);
	});

	it('is dispatching action', () => {
		component = new DataLayoutBuilder(props);
		const action = 'action:test';
		component.dispatchAction(action);

		expect(props.appContext[0].action).toBe(action);
	});

	it('is dispatching action without context', () => {
		component = new DataLayoutBuilder(props);
		const action = 'action:test';
		component.props.appContext[1] = null;
		component.dispatchAction(action);

		expect(props.appContext[0].action).toBe(action);
	});

	it('is getting data definition field', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();

		expect(
			component.getDataDefinitionField(
				{
					nestedFields: [],
					settingsContext: {
						pages: [],
					},
				},
				[],
				'en_US'
			)
		).toMatchObject({
			customProperties: {},
			nestedDataDefinitionFields: [],
		});
	});

	it('is getting data definition field formatted value', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();

		expect(
			component.getDataDefinitionFieldFormattedValue('json', {
				test: 'test',
			})
		).toBe('{"test":"test"}');

		expect(component.getDataDefinitionFieldFormattedValue('', 'test')).toBe(
			'test'
		);
	});

	it('is getting defaultDataLayout', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();
		const dataDefinition = {
			dataDefinitionFields: [],
		};

		expect(component.getDefaultDataLayout(dataDefinition)).toMatchObject({
			dataLayoutPages: [{dataLayoutRows: []}],
		});
	});

	it('is getting fieldTypes', () => {
		component = new DataLayoutBuilder(props);

		expect(props.fieldTypes).toBe(component.getFieldTypes());
	});

	it('is getting layoutProvider', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();

		expect(component.getLayoutProvider()).toMatchObject({
			...{ref: 'layoutProvider'},
		});
	});

	it('is getting component state', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();

		expect(component.getState()).toMatchObject(props.appContext[0]);
	});

	it('is getting component store', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();

		expect(component.getStore()).toMatchObject({});
	});

	it('is getting component form data property', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();

		expect(component._fromDataDefinitionToDDMFormPropertyName('name')).toBe(
			'fieldName'
		);
		expect(
			component._fromDataDefinitionToDDMFormPropertyName('otherProperty')
		).toBe('otherProperty');
		expect(
			component._fromDDMFormToDataDefinitionPropertyName('fieldName')
		).toBe('name');
		expect(
			component._fromDDMFormToDataDefinitionPropertyName('otherProperty')
		).toBe('otherProperty');
	});

	it('is removing listener after dispatching event', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();
		const event = 'event:test';
		const payload = {test: 'dispatching'};
		component.on(event, (eventPayload) => {
			component.removeEventListener(event);

			expect(payload).toBe(eventPayload);
		});
		component.dispatch(event, payload);
	});

	it('is removing listener after dispatching event with function', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();
		const event = 'event:test';
		const payload = {test: 'dispatching'};
		const listener = function (eventPayload) {
			component.removeEventListener(event, listener);
			expect(payload).toBe(eventPayload);
		};
		component.on(event, listener);
		component.dispatch(event, payload);
	});

	it('is serializing pages', () => {
		component = new DataLayoutBuilder(props);
		component.componentDidMount();

		expect(component.serialize([], [])).toMatchObject({
			definition:
				'{"availableLanguageIds":["en_US"],"dataDefinitionFields":[],"defaultLanguageId":"en_US"}',
			layout:
				'{"dataLayoutPages":[],"dataRules":[],"paginationMode":"wizard"}',
		});
	});
});
