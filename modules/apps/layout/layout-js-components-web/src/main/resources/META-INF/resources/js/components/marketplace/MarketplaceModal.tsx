/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {
	Marketplace,
	MarketplaceContextProvider,
	MarketplaceRest,
} from '@liferay/marketplace-js-components-web';
import React, {ReactElement} from 'react';

import MarketplaceViews from './MarketplaceViews';

interface Props {
	trigger?: ReactElement;
}

function MarketplaceModal({trigger}: Props) {
	return (
		<MarketplaceContextProvider
			baseResourceURL={MarketplaceRest.getBaseResourceURL()}
			settings={{productFilter: 'fragments'}}
		>
			<Marketplace.Modal
				trigger={
					trigger ? (
						trigger
					) : (
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get(
								'open-marketplace-explorer'
							)}
							borderless
							displayType="secondary"
							monospaced
							size="sm"
							symbol="marketplace"
							title={Liferay.Language.get(
								'open-marketplace-explorer'
							)}
						/>
					)
				}
			>
				<MarketplaceViews />
			</Marketplace.Modal>
		</MarketplaceContextProvider>
	);
}

export default MarketplaceModal;
