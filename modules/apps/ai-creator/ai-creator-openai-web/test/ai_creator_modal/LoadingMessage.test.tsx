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

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {LoadingMessage} from '../../src/main/resources/META-INF/resources/ai_creator_modal/LoadingMessage';

describe('LoadingMessage', () => {
	it('shows a loading message inside an alert', () => {
		render(<LoadingMessage />);

		expect(screen.getByRole('alert')).toHaveTextContent(
			'waiting-for-openai-to-create-the-contentthis-process-may-take-a-while'
		);
	});
});
