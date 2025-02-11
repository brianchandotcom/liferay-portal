/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';

import './MarketplaceModal.scss';

import {
	Marketplace,
	MarketplaceContextProvider,
	MarketplaceRest,
	MarketplaceViews,
} from '@liferay/marketplace-js-components-web';

interface Props {
	body: string;
	heading: string;
	onCloseModal: () => void;
}

function MarketplaceModal({body, heading, onCloseModal}: Props) {
	const {observer, onClose} = useModal({
		onClose: () => {
			onCloseModal();
		},
	});

	return (
		<ClayModal center observer={observer}>
			<ClayModal.Header>{heading}</ClayModal.Header>

			<ClayModal.Body className="c-p-0">
				<div className="marketplace-modal__image-background">
					<img
						alt="image"
						src={`${Liferay.ThemeDisplay.getPortalURL()}${Liferay.ThemeDisplay.getPathContext()}/o/layout-js-components-web/images/marketplace.svg`}
					/>
				</div>

				<p className="c-p-4">{body}</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<MarketplaceContextProvider
							baseResourceURL={MarketplaceRest.getBaseResourceURL()}
							settings={{productFilter: 'fragments'}}
						>
							<Marketplace.Modal
								trigger={
									<ClayButton
										aria-label={Liferay.Language.get(
											'explore-marketplace'
										)}
										displayType="primary"
										title={Liferay.Language.get(
											'explore-marketplace'
										)}
									>
										<ClayIcon
											className="inline-item inline-item-before"
											symbol="marketplace"
										/>

										{Liferay.Language.get(
											'explore-marketplace'
										)}
									</ClayButton>
								}
							>
								<MarketplaceViews />
							</Marketplace.Modal>
						</MarketplaceContextProvider>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

export default MarketplaceModal;
