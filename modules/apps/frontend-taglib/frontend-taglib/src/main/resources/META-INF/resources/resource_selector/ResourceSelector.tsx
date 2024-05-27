/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal} from 'frontend-js-web';
import React, {useState} from 'react';

interface IResourceSelectorProps {
	inputLabel: string;
	inputName: string;
	modalTitle: string;
	portletNamespace: string;
	resourceName: string;
	resourceNameKey?: string;
	resourceValue: string;
	resourceValueKey?: string;
	selectEventName: string;
	selectResourceURL: string;
	showRemoveButton: boolean;
	warningMessage?: boolean;
}

export default function ResourceSelector({
	inputLabel,
	inputName,
	modalTitle,
	portletNamespace,
	resourceName: initialResourceName,
	resourceNameKey: initialResourceNameKey,
	resourceValue: initialResourceValue,
	resourceValueKey: initialResourceValueKey,
	selectEventName,
	selectResourceURL,
	showRemoveButton,
	warningMessage,
}: IResourceSelectorProps) {
	const resourceNameKey = initialResourceNameKey || 'resourcename';
	const resourceValueKey = initialResourceValueKey || 'resourceid';

	const [resourceData, setResourceData] = useState({
		resourceName: initialResourceName,
		resourceValue: initialResourceValue,
		showWarning: Boolean(warningMessage),
	});

	const onResourceRemove = () => {
		setResourceData({
			resourceName: '',
			resourceValue: '0',
			showWarning: false,
		});
	};

	const onResourceSelect = () =>
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					setResourceData({

						// @ts-ignore

						resourceName: selectedItem[resourceNameKey],

						// @ts-ignore

						resourceValue: selectedItem[resourceValueKey],
						showWarning: false,
					});

					const repositoryIdElement = document.getElementById(
						`${portletNamespace}selectedRepositoryId`
					) as HTMLInputElement;

					if (repositoryIdElement) {

						// @ts-ignore

						repositoryIdElement.value = selectedItem.repositoryid;
					}
				}
			},
			selectEventName: `${portletNamespace}${selectEventName}`,
			title: modalTitle,
			url: selectResourceURL,
		});

	return (
		<div className="form-group">
			<ClayForm.Group>
				<ClayInput
					name={`${portletNamespace}${inputName}`}
					type="hidden"
					value={resourceData.resourceValue}
				/>

				<label htmlFor={`${portletNamespace}resourceName`}>
					{inputLabel}
				</label>

				<ClayInput
					disabled
					id={`${portletNamespace}resourceName`}
					type="text"
					value={resourceData.resourceName}
				/>
			</ClayForm.Group>

			{resourceData.showWarning ? (
				<ClayAlert displayType="warning">{warningMessage}</ClayAlert>
			) : null}

			<ClayButton.Group spaced>
				<ClayButton
					displayType="secondary"
					onClick={onResourceSelect}
					type="button"
				>
					{Liferay.Language.get('select')}
				</ClayButton>

				{showRemoveButton ? (
					<ClayButton
						disabled={resourceData.resourceValue === '0'}
						displayType="secondary"
						onClick={onResourceRemove}
						type="button"
					>
						{Liferay.Language.get('remove')}
					</ClayButton>
				) : null}
			</ClayButton.Group>
		</div>
	);
}
