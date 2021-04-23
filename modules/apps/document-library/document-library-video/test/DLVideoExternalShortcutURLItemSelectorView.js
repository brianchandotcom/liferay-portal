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

import {waitForElementToBeRemoved} from '@testing-library/dom';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import DLVideoExternalShortcutURLItemSelectorView from '../src/main/resources/META-INF/resources/js/DLVideoExternalShortcutURLItemSelectorView';

const liferayOpenerfireMock = jest.fn();

Liferay.Util.getOpener = jest.fn(() => ({
	Liferay: {
		fire: liferayOpenerfireMock,
	},
}));

const defaultProps = {
	eventName: 'eventName',
	getDLVideoExternalShortcutFieldsURL: '/getDLVideoExternalShortcutFieldsURL',
	namespace: 'namespace',
	returnType: 'returnType',
};

const renderComponent = (props) =>
	render(<DLVideoExternalShortcutURLItemSelectorView {...props} />);

describe('DLVideoExternalShortcutURLItemSelectorView', () => {
	afterEach(cleanup);

	describe('when rendered with the default props', () => {
		let result;

		beforeEach(() => {
			result = renderComponent(defaultProps);
		});

		it('has an add button disabled', () => {
			const add = result.getByRole('button');

			expect(add).toBeInTheDocument();
			expect(add).toBeDisabled();
		});
	});

	describe('when there is a valid server response', () => {
		const responseFields = {
			DESCRIPTION: 'DESCRIPTION',
			HTML: '<iframe data-video-liferay></iframe>',
			THUMBNAIL_URL: 'https://thumbnail-url',
			TITLE: 'VIDEO TITLE',
			URL: 'https://video-url.com',
		};

		let result;

		beforeEach(async () => {
			jest.useFakeTimers();

			fetch.mockResponseOnce(JSON.stringify(responseFields));

			result = renderComponent(defaultProps);
			const {getByLabelText} = result;

			fireEvent.change(getByLabelText('video-url'), {
				target: {value: 'https://video-url.com'},
			});

			jest.advanceTimersByTime(500);

			await waitForElementToBeRemoved(() =>
				document.querySelector('span.loading-animation')
			);
		});

		afterEach(() => {
			jest.clearAllTimers();
			jest.clearAllMocks();
		});

		afterAll(() => {
			jest.useRealTimers();
		});

		it('has an add button enabled', () => {
			const add = result.getByRole('button');

			expect(add).toBeInTheDocument();
			expect(add).toBeEnabled();
		});

		describe('when the form is submitted', () => {
			beforeEach(async () => {
				fireEvent.submit(result.getByRole('form'));
			});

			it('fires an event in the opener', () => {
				expect(Liferay.Util.getOpener).toHaveBeenCalled();

				expect(liferayOpenerfireMock).toHaveBeenCalledWith(
					defaultProps.eventName,
					{
						data: {
							returnType: defaultProps.returnType,
							value: {
								html: responseFields.HTML,
								title: responseFields.TITLE,
							},
						},
					}
				);
			});
		});
	});

	describe('when there is an invalid server response', () => {
		let result;

		beforeEach(async () => {
			jest.useFakeTimers();
			fetch.mockResponseOnce('');

			result = renderComponent(defaultProps);
			const {getByLabelText} = result;

			fireEvent.change(getByLabelText('video-url'), {
				target: {value: 'https://unsupported-video-url.com'},
			});

			jest.advanceTimersByTime(500);

			await waitForElementToBeRemoved(() =>
				document.querySelector('span.loading-animation')
			);
		});

		afterEach(() => {
			jest.clearAllTimers();
			jest.clearAllMocks();
		});

		afterAll(() => {
			jest.useRealTimers();
		});

		it('has an add button disabled', () => {
			const add = result.getByRole('button');

			expect(add).toBeInTheDocument();
			expect(add).toBeDisabled();
		});

		describe('when the form is submitted', () => {
			beforeEach(async () => {
				fireEvent.submit(result.getByRole('form'));
			});

			it('does not fire an event in the opener', () => {
				expect(Liferay.Util.getOpener).not.toHaveBeenCalled();
				expect(liferayOpenerfireMock).not.toHaveBeenCalled();
			});
		});
	});
});
