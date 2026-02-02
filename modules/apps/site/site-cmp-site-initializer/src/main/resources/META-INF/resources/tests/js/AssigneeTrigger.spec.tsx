/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import AssigneeTrigger from '../../js/components/AssigneeTrigger';

describe('AssigneeTrigger', () => {
	it('applies additional className when provided', () => {
		const selectedItem = {name: 'Alice', portrait: 'p.jpg'} as any;

		const {container} = render(
			<AssigneeTrigger
				className="my-class"
				selectedItem={selectedItem}
				value="value"
			/>
		);

		expect(
			container.querySelector(
				'.site-cmp-site-initializer__assignee-trigger'
			)
		).toBeInTheDocument();

		expect(container.querySelector('.my-class')).toBeInTheDocument();
	});

	it('renders input with placeholder and value', () => {
		const {container, getByPlaceholderText} = render(
			<AssigneeTrigger value="value" />
		);

		expect(getByPlaceholderText('unassigned')).toBeInTheDocument();

		const input = container.querySelector('input');

		expect((input as HTMLInputElement).value).toBe('value');
	});
});
