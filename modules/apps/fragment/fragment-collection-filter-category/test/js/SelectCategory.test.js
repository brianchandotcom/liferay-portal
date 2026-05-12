/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import {SelectCategory} from '../../src/main/resources/META-INF/resources/js/index';

jest.mock('@liferay/fragment-renderer-collection-filter-impl', () => ({
	getCollectionFilterValue: () => null,
	setCollectionFilterValue: jest.fn(),
}));

describe('SelectCategory', () => {
	it('renders radio items with accessible names in single selection mode', () => {
		render(
			<SelectCategory
				assetCategories={[
					{id: 'cats', label: 'Cats'},
					{id: 'dogs', label: 'Dogs'},
				]}
				enableDropdown
				fragmentEntryLinkId="link-1"
				showSearch={false}
				singleSelection
				targetCollections={[]}
			/>
		);

		fireEvent.click(screen.getByRole('button', {name: 'select'}));

		expect(screen.getByRole('radio', {name: 'Cats'})).toBeInTheDocument();
		expect(screen.getByRole('radio', {name: 'Dogs'})).toBeInTheDocument();
	});
});
