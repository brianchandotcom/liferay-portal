/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen} from '@testing-library/react';
import React from 'react';

import {
	Page,
	PageHeader,
} from '../../../../../src/main/resources/META-INF/resources/js/core/components/PageRenderer/DefaultVariant.es';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/js/core/hooks/useForm.es',
	() => ({
		useFormState: () => ({
			portletId: 'test.portlet',
		}),
	})
);

describe('DefaultVariant Page', () => {
	it('renders page container with role="group" and aria attributes', () => {
		render(
			<Page
				header={
					<PageHeader
						description="Page Description"
						title="Page Title"
					/>
				}
				pageIndex={0}
			></Page>
		);

		const group = screen.getByRole('group');

		expect(group).toHaveAttribute('aria-labelledby', 'pageTitle0');
		expect(group).toHaveAttribute('aria-describedby', 'pageDescription0');
	});

	it('renders page title and description with correct ids', () => {
		render(
			<Page
				header={
					<PageHeader
						description="Page Description"
						title="Page Title"
					/>
				}
				pageIndex={1}
			/>
		);

		expect(screen.getByText('Page Title')).toHaveAttribute(
			'id',
			'pageTitle1'
		);

		expect(screen.getByText('Page Description')).toHaveAttribute(
			'id',
			'pageDescription1'
		);
	});
});
