/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';

import '@testing-library/jest-dom';
import React from 'react';

import {ObjectLayoutBox} from '../../components/Layout/LayoutScreen/ObjectLayoutBox';
import {BoxType} from '../../components/Layout/types';

const mockUseLayoutContext = jest.fn();

jest.mock('../../components/Layout/objectLayoutContext', () => ({
	useLayoutContext: () =>
		mockUseLayoutContext() || [
			{
				enableCategorization: false,
				enableFriendlyURLCustomization: false,
				isViewOnly: false,
				objectLayout: {
					objectLayoutTabs: [],
				},
			},
			jest.fn(),
		],
}));

function mockLayoutContextWithBoxes({
	enableCategorization = false,
	enableFriendlyURLCustomization = false,
	objectLayoutBoxes = [],
}: {
	enableCategorization?: boolean;
	enableFriendlyURLCustomization?: boolean;
	objectLayoutBoxes: Array<
		{type: string} & Partial<{
			collapsable: boolean;
		}>
	>;
}) {
	mockUseLayoutContext.mockReturnValue([
		{
			enableCategorization,
			enableFriendlyURLCustomization,
			isViewOnly: false,
			objectLayout: {
				objectLayoutTabs: [
					{
						objectLayoutBoxes,
					},
				],
			},
		},
		jest.fn(),
	]);
}

function renderObjectLayoutBox({type}: {type: BoxType}) {
	const defaultProps = {
		boxIndex: 0,
		collapsable: false,
		label: 'Box Label',
		objectLayoutRows: [],
		tabIndex: 0,
		type,
	};

	return render(<ObjectLayoutBox {...defaultProps} />);
}

describe('ObjectLayoutBox component', () => {
	beforeEach(() => {
		jest.clearAllMocks();
		mockUseLayoutContext.mockClear();
	});

	it('disables the categorization collapsible switch if enableCategorization is false', () => {
		mockLayoutContextWithBoxes({
			enableCategorization: false,
			objectLayoutBoxes: [{type: 'categorization'}],
		});

		renderObjectLayoutBox({type: 'categorization'});

		const categorizationSwitch = screen.getByRole('switch', {
			name: 'collapsible',
		});

		expect(categorizationSwitch).toBeDisabled();

		const disabledLabel = screen.getByText('disabled');

		expect(disabledLabel).toBeVisible();
	});

	it('disables the seo collapsible switch if enableFriendlyURLCustomization is false', () => {
		mockLayoutContextWithBoxes({
			enableFriendlyURLCustomization: false,
			objectLayoutBoxes: [{type: 'seo'}],
		});

		renderObjectLayoutBox({type: 'seo'});

		const seoSwitch = screen.getByRole('switch', {
			name: 'collapsible',
		});

		expect(seoSwitch).toBeDisabled();

		const disabledLabel = screen.getByText('disabled');

		expect(disabledLabel).toBeVisible();
	});
});
