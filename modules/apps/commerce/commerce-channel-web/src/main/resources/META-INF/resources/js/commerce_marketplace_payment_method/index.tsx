/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {
	Marketplace,
	MarketplaceContextProvider,
	MarketplaceRest,
	MarketplaceViews,
} from '@liferay/marketplace-js-components-web';
import React from 'react';

const CommerceChannelAddPaymentMethod = () => (
	<MarketplaceContextProvider
		baseResourceURL={MarketplaceRest.getBaseResourceURL()}
		className="d-flex justify-content-end my-2 px-2 py-2"
		settings={{productFilter: 'payments'}}
	>
		<Marketplace.Modal
			noConnectionMessage={Liferay.Language.get(
				'you-are-trying-to-add-a-new-payment-method-through-the-marketplace,-but-the-connection-has-not-been-established-yet'
			)}
			trigger={
				<ClayButton size="sm">
					<ClayIcon className="mr-2" symbol="marketplace" />

					{Liferay.Language.get('add')}
				</ClayButton>
			}
		>
			<MarketplaceViews />
		</Marketplace.Modal>
	</MarketplaceContextProvider>
);

export default CommerceChannelAddPaymentMethod;
