/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

import {FieldFile} from '../forms';

const JSON_EXTENSION = '.json';

export default function ImportStructureModalContent({
	closeModal,
	importURL,
	objectFolderExternalReferenceCode,
}: {
	closeModal: () => void;
	importURL: string;
	objectFolderExternalReferenceCode: string;
}) {
	const [warning, setWarning] = useState(true);
	const [jsonFile, setJsonFile] = useState<File | null>(null);

	const onFileChange = (file: File | null) => {
		setJsonFile(file);
	};

	const onImportButtonClick = () => {
		const formData = new FormData();

		formData.append(
			'objectFolderExternalReferenceCode',
			objectFolderExternalReferenceCode
		);

		if (jsonFile) {
			formData.append('objectDefinitionJSON', new Blob([jsonFile]));
		}

		fetch(importURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => {
				if (response.ok) {
					closeModal();
				}
				else {
					return response.json();
				}
			})
			.then((response) => {
				console.log(response);
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
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
				<FieldFile
					fieldId="jsonFileId"
					label={Liferay.Language.get('json-file')}
					onFileChange={onFileChange}
					validExtensions={JSON_EXTENSION}
				/>
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
							disabled={!jsonFile}
							displayType="primary"
							onClick={onImportButtonClick}
						>
							{Liferay.Language.get('import-and-override')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
