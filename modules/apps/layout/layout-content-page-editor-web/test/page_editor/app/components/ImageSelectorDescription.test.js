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
import {render, screen} from '@testing-library/react';
import React from 'react';

import {ImageSelectorDescription} from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/ImageSelectorDescription';
import StoreMother from '../../../../src/main/resources/META-INF/resources/page_editor/test-utils/StoreMother';

describe('ImageSelectorDescription', () => {
	it('synchronizes imageDescription prop with input value', () => {
		render(
			<StoreMother.Component>
				<ImageSelectorDescription
					imageDescription="Random description"
					onImageDescriptionChanged={() => {}}
				/>
			</StoreMother.Component>
		);

		expect(
			screen.getByLabelText('image-description', {
				selector: 'input',
			}).value
		).toBe('Random description');
	});

	it('call onImageDescriptionChanged on blur', () => {
		const onImageDescriptionChanged = jest.fn();

		render(
			<StoreMother.Component>
				<ImageSelectorDescription
					imageDescription=""
					onImageDescriptionChanged={onImageDescriptionChanged}
				/>
			</StoreMother.Component>
		);

		const input = screen.getByLabelText('image-description', {
			selector: 'input',
		});

		input.value = 'Some other thing';
		input.dispatchEvent(new FocusEvent('blur'));

		expect(onImageDescriptionChanged).toHaveBeenCalledWith(
			'Some other thing'
		);
	});
});
