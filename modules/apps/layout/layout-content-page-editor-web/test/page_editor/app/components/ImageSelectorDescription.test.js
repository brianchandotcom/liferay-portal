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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import {ImageSelectorDescription} from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/ImageSelectorDescription';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableLanguages: {
				en_US: {
					default: false,
					displayName: 'English (United States)',
					languageIcon: 'en-us',
					languageId: 'en_US',
					w3cLanguageId: 'en-US',
				},
			},
		},
	})
);

describe('ImageSelectorDescription', () => {
	afterEach(() => {
		cleanup();
	});

	it('synchronizes imageDescription prop with input value', () => {
		const {getByLabelText} = render(
			<StoreAPIContextProvider
				getState={() => ({
					languageId: 'en_US',
				})}
			>
				<ImageSelectorDescription
					imageDescription="Random description"
					onImageDescriptionChanged={() => {}}
				/>
			</StoreAPIContextProvider>
		);

		expect(getByLabelText('image-description').value).toBe(
			'Random description'
		);
	});

	it('call onImageDescriptionChanged on blur', () => {
		const onImageDescriptionChanged = jest.fn();

		const {getByLabelText} = render(
			<StoreAPIContextProvider
				getState={() => ({
					languageId: 'en_US',
				})}
			>
				<ImageSelectorDescription
					imageDescription=""
					onImageDescriptionChanged={onImageDescriptionChanged}
				/>
			</StoreAPIContextProvider>
		);

		const input = getByLabelText('image-description');

		input.value = 'Some other thing';
		input.dispatchEvent(new FocusEvent('blur'));

		expect(onImageDescriptionChanged).toHaveBeenCalledWith(
			'Some other thing'
		);
	});
});
