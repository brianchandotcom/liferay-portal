/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import React, {useState} from 'react';

export default function ImportStructureModalContent({
	closeModal,
}: {
	closeModal: () => void;
}) {
	const [warning, setWarning] = useState(true);
	const [jsonFile, setJsonFile] = useState(null);

	const onAddButtonClick = () => {
		console.log('onAddButtonClick');
		setJsonFile('something');
	};

	const inputId = 'jsonInputId';

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('import-and-override-structure')}
			</ClayModal.Header>

			{warning && (
				<ClayAlert
					displayType="warning"
					onClose={() => {
						setWarning(false);
					}}
					title={`${Liferay.Language.get('warning')}:`}
					variant="stripe"
				>
					{Liferay.Language.get(
						'import-and-override-structure-warning-message'
					)}
				</ClayAlert>
			)}

			<ClayModal.Body>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<label htmlFor={inputId}>
							{Liferay.Language.get('json-file')}
						</label>

						<ClayInput id={inputId} value={jsonFile || ''} />
					</ClayInput.GroupItem>

					<ClayInput.GroupItem className="mt-4" shrink>
						{jsonFile ? (
							<>
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'change-file'
									)}
									className="lfr-portal-tooltip"
									displayType="secondary"
									symbol="change"
									title={Liferay.Language.get('change-file')}
									type="button"
								/>

								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'remove-file'
									)}
									className="lfr-portal-tooltip"
									displayType="unstyled"
									symbol="trash"
									title={Liferay.Language.get('remove-file')}
									type="button"
								/>
							</>
						) : (
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('add')}
								className="lfr-portal-tooltip"
								displayType="secondary"
								onClick={onAddButtonClick}
								symbol="plus"
								title={Liferay.Language.get('add')}
								type="button"
							/>
						)}
					</ClayInput.GroupItem>
				</ClayInput.Group>
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

						<ClayButton displayType="primary">
							{Liferay.Language.get('import-and-override')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
