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

import {useConfig} from '../../../../../src/main/resources/META-INF/resources/js/core/hooks/useConfig.es';
import PartialResults from '../../../../../src/main/resources/META-INF/resources/js/custom/form/components/PartialResults';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/core/hooks/useConfig.es'
);

describe('PartialResults', () => {
	afterEach(() => {
		cleanup();
	});

	it('shows a back button', () => {
		useConfig.mockReturnValue(() => ({
			resource: {
				lastModifiedDate: 'The last entry was sent 2 hours ago.',
				totalItems: 10,
			},
		}));

		const {getByText} = render(<PartialResults />);

		expect(getByText('back')).toBeInTheDocument();
	});
});
