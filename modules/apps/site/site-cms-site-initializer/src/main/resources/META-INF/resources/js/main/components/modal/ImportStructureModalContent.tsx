/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

const JSON_EXTENSION = '.json';

export default function ImportStructureModalContent({
	closeModal,
}: {
	closeModal: () => void;
}) {
	const [warning, setWarning] = useState(true);
	const [jsonFile, setJsonFile] = useState<File | null>(null);
	const fileInputRef = useRef<HTMLInputElement>();

	const resetFileInput = () => {
		if (fileInputRef && fileInputRef.current) {
			fileInputRef.current.value = '';
		}

		setJsonFile(null);
	};

	const onChangeClick = () => {
		fileInputRef.current?.click();

		resetFileInput();
	};

	const handleFileInputChange = async ({
		target,
	}: React.ChangeEvent<HTMLInputElement>) => {
		if (
			!target.value.endsWith(JSON_EXTENSION) ||
			!target.files ||
			target.files?.length === 0
		) {
			return;
		}

		setJsonFile(target.files[0]);
	};

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
						<label htmlFor="jsonInputId">
							{Liferay.Language.get('json-file')}
						</label>

						<ClayInput
							id="jsonInputId"
							value={jsonFile?.name || ''}
						/>

						<ClayInput
							accept={JSON_EXTENSION}
							className="d-none"
							id="fileInputId"
							name="fileInputId"
							onChange={handleFileInputChange}

							// @ts-ignore

							ref={fileInputRef}
							type="file"
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem className="mt-4" shrink>
						{jsonFile ? (
							<>
								<ClayButtonWithIcon
									aria-label={sub(
										Liferay.Language.get('change-x'),
										Liferay.Language.get('file')
									)}
									className="lfr-portal-tooltip"
									displayType="secondary"
									onClick={onChangeClick}
									symbol="change"
									title={sub(
										Liferay.Language.get('change-x'),
										Liferay.Language.get('file')
									)}
									type="button"
								/>

								<ClayButtonWithIcon
									aria-label={sub(
										Liferay.Language.get('remove-x'),
										Liferay.Language.get('file')
									)}
									className="lfr-portal-tooltip"
									displayType="unstyled"
									onClick={resetFileInput}
									symbol="trash"
									title={sub(
										Liferay.Language.get('remove-x'),
										Liferay.Language.get('file')
									)}
									type="button"
								/>
							</>
						) : (
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('add')}
								className="lfr-portal-tooltip"
								displayType="secondary"
								onClick={() => fileInputRef.current?.click()}
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
