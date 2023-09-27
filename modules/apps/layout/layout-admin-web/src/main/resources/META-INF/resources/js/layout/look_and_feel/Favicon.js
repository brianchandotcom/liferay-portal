/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal} from 'frontend-js-web';
import React, {useState} from 'react';

export default function Favicon({
	clearButtonEnabled: initialClearButtonEnabled,
	defaultImgURL,
	defaultTitle,
	faviconFileEntryId: initialFaviconFileEntryId,
	imgURL: initialImgURL,
	isReadOnly,
	portletNamespace,
	themeFaviconCETExternalReferenceCode: initialThemeFaviconCETExternalReferenceCode,
	title: initialTitle,
	url,
}) {
	const onChangeFaviconButtonClick = () => {
		if (isReadOnly) {
			return;
		}

		openSelectionModal({
			iframeBodyCssClass: '',
			onSelect(selectedItem) {
				if (selectedItem) {
					if (selectedItem && selectedItem.value) {
						const itemValue = JSON.parse(selectedItem.value);
						const nextValues = {};

						if (
							selectedItem.returnType ===
							'com.liferay.client.extension.type.item.selector.CETItemSelectorReturnType'
						) {
							nextValues.faviconFileEntryId = 0;
							nextValues.themeFaviconCETExternalReferenceCode =
								itemValue.cetExternalReferenceCode;
						}
						else {
							nextValues.faviconFileEntryId =
								itemValue.fileEntryId;
							nextValues.themeFaviconCETExternalReferenceCode =
								'';
						}

						if (itemValue.url) {
							nextValues.imgURL = itemValue.url;
						}

						nextValues.title = itemValue.title || itemValue.name;
						nextValues.clearButtonEnabled = true;
						setValues(nextValues);
					}
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

		setValues({
			clearButtonEnabled: false,
			faviconFileEntryId: 0,
			imgURL: defaultImgURL,
			themeFaviconCETExternalReferenceCode: '',
			title: defaultTitle,
		});
	};

	const [values, setValues] = useState({
		clearButtonEnabled: initialClearButtonEnabled,
		faviconFileEntryId: initialFaviconFileEntryId,
		imgURL: initialImgURL,
		themeFaviconCETExternalReferenceCode: initialThemeFaviconCETExternalReferenceCode,
		title: initialTitle,
	});

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
