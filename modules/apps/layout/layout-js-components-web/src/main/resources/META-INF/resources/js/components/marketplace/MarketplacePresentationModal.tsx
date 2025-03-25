/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';

import MarketplaceModal from './MarketplaceModal';

import '../../../css/MarketplaceModal.scss';

interface MarketplacePresentationModalProps {
	body: string;
	fragmentPortletNamespace: string;
	fragmentsImportURL: string;
	heading: string;
	onCloseModal: () => void;
}

export default function MarketplacePresentationModal({
	body,
	fragmentPortletNamespace,
	fragmentsImportURL,
	heading,
	onCloseModal,
}: MarketplacePresentationModalProps) {
	const [openMarketplace, setOpenMarketplace] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			onCloseModal();
		},
	});

	return openMarketplace ? (
		<MarketplaceModal
			fragmentPortletNamespace={fragmentPortletNamespace}
			fragmentsImportURL={fragmentsImportURL}
			openOnRender={true}
			trigger={null}
		/>
	) : (
		<ClayModal center observer={observer}>
			<ClayModal.Header>{heading}</ClayModal.Header>

			<ClayModal.Body className="c-p-0">
				<div className="marketplace-modal__image-background">
					<img
						alt=""
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

						<ClayButton
							aria-label={Liferay.Language.get(
								'explore-marketplace'
							)}
							displayType="primary"
							onClick={() => setOpenMarketplace(true)}
							title={Liferay.Language.get('explore-marketplace')}
						>
							<ClayIcon
								className="inline-item inline-item-before"
								symbol="marketplace"
							/>

							{Liferay.Language.get('explore-marketplace')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
