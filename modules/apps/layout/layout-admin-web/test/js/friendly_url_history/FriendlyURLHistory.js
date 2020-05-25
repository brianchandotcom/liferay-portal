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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import FriendlyURLHistory from '../../../src/main/resources/META-INF/resources/js/friendly_url_history/FriendlyURLHistory';

describe('FriendlyURLHistory', () => {
	afterEach(cleanup);

	it('renders an icon', async () => {
		const {getByRole} = render(<FriendlyURLHistory portletNamespace="_friendlyUrlHistory_" />);

		expect(getByRole('button').className).toContain('btn-url-history');
	});
});
