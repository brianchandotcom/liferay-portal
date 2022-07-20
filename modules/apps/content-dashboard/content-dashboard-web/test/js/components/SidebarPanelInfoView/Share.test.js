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

import {render, waitFor} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import Share from '../../../../src/main/resources/META-INF/resources/js/components/SidebarPanelInfoView/Share';

describe('Share', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('renders the html provided by fetchSharingButtonURL', async () => {
		window.fetch = jest.fn().mockReturnValue(
			Promise.resolve({
				ok: true,
				text: jest
					.fn()
					.mockReturnValue(Promise.resolve('<button>share</button>')),
			})
		);

		const {getByText} = render(
			<Share fetchSharingButtonURL="https://example" />
		);

		await waitFor(() => {
			expect(getByText('share')).toBeInTheDocument();
			expect(fetch).toHaveBeenCalled();
		});
	});

	it('calls onError prop if the is an error', async () => {
		window.fetch = jest.fn().mockReturnValue(
			Promise.resolve({
				ok: false,
				text: jest
					.fn()
					.mockReturnValue(Promise.resolve('<button>share</button>')),
			})
		);

		const handleError = jest.fn();

		render(
			<Share
				fetchSharingButtonURL="https://example"
				onError={handleError}
			/>
		);

		await waitFor(() => {
			expect(handleError).toHaveBeenCalled();
		});
	});
});
