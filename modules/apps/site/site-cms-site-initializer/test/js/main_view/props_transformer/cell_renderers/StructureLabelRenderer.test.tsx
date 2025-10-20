/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {render, screen} from '@testing-library/react';
import React from 'react';

import StructureLabelRenderer from '../../../../../src/main/resources/META-INF/resources/js/main_view/props_transformer/cell_renderers/StructureLabelRenderer';

describe('StructureLabelRenderer', () => {
	const defaultProps = {
		actions: [
			{
				data: {id: 'edit'},
				href: 'http://www.test.com',
			},
		],
		itemData: {},
		options: {actionId: 'edit'},
		value: 'Test Value',
	};

	it('renders link when system is false or undefined', () => {
		render(<StructureLabelRenderer {...defaultProps} />);

		expect(
			screen.getByRole('link', {name: 'Test Value'})
		).toBeInTheDocument();
	});

	it('checks the accessibility of the multiple file uploader', async () => {
		const {container} = render(
			<StructureLabelRenderer {...defaultProps} />
		);

		await checkAccessibility({bestPractices: true, context: container});
	});

	it('renders value and lock icon when system is true', () => {
		render(
			<StructureLabelRenderer
				{...defaultProps}
				itemData={{system: true}}
			/>
		);

		expect(screen.getByText('Test Value')).toBeInTheDocument();
		expect(
			screen.getByLabelText('system-default-structure')
		).toBeInTheDocument();
	});
});
