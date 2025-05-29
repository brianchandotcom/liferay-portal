/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {ComponentProps, useCallback, useEffect, useState} from 'react';

import openModalComponent from '../modals/openModalComponent';
import MarketplaceModal from './MarketplaceModal';
import MarketplacePresentationModal from './MarketplacePresentationModal';

import './MarketplaceButton.scss';

import {AppsPermissions} from '@liferay/marketplace-js-components-web';
import classNames from 'classnames';

import MarketplaceViews from './MarketplaceViews';

interface MarketplaceButtonProps {
	body: string;
	heading: string;
	permissions: AppsPermissions;
	portletNamespace: string;
}

function MarketplaceButton({
	body,
	heading,
	permissions,
	portletNamespace,
	...marketplaceViewProps
}: MarketplaceButtonProps & ComponentProps<typeof MarketplaceViews>) {
	const [visited, setVisited] = useState(false);

	const hasButtonBeenVisited = useCallback(
		() =>
			Liferay.Util.Session.get(
				`${portletNamespace}isMarketplaceButtonVisited`
			),
		[portletNamespace]
	);

	const markButtonAsVisited = useCallback(() => {
		setVisited(true);

		Liferay.Util.Session.set(
			`${portletNamespace}isMarketplaceButtonVisited`,
			'true'
		);
	}, [portletNamespace]);

	useEffect(() => {
		const handleVisited = async () => {
			const visited = await hasButtonBeenVisited();

			setVisited(visited === 'true');
		};

		handleVisited();
	}, [hasButtonBeenVisited]);

	const handleClick = useCallback(() => {
		openModalComponent({
			ModalComponent: MarketplacePresentationModal,
			modalComponentProps: {
				body,
				heading,
				permissions,
				portletNamespace,
				...marketplaceViewProps,
			},
		});

		markButtonAsVisited();
	}, [
		body,
		heading,
		permissions,
		portletNamespace,
		marketplaceViewProps,
		markButtonAsVisited,
	]);

	if (visited) {
		return (
			<MarketplaceModal
				permissions={permissions}
				portletNamespace={portletNamespace}
				{...marketplaceViewProps}
			/>
		);
	}

	return (
		<ClayButtonWithIcon
			aria-label={Liferay.Language.get('open-marketplace-explorer')}
			borderless
			className={classNames('marketplace-button ml-2', {
				notification: !visited,
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
