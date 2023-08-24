/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {
	fetch,
	navigate,
	objectToFormData,
	openSelectionModal,
	openToast,
	sub,
} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

const DLFolderSelector = ({
	copyActionURL,
	fileShortcutId,
	itemType,
	portletNamespace,
	redirect,
	selectionModalURL,
	sourceFileEntryId,
	sourceFileName,
	sourceFolderId,
	sourceFolderName,
	sourceRepositoryId,
}) => {
	const [copyButtonDisabled, setCopyButtonDisabled] = useState(true);
	const [
		destinationParentFolderName,
		setDestinationParentFolderName,
	] = useState('');
	const [destinationParentFolderId, setDestinationParentFolderId] = useState(
		-1
	);
	const [destinationRepositoryId, setDestinationRepositoryId] = useState(-1);
	const [destinationRepositoryName, setDestinationRepositoryName] = useState(
		''
	);
	const [placeholder, setPlaceholder] = useState('');

	useEffect(() => {
		if (
			destinationRepositoryId === -1 ||
			sourceRepositoryId === undefined ||
			sourceRepositoryId === destinationRepositoryId
		) {
			setPlaceholder(destinationParentFolderName);
		}
		else {
			setPlaceholder(
				`(${destinationRepositoryName}) ${destinationParentFolderName}`
			);
		}
	}, [
		destinationRepositoryName,
		destinationRepositoryId,
		destinationParentFolderName,
		sourceRepositoryId,
	]);

	const handleSelectButtonClick = (event) => {
		event.preventDefault();

		openSelectionModal({
			onSelect(selectedItem) {
				if (!selectedItem) {
					return;
				}

				setDestinationParentFolderName(selectedItem.foldername);
				setDestinationParentFolderId(selectedItem.folderid);
				setDestinationRepositoryId(selectedItem.repositoryid);
				setDestinationRepositoryName(selectedItem.repositoryname);
				setCopyButtonDisabled(false);
			},
			selectEventName: `${portletNamespace}folderSelected`,
			title: sub(Liferay.Language.get('select')),
			url: selectionModalURL,
		});
	};

	const handleSubmit = (event) => {
		event.preventDefault();

		const bodyContentObject = objectToFormData(
			itemType === 'folder'
				? {
						[`${portletNamespace}sourceRepositoryId`]: sourceRepositoryId,
						[`${portletNamespace}sourceFolderId`]: sourceFolderId,
						[`${portletNamespace}destinationParentFolderId`]: destinationParentFolderId,
						[`${portletNamespace}destinationRepositoryId`]: destinationRepositoryId,
				  }
				: {
						[`${portletNamespace}fileEntryId`]: sourceFileEntryId,
						[`${portletNamespace}fileShortcutId`]: fileShortcutId,
						[`${portletNamespace}destinationFolderId`]: destinationParentFolderId,
						[`${portletNamespace}destinationRepositoryId`]: destinationRepositoryId,
				  }
		);

		fetch(copyActionURL, {
			body: bodyContentObject,
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({errorMessage}) => {
				if (errorMessage) {
					openToast({
						message: errorMessage,
						title: Liferay.Language.get('error'),
						type: 'danger',
					});
				}
				else {
					navigate(redirect);
				}
			})
			.catch((error) => {
				openToast({
					message: error.message,
					title: Liferay.Language.get('error'),
					type: 'danger',
				});
			});
	};

	return (
		<ClayForm onSubmit={handleSubmit}>
			<ClayAlert
				className="c-mb-4"
				displayType="warning"
				title={Liferay.Language.get('alert')}
			>
				{Liferay.Language.get('document-library-copy-folder-help')}
			</ClayAlert>

			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}copyFromInput`}>
					{Liferay.Language.get('copy-from')}
				</label>

				<ClayInput
					className="c-mb-3"
					disabled
					id={`${portletNamespace}copyFromInput`}
					placeholder={sourceFolderName || sourceFileName}
					type="text"
				/>

				<label htmlFor={`${portletNamespace}copyToInput`}>
					{Liferay.Language.get('copy-to')}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							disabled
							id={`${portletNamespace}copyToInput`}
							placeholder={placeholder}
							type="text"
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							displayType="secondary"
							onClick={handleSelectButtonClick}
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<ClayButton.Group spaced>
				<ClayButton
					disabled={copyButtonDisabled}
					displayType="primary"
					type="submit"
				>
					{Liferay.Language.get('copy')}
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => navigate(redirect)}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayButton.Group>
		</ClayForm>
	);
};

export default DLFolderSelector;
