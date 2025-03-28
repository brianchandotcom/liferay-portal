/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {
	AppsPermissions,
	Marketplace,
	MarketplaceContext,
	MarketplaceContextProvider,
	MarketplaceView,
} from '@liferay/marketplace-js-components-web';
import React from 'react';

import MarketplaceViews from './MarketplaceViews';

type CommerceChannelAddPaymentMethodProps = {
	baseResourceURL: string;
	permissions: AppsPermissions;
};

const CommerceChannelAddPaymentMethod = ({
	baseResourceURL,
	permissions,
}: CommerceChannelAddPaymentMethodProps) => (
	<MarketplaceContextProvider
		baseResourceURL={baseResourceURL}
		className="d-flex justify-content-end my-2 px-2 py-2"
		permissions={permissions}
		settings={{
			productFilter: 'payments',
		}}
	>
		<MarketplaceContext.Consumer>
			{({view}) => (
				<Marketplace.Modal
					noConnectionMessage={`${Liferay.Language.get(
						'you-are-trying-to-add-a-new-payment-method-through-the-marketplace,-but-the-connection-has-not-been-established-yet'
					)}${Liferay.Language.get(
						'please-go-to-instance-settings-to-enable-the-connection'
					)}`}
					size={
						view === MarketplaceView.PURCHASE ? 'lg' : 'full-screen'
					}
					trigger={
						<ClayButton size="sm">
							<ClayIcon className="mr-2" symbol="marketplace" />

							{Liferay.Language.get('add')}
						</ClayButton>
					}
				>
					<MarketplaceViews />
				</Marketplace.Modal>
			)}
		</MarketplaceContext.Consumer>
	</MarketplaceContextProvider>
);

export default CommerceChannelAddPaymentMethod;
