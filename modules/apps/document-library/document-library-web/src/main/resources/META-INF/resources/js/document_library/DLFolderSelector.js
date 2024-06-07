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

const TPL_ERROR_MESSAGES = `<span>{0}</span><ul class="mb-0 mt-2 pl-3">{1}</ul>`;

const DLFolderSelector = ({
	copyActionURL,
	dlObjectIds,
	dlObjectName,
	portletNamespace,
	redirect,
	selectionModalURL,
	size,
	sourceRepositoryId,
}) => {
	const [copyButtonDisabled, setCopyButtonDisabled] = useState(true);
	const [destinationParentFolderName, setDestinationParentFolderName] =
		useState('');
	const [destinationParentFolderId, setDestinationParentFolderId] =
		useState(-1);
	const [destinationRepositoryId, setDestinationRepositoryId] = useState(-1);
	const [destinationRepositoryName, setDestinationRepositoryName] =
		useState('');
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

				setDestinationParentFolderName(selectedItem.resourcename);
				setDestinationParentFolderId(selectedItem.resourceid);
				setDestinationRepositoryId(selectedItem.repositoryid);
				setDestinationRepositoryName(selectedItem.repositoryname);
				setCopyButtonDisabled(false);
			},
			selectEventName: `${portletNamespace}folderSelected`,
			title: Liferay.Language.get('select'),
			url: selectionModalURL,
		});
	};

	const formatErrorMessages = (errorMessages, failedItems) => {
		const errors = errorMessages
			.map((message) => {
				return `<li>${message}</li>`;
			})
			.join('');

		return sub(TPL_ERROR_MESSAGES, [
			failedItems > 1
				? sub(
						Liferay.Language.get('x-items-could-not-be-copied'),
						failedItems
					)
				: sub(
						Liferay.Language.get('x-item-could-not-be-copied'),
						failedItems
					),
			errors,
		]);
	};

	const showErrorMessage = (message) => {
		openToast({
			autoClose: false,
			message,
			title: Liferay.Language.get('error'),
			type: 'danger',
		});
	};

	const handleSubmit = (event) => {
		event.preventDefault();

		const bodyContentObject = objectToFormData({
			[`${portletNamespace}dlObjectIds`]: dlObjectIds,
			[`${portletNamespace}size`]: size,
			[`${portletNamespace}sourceRepositoryId`]: sourceRepositoryId,
			[`${portletNamespace}destinationParentFolderId`]:
				destinationParentFolderId,
			[`${portletNamespace}destinationRepositoryId`]:
				destinationRepositoryId,
		});

		fetch(copyActionURL, {
			body: bodyContentObject,
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({errorMessages, failedItems, successItems}) => {
				if (successItems) {
					openToast({
						message: sub(
							successItems > 1
								? Liferay.Language.get(
										'x-items-were-copied-successfully'
									)
								: Liferay.Language.get(
										'x-item-was-copied-successfully'
									),
							successItems
						),
					});
				}

				if (failedItems > 10) {
					showErrorMessage(
						sub(
							Liferay.Language.get('x-items-could-not-be-copied'),
							failedItems
						)
					);
				}
				else if (errorMessages) {
					showErrorMessage(
						formatErrorMessages(errorMessages, failedItems)
					);
				}
				else {
					navigate(redirect);
				}
			})
			.catch((error) => {
				showErrorMessage(error.message);
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
					placeholder={dlObjectName}
					readOnly
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
