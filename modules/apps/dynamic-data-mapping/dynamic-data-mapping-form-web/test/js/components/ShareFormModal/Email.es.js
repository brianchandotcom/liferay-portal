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

import userEvent from '@testing-library/user-event';

import Email from '../../../../src/main/resources/META-INF/resources/admin/js/components/ShareFormModal/Email.es';

const initialEmailContent = {
	addresses: [],
	message: 'please-fill-out-this-form-publish/url',
	subject: 'Created Form',
};

const props = {
	autocompleteUserURL: '/admin/autocomplete_user',
	localizedName: {
		en_US: 'Created Form',
	},
	spritemap: 'spritemap',
	url: 'publish/url',
};

describe('Email', () => {
	let component;

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	beforeEach(() => {
		fetch.mockResponse(JSON.stringify([]));

		jest.useFakeTimers();
	});

	it('fetchs email addresses when rendered', () => {
		const spy = jest.spyOn(window, 'fetch');

		component = new Email(props);

		expect(spy).toHaveBeenCalledWith(
			component.props.autocompleteUserURL,
			expect.anything()
		);

		spy.mockRestore();
	});

	it('initializes email content when rendered', () => {
		component = new Email(props);

		expect(component.state.emailContent).toEqual(initialEmailContent);
	});

	it('changes email content when message field is edited', () => {
		component = new Email(props);

		expect(component.state.emailContent.message).toEqual(
			initialEmailContent.message
		);

		const message = component.element.querySelector('#message');

		userEvent.type(message, 'New message');

		jest.runAllTimers();

		expect(component.state.emailContent.message).toEqual(message.value);
	});

	it('changes email content when subject field is edited', () => {
		component = new Email(props);

		expect(component.state.emailContent.subject).toEqual(
			initialEmailContent.subject
		);

		const subject = component.element.querySelector('#subject');

		userEvent.type(subject, 'New Subject');

		jest.runAllTimers();

		expect(component.state.emailContent.subject).toEqual(subject.value);
	});

	it('receives labelItemAdded event when a label item is added', () => {
		component = new Email({
			...props,
			autocompleteUser: [],
		});

		expect(component.state.emailContent.addresses).toEqual(
			initialEmailContent.addresses
		);

		const selectedItems = [
			{
				label: 'test@liferay.com',
				value: 'test@liferay.com',
			},
		];

		component.refs.multiSelectRef.emit({
			data: {
				selectedItems,
			},
			name: 'labelItemAdded',
		});

		expect(component.state.emailContent.addresses).toEqual(selectedItems);
	});

	it('receives labelItemRemoved event when a label item is removed', () => {
		component = new Email({
			...props,
			autocompleteUser: [],
		});

		expect(component.state.emailContent.addresses).toEqual(
			initialEmailContent.addresses
		);

		const selectedItems = [
			{
				label: 'test@liferay.com',
				value: 'test@liferay.com',
			},
		];

		component.refs.multiSelectRef.emit({
			data: {
				selectedItems,
			},
			name: 'labelItemRemoved',
		});

		expect(component.state.emailContent.addresses).toEqual(selectedItems);
	});

	it('allows to add a new item when the input value is a valid email', () => {
		component = new Email({
			...props,
			autocompleteUser: [],
		});

		const {multiSelectRef} = component.refs;

		const value = 'test@liferay.com';

		multiSelectRef.emit({
			data: {
				value,
			},
			name: 'inputChange',
		});

		const isValid = component.isEmailAddressValid(value);

		expect(isValid).toBe(true);
		expect(multiSelectRef.creatable).toBe(true);
	});

	it('does not allow to add a new item when the input value is not an email', () => {
		component = new Email({
			...props,
			autocompleteUser: [],
		});

		const {multiSelectRef} = component.refs;

		const value = 'test';

		multiSelectRef.emit({
			data: {
				value,
			},
			name: 'inputChange',
		});

		const isValid = component.isEmailAddressValid(value);

		expect(isValid).toBe(false);
		expect(multiSelectRef.creatable).toBe(false);
	});
});
