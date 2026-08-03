/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import getAssetEntriesItemSelectorProps from '../../src/main/resources/META-INF/resources/js/getAssetEntriesItemSelectorProps';

function getTitleCell() {
	const {fdsProps} = getAssetEntriesItemSelectorProps({
		assetEntryTypes: [{classNameId: 1, label: 'Web Content'}],
		groupIds: '123',
		portletNamespace: 'ns',
	});

	const {component: TitleCell} = fdsProps.customRenderers.tableCell.find(
		(contentRenderer) => contentRenderer.name === 'assetEntryTitle'
	);

	return TitleCell;
}

describe('getAssetEntriesItemSelectorProps', () => {
	describe('TitleCell', () => {
		it('renders the not-visible-to-guest icon when viewableByGuest is false', () => {
			const TitleCell = getTitleCell();

			render(
				<TitleCell itemData={{viewableByGuest: false}} value="Title" />
			);

			expect(
				screen.getByLabelText('not-visible-to-guest-users')
			).toBeInTheDocument();
		});

		it('does not render the icon when viewableByGuest is true', () => {
			const TitleCell = getTitleCell();

			render(
				<TitleCell itemData={{viewableByGuest: true}} value="Title" />
			);

			expect(
				screen.queryByLabelText('not-visible-to-guest-users')
			).not.toBeInTheDocument();
		});

		it('does not render the icon when viewableByGuest is absent', () => {
			const TitleCell = getTitleCell();

			render(<TitleCell itemData={{}} value="Title" />);

			expect(
				screen.queryByLabelText('not-visible-to-guest-users')
			).not.toBeInTheDocument();
		});
	});
});
