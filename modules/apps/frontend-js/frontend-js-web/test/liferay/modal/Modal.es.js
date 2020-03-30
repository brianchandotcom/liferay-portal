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

import {render} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import {Modal} from '../../../src/main/resources/META-INF/resources/liferay/modal/Modal.es';

describe('Modal', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('renders markup based on given configuration', () => {
		const {baseElement} = render(
			<Modal
				id="abcd"
				size="lg"
				title="My Modal"
				url="https://www.sample.url?p_p_id=com_liferay_MyPortlet"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(baseElement).toMatchSnapshot();
	});
});
