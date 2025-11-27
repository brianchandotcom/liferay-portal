/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal} from 'frontend-js-components-web';
import React, {useState} from 'react';

export default function Favicon({
	clearButtonEnabled: initialClearButtonEnabled,
	defaultImgURL,
	defaultTitle,
	faviconFileEntryERC: initialFaviconFileEntryERC,
	faviconFileEntryId: initialFaviconFileEntryId,
	faviconFileEntryScopeERC: initialFaviconFileEntryScopeERC,
	imgURL: initialImgURL,
	isReadOnly,
	portletNamespace,
	themeFaviconCETExternalReferenceCode:
		initialThemeFaviconCETExternalReferenceCode,
	title: initialTitle,
	url,
}) {
	const [values, setValues] = useState({
		clearButtonEnabled: initialClearButtonEnabled,
		faviconFileEntryERC: initialFaviconFileEntryERC,
		faviconFileEntryId: initialFaviconFileEntryId,
		faviconFileEntryScopeERC: initialFaviconFileEntryScopeERC,
		imgURL: initialImgURL,
		themeFaviconCETExternalReferenceCode:
			initialThemeFaviconCETExternalReferenceCode,
		title: initialTitle,
	});

	const updateFaviconState = ({
		clearButtonEnabled = true,
		faviconFileEntryERC = '',
		faviconFileEntryId = 0,
		faviconFileEntryScopeERC = '',
		imgURL,
		themeFaviconCETExternalReferenceCode = '',
		title,
	}) => {
		setValues((prevValues) => ({
			...prevValues,
			clearButtonEnabled,
			faviconFileEntryERC,
			faviconFileEntryId,
			faviconFileEntryScopeERC,
			imgURL,
			themeFaviconCETExternalReferenceCode,
			title,
		}));
	};

	const onChangeFaviconButtonClick = () => {
		if (isReadOnly) {
			return;
		}

		openSelectionModal({
			iframeBodyCssClass: '',
			onSelect(selectedItem) {
				if (selectedItem && selectedItem.value) {
					const itemValue = JSON.parse(selectedItem.value);

					if (
						selectedItem.returnType ===
						'com.liferay.client.extension.type.item.selector.CETItemSelectorReturnType'
					) {
						updateFaviconState({
							imgURL: itemValue.url,
							themeFaviconCETExternalReferenceCode:
								itemValue.cetExternalReferenceCode,
							title: itemValue.title || itemValue.name,
						});

						return;
					}

					let faviconFileEntryScopeERC = '';

					if (
						String(itemValue.groupId) !==
						String(Liferay.ThemeDisplay.getScopeGroupId())
					) {
						faviconFileEntryScopeERC =
							itemValue.groupExternalReferenceCode || '';
					}

					updateFaviconState({
						faviconFileEntryERC: itemValue.externalReferenceCode,
						faviconFileEntryId: itemValue.fileEntryId,
						faviconFileEntryScopeERC,
						imgURL: itemValue.url,
						title: itemValue.title || itemValue.name,
					});
				}
			},
			selectEventName: `${portletNamespace}selectImage`,
			title: Liferay.Language.get('select-favicon'),
			url: url.toString(),
		});
	};

	const onClearFaviconButtonClick = () => {
		if (isReadOnly) {
			return;
		}

		updateFaviconState({
			clearButtonEnabled: false,
			imgURL: defaultImgURL,
			title: defaultTitle,
		});
	};

	return (
		<>
			<ClayInput
				name={`${portletNamespace}themeFaviconCETExternalReferenceCode`}
				type="hidden"
				value={values.themeFaviconCETExternalReferenceCode}
			/>
			<ClayInput
				name={`${portletNamespace}faviconFileEntryId`}
				type="hidden"
				value={values.faviconFileEntryId}
			/>
			<ClayInput
				name={`${portletNamespace}faviconFileEntryERC`}
				type="hidden"
				value={values.faviconFileEntryERC}
			/>
			<ClayInput
				name={`${portletNamespace}faviconFileEntryScopeERC`}
				type="hidden"
				value={values.faviconFileEntryScopeERC}
			/>

			{values.imgURL && (
				<img
					alt={values.title}
					className="c-mb-2"
					height="16"
					src={values.imgURL}
					width="16"
				/>
			)}

			<ClayForm.Group>
				<label htmlFor={`${portletNamespace}basicInputText`}>
					{Liferay.Language.get('favicon')}
				</label>

				<div className="d-flex">
					<ClayInput
						className="c-mr-2"
						id={`${portletNamespace}basicInputText`}
						onClick={onChangeFaviconButtonClick}
						readOnly={true}
						value={values.title}
					/>

					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('select-favicon')}
						className="c-mr-2 flex-shrink-0"
						disabled={isReadOnly}
						displayType="secondary"
						onClick={onChangeFaviconButtonClick}
						symbol="change"
						title={Liferay.Language.get('select-favicon')}
					/>

					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('clear-favicon')}
						className="flex-shrink-0"
						disabled={!values.clearButtonEnabled || isReadOnly}
						displayType="secondary"
						onClick={onClearFaviconButtonClick}
						symbol="times-circle"
						title={Liferay.Language.get('clear-favicon')}
					/>
				</div>
			</ClayForm.Group>
		</>
	);
}
