/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import BaseAPIApplicationFields from '../../../src/main/resources/META-INF/resources/js/components/baseComponents/BaseAPIApplicationFields';

describe('BaseAPIApplicationFields', () => {
	it('shows the API URL host built from the window origin and the base path', () => {
		render(
			<BaseAPIApplicationFields
				basePath="/o/c/"
				data={{}}
				displayError={{baseURL: false, title: false}}
				setData={jest.fn()}
			/>
		);

		expect(
			screen.getByText(`${window.location.origin}/o/c/`)
		).toBeInTheDocument();
	});
});
