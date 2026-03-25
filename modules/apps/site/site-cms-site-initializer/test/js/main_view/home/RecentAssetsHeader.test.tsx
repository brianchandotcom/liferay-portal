/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {navigate} from 'frontend-js-web';
import React from 'react';

import {RecentAssetsHeader} from '../../../../src/main/resources/META-INF/resources/js';

describe('RecentAssetsHeader', () => {
	const defaultProps = {
		label: 'View All',
		title: 'Recent Content',
		url: '/all',
	};

	it('renders a button with the label and the url', async () => {
		render(<RecentAssetsHeader {...defaultProps} />);

		expect(
			screen.getByRole('heading', {name: defaultProps.title})
		).toBeInTheDocument();

		await userEvent.click(
			screen.getByRole('button', {
				name: defaultProps.label,
			})
		);

		await waitFor(() => {
			expect(navigate).toHaveBeenCalledWith(defaultProps.url);
		});
	});
});
