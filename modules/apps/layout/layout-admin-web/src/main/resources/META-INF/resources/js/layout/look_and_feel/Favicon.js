/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	portletNamespace,
	themeFaviconCETExternalReferenceCode: initialThemeFaviconCETExternalReferenceCode,
	title: initialTitle,
	url,
}) {
	const onChangeFaviconButtonClick = () => {
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
					className="mb-2"
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
						className="mr-2"
						id={`${portletNamespace}basicInputText`}
						onClick={onChangeFaviconButtonClick}
						readOnly={true}
						value={values.title}
					/>

					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('select-favicon')}
						className="flex-shrink-0 mr-2"
						displayType="secondary"
						onClick={onChangeFaviconButtonClick}
						symbol="change"
						title={Liferay.Language.get('select-favicon')}
					/>

					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('clear-favicon')}
						className="flex-shrink-0"
						disabled={!values.clearButtonEnabled}
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
