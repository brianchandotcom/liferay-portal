/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {FolderAction} from './FolderItemSelectorModalContent';

export type Option = 'keep' | 'replace';

export default function DuplicatedAssetFolderNamesModalContent({
	action,
	closeModal,
	itemData,
	onContinueClick,
}: {
	action: FolderAction;
	closeModal: () => void;
	itemData: ItemData;
	onContinueClick: (option: Option) => void;
}) {
	const [operation, setOperation] = useState<Option>('replace');

	const handleContinueClick = async () => {
		onContinueClick(operation);

		closeModal();
	};

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{action === 'copy'
					? Liferay.Language.get('copy')
					: Liferay.Language.get('move')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p>
					{sub(
						itemData.entryClassName ===
							'com.liferay.object.model.ObjectEntryFolder'
							? Liferay.Language.get(
									'an-older-folder-named-x-already-exists-in-this-folder.-choose-one-of-the-following-options'
								)
							: Liferay.Language.get(
									'an-older-asset-named-x-already-exists-in-this-folder.-choose-one-of-the-following-options'
								),
						itemData.title
					)}
				</p>

				<ClayRadioGroup
					defaultValue="replace"
					onChange={(value) => setOperation(value as Option)}
				>
					<ClayRadio
						label={sub(
							Liferay.Language.get('replace-x'),
							itemData.title
						)}
						value="replace"
					/>

					<ClayRadio
						label={Liferay.Language.get('keep-both')}
						value="keep"
					/>
				</ClayRadioGroup>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={handleContinueClick}
						>
							{Liferay.Language.get('continue')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
