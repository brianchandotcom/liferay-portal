/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {
	Marketplace,
	MarketplaceContextProvider,
	MarketplaceRest,
	MarketplaceView,
	useMarketplaceContext,
} from '@liferay/marketplace-js-components-web';
import {sub} from 'frontend-js-web';
import React, {
	ReactElement,
	cloneElement,
	useCallback,
	useEffect,
	useState,
} from 'react';

import MarketplaceViews from './MarketplaceViews';

interface MarketplaceModalProps {
	trigger?: ReactElement;
}

export default function MarketplaceModal({trigger}: MarketplaceModalProps) {
	const [title, setTitle] = useState<string | undefined>();

	const props = {
		trigger: (
			<MarketplaceModalTrigger setTitle={setTitle} trigger={trigger} />
		),
		...(title && {title}),
	};

	return (
		<MarketplaceContextProvider
			baseResourceURL={MarketplaceRest.getBaseResourceURL()}
			settings={{productFilter: 'fragments'}}
		>
			<Marketplace.Modal {...props}>
				<MarketplaceViews />
			</Marketplace.Modal>
		</MarketplaceContextProvider>
	);
}

interface MarketplaceModalTriggerProps {
	setTitle: React.Dispatch<React.SetStateAction<string | undefined>>;
	trigger?: ReactElement;
}

function MarketplaceModalTrigger({
	setTitle,
	trigger,
}: MarketplaceModalTriggerProps) {
	const {
		modal: {onOpenChange},
		product,
		setView,
		view,
	} = useMarketplaceContext();

	const handleClick = useCallback(() => {
		if (view === MarketplaceView.PURCHASE) {
			setView(MarketplaceView.PRODUCTS);
		}

		onOpenChange(true);
	}, [view, setView, onOpenChange]);

	useEffect(() => {
		setTitle(
			view === MarketplaceView.PURCHASE && product
				? sub(Liferay.Language.get('installing-x'), product.name)
				: ''
		);
	}, [view, product, setTitle]);

	if (trigger) {
		return cloneElement(trigger, {
			onClick: (event: React.MouseEvent) => {
				if (trigger.props.onClick) {
					trigger.props.onClick(event);
				}
				else {
					handleClick();
				}
			},
		});
	}

	return (
		<ClayButtonWithIcon
			aria-label={Liferay.Language.get('open-marketplace-explorer')}
			borderless
			displayType="secondary"
			monospaced
			onClick={handleClick}
			size="sm"
			symbol="marketplace"
			title={Liferay.Language.get('open-marketplace-explorer')}
		/>
	);
}
