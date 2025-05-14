/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {useState} from 'react';

import {ModalConfirmImport} from '../modal/ModalConfirmImport';

export default function ImportButton({
	checkAnyObjectEntrySelectedFnName,
	copyAsNewCheckboxId,
	deletePortletDataBeforeImportingCheckboxId,
	handleSubmitFnName,
	mirrorWithOverwritingCheckboxId,
}: {
	checkAnyObjectEntrySelectedFnName: string;
	copyAsNewCheckboxId: string;
	deletePortletDataBeforeImportingCheckboxId: string;
	handleSubmitFnName: string;
	mirrorWithOverwritingCheckboxId: string;
}) {
	const [isOpen, setIsOpen] = useState(false);

	const handleSubmit = () => {
		const mirrorCheckbox = document.getElementById(
			mirrorWithOverwritingCheckboxId
		) as HTMLInputElement | null;

		const copyAsNewCheckbox = document.getElementById(
			copyAsNewCheckboxId
		) as HTMLInputElement | null;

		const deleteBeforeImportCheckbox = document.getElementById(
			deletePortletDataBeforeImportingCheckboxId
		) as HTMLInputElement | null;

		const mirrorChecked = mirrorCheckbox?.checked ?? false;
		const copyAsNewChecked = copyAsNewCheckbox?.checked ?? false;
		const deleteBeforeImportChecked =
			deleteBeforeImportCheckbox?.checked ?? false;

		const isAnyChecked = (window as any)[
			checkAnyObjectEntrySelectedFnName
		]?.();

		const showModal =
			isAnyChecked &&
			(mirrorChecked || copyAsNewChecked || deleteBeforeImportChecked);

		showModal ? setIsOpen(true) : (window as any)[handleSubmitFnName]?.();
	};

	return (
		<div>
			<ClayButton.Group spaced>
				<ClayButton onClick={handleSubmit}>
					{Liferay.Language.get('Import')}
				</ClayButton>
			</ClayButton.Group>

			{isOpen && (
				<ModalConfirmImport
					handleOnClose={() => setIsOpen(false)}
					handleSubmitFnName={handleSubmitFnName}
				/>
			)}
		</div>
	);
}
