/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

import AssetTypeInfoPanelEmptyState from '../../../../src/main/resources/META-INF/resources/js/main_view/info_panel/AssetTypeInfoPanelEmptyState';

describe('CMS Asset Type Info Panel Empty State', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders the empty state when no asset is selected', () => {
		render(<AssetTypeInfoPanelEmptyState selected={0} />);

		expect(
			screen.getByText('click-on-an-asset-to-see-its-details')
		).toBeInTheDocument();
	});

	it('renders the multi-selection state when multiple assets are selected', () => {
		render(<AssetTypeInfoPanelEmptyState selected={2} />);

		expect(screen.getAllByRole('presentation')[0]).toHaveAttribute(
			'src',
			expect.stringContaining('multiselection_state.svg')
		);
	});
});
