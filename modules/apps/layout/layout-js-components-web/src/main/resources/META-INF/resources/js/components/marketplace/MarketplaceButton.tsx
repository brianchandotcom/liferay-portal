/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useCallback, useState} from 'react';

import openModalComponent from '../modals/openModalComponent';
import MarketplaceModal from './MarketplaceModal';
import MarketplacePresentationModal from './MarketplacePresentationModal';

import '../../../css/MarketplaceButton.scss';

import classNames from 'classnames';

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
		<ClayButtonWithIcon
			aria-label={Liferay.Language.get('open-marketplace-explorer')}
			borderless
			className={classNames('marketplace-button ml-2', {
				notification: !isMarketplaceButtonVisited,
			})}
			displayType="secondary"
			id={`${portletNamespace}isMarketplaceButtonVisited`}
			monospaced
			onClick={handleClick}
			size="sm"
			symbol="marketplace"
			title={Liferay.Language.get('open-marketplace-explorer')}
		/>
	);
}

export default MarketplaceButton;
