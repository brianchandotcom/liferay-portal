/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';

import {useMarketplaceContext} from '../../MarketplaceContext';
import {ConnectionWithMarketplaceNeededModal} from './ConnectionWithMarketplaceNeededModal';
import MarketplaceModalView from './MarketplaceModal';

export type MarketplaceModalProps = {
	children: ReactNode;
	noConnectionMessage?: string;
	trigger: React.ReactElement;
};

export function MarketplaceModal({
	children,
	noConnectionMessage,
	trigger,
}: MarketplaceModalProps) {
	const {
		marketplaceConfiguration,
		modal: {observer, onOpenChange, open},
	} = useMarketplaceContext();

	const Modal = marketplaceConfiguration.authorized
		? MarketplaceModalView
		: ConnectionWithMarketplaceNeededModal;

	if (marketplaceConfiguration.loading) {
		return null;
	}

	return (
		<>
			{React.cloneElement(trigger, {
				onClick(event: Event) {
					if (trigger.props.onClick) {
						trigger.props.onClick(event);
					}

					onOpenChange(true);
				},
			})}

			<Modal
				message={noConnectionMessage}
				observer={observer}
				open={open}
			>
				{children}
			</Modal>
		</>
	);
}
