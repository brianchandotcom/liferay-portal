/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import {Observer, Size} from '@clayui/modal/lib/types';
import React, {ReactNode} from 'react';

import {MarketplaceView, useMarketplaceContext} from '../../MarketplaceContext';

type MarketplaceModalViewProps = {
	children: ReactNode;
	observer: Observer;
	open: boolean;
	size?: Size;
	title?: string;
};

function MarketplaceModalView({
	children,
	observer,
	open,
	size,
	title,
}: MarketplaceModalViewProps) {
	const {view} = useMarketplaceContext();

	if (!open) {
		return null;
	}

	return (
		<ClayModal
			center
			className="marketplace-modal"
			observer={observer}
			size={
				size ||
				(view === MarketplaceView.PURCHASE ? 'lg' : 'full-screen')
			}
		>
			<ClayModal.Header>
				{title || Liferay.Language.get('add-from-marketplace')}
			</ClayModal.Header>

			<ClayModal.Body className="m-0 p-0">{children}</ClayModal.Body>
		</ClayModal>
	);
}

export default MarketplaceModalView;
