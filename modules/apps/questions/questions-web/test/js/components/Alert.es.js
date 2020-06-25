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

import {cleanup} from '@testing-library/react';
import React from 'react';

import Alert from '../../../src/main/resources/META-INF/resources/js/components/Alert.es';
import {renderComponent} from '../../helpers.es';

import '@testing-library/jest-dom/extend-expect';

describe('Alert', () => {
	afterEach(cleanup);

	it('renders alert with its title and message', () => {
		const info = {
			message: 'Info message',
			title: 'Info title',
		};
		const {getByText} = renderComponent({
			ui: <Alert info={info} />,
		});

		expect(getByText(info.title)).toBeInTheDocument();

		expect(getByText(info.message)).toBeInTheDocument();
	});
});
