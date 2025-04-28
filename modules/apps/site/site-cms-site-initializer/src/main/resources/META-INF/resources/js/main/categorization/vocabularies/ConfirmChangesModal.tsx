/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import React from 'react';

const CONFIRMATION_MESSAGES = {
	ASSET_TYPES: 'remove-asset-types-from-vocabulary-confirmation',
	BOTH: 'remove-both-from-vocabulary-confirmation',
	SPACES: 'remove-space-from-vocabulary-confirmation',
};
export default function ConfirmChangesModal({
	assetTypeChange,
	observer,
	onOpenChange,
	onSave,
	spaceChange,
}: {
	assetTypeChange: boolean;
	observer: any;
	onOpenChange: (value: boolean) => void;
	onSave: Function;
	spaceChange: boolean;
}) {
	const getConfirmationMessage = () => {
		if (assetTypeChange && spaceChange) {
			return CONFIRMATION_MESSAGES.BOTH;
		}
		else if (assetTypeChange) {
			return CONFIRMATION_MESSAGES.ASSET_TYPES;
		}
		else if (spaceChange) {
			return CONFIRMATION_MESSAGES.SPACES;
		}
	};

	return (
		<ClayModal observer={observer} status="warning">
			<ClayModal.Header>
				{Liferay.Language.get('confirm-changes')}
			</ClayModal.Header>

			<ClayModal.Body>{getConfirmationMessage()}</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onOpenChange(false)}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							onClick={async () => {
								onOpenChange(false);

								onSave();
							}}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
