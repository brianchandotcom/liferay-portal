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
	contentTypeConfig: {allowInvalidAvailableLocalesForProperty: false},
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

		component.formBuilderWithLayoutProvider.refs.layoutProvider.dispatch(
			event,
			payload
		);
	});

	it('is emetting message', () => {
		component = new DataLayoutBuilder(props);
		const event = 'event:test';
		const payload = {test: 'emeting'};

		component.eventEmitter.once(event, expect(payload).toBe);

		component.emit(event, payload);
	});

	it('is getting fieldTypes', () => {
		component = new DataLayoutBuilder(props);

		expect(props.fieldTypes).toBe(component.props.fieldTypes);
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
		component.formBuilderWithLayoutProvider.refs.layoutProvider.dispatch(
			event,
			payload
		);
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
		component.formBuilderWithLayoutProvider.refs.layoutProvider.dispatch(
			event,
			payload
		);
	});
});
