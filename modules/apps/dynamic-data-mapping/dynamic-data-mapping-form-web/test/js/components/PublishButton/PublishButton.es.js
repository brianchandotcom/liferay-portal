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

import PublishButton from '../../../../src/main/resources/META-INF/resources/admin/js/components/PublishButton/PublishButton.es';

const formInstanceId = '12345';
const namespace = 'portlet_namespace';
const spritemap = 'spritemap';
const url = 'publish/url';

const props = {
	formInstanceId,
	namespace,
	published: true,
	resolvePublishURL: () =>
		new Promise(resolve =>
			resolve({
				formInstanceId,
				publishURL: 'published/form'
			})
		),
	spritemap,
	submitForm: () => false,
	url
};

const mockEvent = {preventDefault: () => false};

describe('PublishButton', () => {
	let component;

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	beforeEach(() => {
		jest.useFakeTimers();
		fetch.resetMocks();
	});

	it('renders published', () => {
		component = new PublishButton(props);

		expect(component).toMatchSnapshot();
	});

	it('renders unpublished', () => {
		component = new PublishButton({
			...props,
			published: false
		});

		expect(component).toMatchSnapshot();
	});

	describe('publish()', () => {
		it('calls submitForm()', () => {
			const submitForm = jest.fn();

			component = new PublishButton({
				...props,
				submitForm
			});

			return component
				.publish(mockEvent)
				.then(() => expect(submitForm).toHaveBeenCalled());
		});
	});

	describe('unpublish()', () => {
		it('calls submitForm()', () => {
			const submitForm = jest.fn();

			component = new PublishButton({
				...props,
				submitForm
			});

			return component
				.publish(mockEvent)
				.then(() => expect(submitForm).toHaveBeenCalled());
		});
	});

	describe('toggle()', () => {
		it('calls publish() when props.published=false', () => {
			component = new PublishButton({
				...props,
				published: false
			});

			const publishSpy = jest.spyOn(component, 'publish');

			publishSpy.mockImplementation(() => Promise.resolve());

			return component
				.toggle(mockEvent)
				.then(() => expect(publishSpy).toHaveBeenCalled());
		});

		it('calls unpublish() when props.published=true', () => {
			component = new PublishButton({
				...props,
				published: true
			});

			const unpublishSpy = jest.spyOn(component, 'unpublish');

			unpublishSpy.mockImplementation(() => Promise.resolve());

			return component
				.toggle(mockEvent)
				.then(() => expect(unpublishSpy).toHaveBeenCalled());
		});

		it('is called when button is clicked', () => {
			component = new PublishButton({
				...props,
				published: true
			});

			const toggleSpy = jest.spyOn(component, 'toggle');

			component.refs.button.emit('click');

			jest.runAllTimers();

			expect(toggleSpy).toHaveBeenCalled();
		});
	});
});
