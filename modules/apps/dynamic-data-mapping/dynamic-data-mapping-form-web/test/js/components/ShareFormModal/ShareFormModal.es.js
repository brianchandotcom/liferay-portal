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

import {convertToFormData} from 'dynamic-data-mapping-form-renderer/js/util/fetch.es';

import ShareFormModal from '../../../../src/main/resources/META-INF/resources/admin/js/components/ShareFormModal/ShareFormModal.es';

const props = {
	autocompleteUserURL: '/admin/autocomplete_user',
	localizedName: {
		en_US: 'Created Form',
	},
	portletNamespace: 'portletNamespace_',
	shareFormInstanceURL: '/admin/share_form_instance',
	spritemap: 'spritemap',
	url: 'publish/url',
};

const initialEmailContent = {
	addresses: [],
	message: 'please-fill-out-this-form-publish/url',
	subject: 'Created Form',
};

describe('ShareFormModal', () => {
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

	it('opens the modal when clicking the button', () => {
		component = new ShareFormModal(props);

		const {shareFormModalRef} = component.refs;

		expect(shareFormModalRef.visible).toEqual(false);

		document
			.querySelector('.share-form-modal')
			.insertAdjacentHTML(
				'afterend',
				'<div class="nav-item"><button class="lfr-ddm-share-url-button"></button></div>'
			);

		document.querySelector('.lfr-ddm-share-url-button').click();

		jest.runAllTimers();

		expect(shareFormModalRef.visible).toEqual(true);
	});

	describe('open()', () => {
		it('opens the modal', () => {
			component = new ShareFormModal(props);

			const {shareFormModalRef} = component.refs;

			expect(shareFormModalRef.visible).toEqual(false);

			component.open();

			jest.runAllTimers();

			expect(shareFormModalRef.visible).toEqual(true);
		});

		it('initializes email content', () => {
			component = new ShareFormModal(props);

			const {emailRef} = component.refs.shareFormModalRef.refs;

			const oldEmailContent = {
				...initialEmailContent,
				addresses: [
					{
						label: 'test@liferay.com',
						value: 'test@liferay.com',
					},
				],
			};

			emailRef.setState({
				emailContent: oldEmailContent,
			});

			expect(emailRef.state.emailContent).toEqual(oldEmailContent);

			component.open();

			jest.runAllTimers();

			expect(emailRef.state.emailContent).toEqual(initialEmailContent);
		});
	});

	describe('close()', () => {
		it('closes the modal', () => {
			component = new ShareFormModal(props);

			component.open();

			jest.runAllTimers();

			const {shareFormModalRef} = component.refs;

			expect(shareFormModalRef.visible).toEqual(true);

			component.close();

			jest.runAllTimers();

			expect(shareFormModalRef.visible).toEqual(false);
		});
	});

	it('receives the clickButton event and closes the modal', () => {
		component = new ShareFormModal(props);

		component.open();

		jest.runAllTimers();

		const {shareFormModalRef} = component.refs;

		expect(shareFormModalRef.visible).toEqual(true);

		shareFormModalRef.emit('clickButton', {
			target: document.querySelector('.btn.btn-primary'),
		});

		jest.runAllTimers();

		expect(shareFormModalRef.visible).toEqual(false);
	});

	describe('submitEmailContent()', () => {
		it('does not submit when the email addresses field is empty', () => {
			component = new ShareFormModal(props);

			component.open();

			jest.runAllTimers();

			const {shareFormModalRef} = component.refs;

			shareFormModalRef.emit('clickButton', {
				target: document.querySelector('.btn.btn-primary'),
			});

			jest.runAllTimers();

			const spy = jest.spyOn(window, 'fetch');

			expect(spy).not.toHaveBeenCalledWith(
				component.props.shareFormInstanceURL,
				expect.anything()
			);
			expect(shareFormModalRef.visible).toEqual(false);
		});

		it('submits the email content', () => {
			component = new ShareFormModal(props);

			component.open();

			jest.runAllTimers();

			const {shareFormModalRef} = component.refs;

			const {emailRef} = shareFormModalRef.refs;

			emailRef.setState({
				emailContent: {
					...initialEmailContent,
					addresses: [
						{
							label: 'test@liferay.com',
							value: 'test@liferay.com',
						},
						{
							label: 'test2@liferay.com',
							value: 'test2@liferay.com',
						},
					],
				},
			});

			shareFormModalRef.emit('clickButton', {
				target: document.querySelector('.btn.btn-primary'),
			});

			jest.runAllTimers();

			const spy = jest.spyOn(window, 'fetch');

			expect(spy).toHaveBeenCalledWith(
				component.props.shareFormInstanceURL,
				expect.anything()
			);

			const call = fetch.mock.calls[1];

			expect(call[0]).toEqual(component.props.shareFormInstanceURL);

			const {body, method} = call[1];

			const expectedBody = {
				portletNamespace_addresses:
					'test@liferay.com,test2@liferay.com',
				portletNamespace_message:
					'please-fill-out-this-form-publish/url',
				portletNamespace_subject: 'Created Form',
			};

			expect(method).toEqual('POST');
			expect(body).toEqual(convertToFormData(expectedBody));
			expect(shareFormModalRef.visible).toEqual(false);
		});
	});
});
