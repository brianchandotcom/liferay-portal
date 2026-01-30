/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AppsPermissions} from '@liferay/marketplace-js-components-web';
import React, {ComponentProps, useState} from 'react';

import CardStyleModal from '../modals/CardStyleModal';
import MarketplaceModal from './MarketplaceModal';
import MarketplaceViews from './MarketplaceViews';

interface MarketplacePresentationModalProps {
	body: string;
	heading: string;
	onCloseModal?: () => void;
	permissions: AppsPermissions;
	portletNamespace: string;
}

export default function MarketplacePresentationModal({
	body,
	heading,
	onCloseModal,
	permissions,
	portletNamespace,
	...marketplaceViewProps
}: MarketplacePresentationModalProps &
	ComponentProps<typeof MarketplaceViews>) {
	const [openMarketplace, setOpenMarketplace] = useState(false);

	return openMarketplace ? (
		<MarketplaceModal
			openOnRender={true}
			permissions={permissions}
			portletNamespace={portletNamespace}
			trigger={null}
			{...marketplaceViewProps}
		/>
	) : (
		<CardStyleModal
			body={body}
			buttons={[
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
				},
				{
					displayType: 'primary',
					icon: 'marketplace',
					label: Liferay.Language.get('explore-marketplace'),
					onClick: () => setOpenMarketplace(true),
				},
			]}
			heading={heading}
			imageSrc={`${Liferay.ThemeDisplay.getPortalURL()}${Liferay.ThemeDisplay.getPathContext()}/o/layout-js-components-web/images/marketplace.svg`}
			onCloseModal={onCloseModal}
		/>
	);
}
