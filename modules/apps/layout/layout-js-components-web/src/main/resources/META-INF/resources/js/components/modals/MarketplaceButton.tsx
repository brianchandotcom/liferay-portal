/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useCallback, useState} from 'react';

import MarketplaceModal from './MarketplaceModal';
import MarketplacePresentationModal from './MarketplacePresentationModal';
import openModalComponent from './openModalComponent';

import '../../../css/MarketplaceButton.scss';

interface Props {
	body: string;
	heading: string;
	isMarketplaceButtonVisited: boolean;
	portletNamespace: string;
}

function MarketplaceButton({
	body,
	heading,
	isMarketplaceButtonVisited,
	portletNamespace,
}: Props) {
	const [visited, setVisited] = useState(isMarketplaceButtonVisited);

	const handleClick = useCallback(() => {
		openModalComponent({
			ModalComponent: MarketplacePresentationModal,
			modalComponentProps: {body, heading},
		});

		setVisited(true);
		Liferay.Util.Session.set(
			`${portletNamespace}isMarketplaceButtonVisited`,
			true
		);
	}, [body, heading, portletNamespace]);

	if (visited) {
		return <MarketplaceModal />;
	}

	return (
		<div className="marketplace-button">
			<ClayButtonWithIcon
				aria-label={Liferay.Language.get('open-marketplace-explorer')}
				borderless
				displayType="secondary"
				id={`${portletNamespace}isMarketplaceButtonVisited`}
				monospaced
				onClick={handleClick}
				size="sm"
				symbol="marketplace"
				title={Liferay.Language.get('open-marketplace-explorer')}
			/>

			<span
				className="notification"
				id={`${portletNamespace}marketplaceBadge`}
			></span>
		</div>
	);
}

export default MarketplaceButton;
