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

import '../FieldBase/FieldBase.es';

import './DocumentLibraryRegister.soy';

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayCard from '@clayui/card';
import {ClayInput} from '@clayui/form';
import {ClayIcon} from '@clayui/icon';
import {
	ItemSelectorDialog,
	createActionURL,
	createPortletURL,
} from 'frontend-js-web';
import React, {useCallback, useMemo, useState} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import templates from './DocumentLibraryAdapter.soy';

function getDocumentLibrarySelectorURL({
	itemSelectorAuthToken,
	portletNamespace,
}) {
	const criterionJSON = {
		desiredItemSelectorReturnTypes:
			'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	};

	const uploadCriterionJSON = {
		URL: getUploadURL(),
		desiredItemSelectorReturnTypes:
			'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
	};

	const documentLibrarySelectorParameters = {
		'0_json': JSON.stringify(criterionJSON),
		'1_json': JSON.stringify(criterionJSON),
		'2_json': JSON.stringify(uploadCriterionJSON),
		criteria:
			'com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion',
		doAsGroupId: themeDisplay.getScopeGroupId(),
		itemSelectedEventName: `${portletNamespace}selectDocumentLibrary`,
		p_p_auth: itemSelectorAuthToken,
		p_p_id: Liferay.PortletKeys.ITEM_SELECTOR,
		p_p_mode: 'view',
		p_p_state: 'pop_up',
		refererGroupId: themeDisplay.getScopeGroupId(),
	};

	const documentLibrarySelectorURL = createPortletURL(
		themeDisplay.getLayoutRelativeControlPanelURL(),
		documentLibrarySelectorParameters
	);

	return documentLibrarySelectorURL.toString();
}

function getUploadURL() {
	const uploadParameters = {
		cmd: 'add_temp',
		'javax.portlet.action': '/document_library/upload_file_entry',
		p_auth: Liferay.authToken,
		p_p_id: Liferay.PortletKeys.DOCUMENT_LIBRARY,
	};

	const uploadURL = createActionURL(
		themeDisplay.getLayoutRelativeURL(),
		uploadParameters
	);

	return uploadURL.toString();
}

const CardItem = ({fileEntryTitle}) => {
	return (
		<ClayCard horizontal>
			<ClayCard.Body>
				<div className="card-col-content card-col-gutters">
					<h4 className="text-truncate" title={fileEntryTitle}>
						{fileEntryTitle}
					</h4>
				</div>

				<div className="card-col-field">
					<a download="{$fileEntryTitle}" href="{$fileEntryURL}">
						<ClayIcon symbol="download" />
					</a>
				</div>
			</ClayCard.Body>
		</ClayCard>
	);
};

function transformFileEntryProperties({fileEntryTitle, fileEntryURL, value}) {
	if (value && typeof value === 'string') {
		try {
			const fileEntry = JSON.parse(value);

			fileEntryTitle = fileEntry.title;

			if (fileEntry.url) {
				fileEntryURL = fileEntry.url;
			}
		}
		catch (e) {
			console.warn('Unable to parse JSON', value);
		}
	}

	return [fileEntryTitle, fileEntryURL];
}

const DocumentLibrary = ({
	fileEntryTitle = '',
	fileEntryURL = '',
	id,
	name,
	onClearButtonClicked,
	onSelectButtonClicked,
	placeholder,
	readOnly,
	value,
}) => {
	const [transformedFileEntryTitle, transformedFileEntryURL] = useMemo(
		() =>
			transformFileEntryProperties({
				fileEntryTitle,
				fileEntryURL,
				value,
			}),
		[fileEntryTitle, fileEntryURL, value]
	);

	return (
		<div className="liferay-ddm-form-field-document-library">
			{transformedFileEntryURL && readOnly ? (
				<CardItem fileEntryTitle={transformedFileEntryTitle} />
			) : (
				<ClayInput.Group>
					<ClayInput.GroupItem prepend>
						<ClayInput
							aria-label={Liferay.Language.get('file')}
							className="field"
							disabled
							id={`${name}inputFile`}
							value={transformedFileEntryTitle}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButton
							className="select-button"
							disabled={readOnly}
							displayType="secondary"
							onClick={onSelectButtonClicked}
						>
							<span className="lfr-btn-label">
								{Liferay.Language.get('select')}
							</span>
						</ClayButton>
					</ClayInput.GroupItem>

					{transformedFileEntryTitle && (
						<ClayInput.GroupItem append shrink>
							<ClayButtonWithIcon
								className="clear-button"
								displayType="secondary"
								onClick={onClearButtonClicked}
								symbol="times"
							/>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			)}

			<ClayInput
				id={id}
				name={name}
				placeholder={placeholder}
				type="hidden"
				value={transformedFileEntryTitle}
			/>
		</div>
	);
};

const DocumentLibraryProxy = connectStore(
	({
		emit,
		id,
		name,
		readOnly,
		fileEntryTitle,
		fileEntryURL,
		itemSelectorAuthToken,
		store,
		placeholder,
		value = {},
		...otherProps
	}) => {
		const [currentValue, setCurrentValue] = useState(value);

		const handleVisibleChange = useCallback(
			event => {
				if (event.selectedItem) {
					emit('fieldFocused', event, event.selectedItem);
				}
				else {
					emit('fieldBlurred', event);
				}
			},
			[emit]
		);

		const handleSelectButtonClicked = useCallback(
			({itemSelectorAuthToken, portletNamespace}) => {
				const itemSelectorDialog = new ItemSelectorDialog({
					eventName: `${portletNamespace}selectDocumentLibrary`,
					singleSelect: true,
					url: getDocumentLibrarySelectorURL({
						itemSelectorAuthToken,
						portletNamespace,
					}),
				});

				itemSelectorDialog.on('selectedItemChange', handleFieldChanged);
				itemSelectorDialog.on('visibleChange', handleVisibleChange);

				itemSelectorDialog.open();
			},
			[handleFieldChanged, handleVisibleChange]
		);

		const handleFieldChanged = useCallback(
			event => {
				const selectedItem = event.selectedItem;

				if (selectedItem) {
					const {value} = selectedItem;

					setCurrentValue(value);

					emit('fieldEdited', event, value);
				}
			},
			[emit]
		);

		return (
			<FieldBaseProxy
				{...otherProps}
				id={id}
				name={name}
				readOnly={readOnly}
				store={store}
			>
				<DocumentLibrary
					fileEntryTitle={fileEntryTitle}
					fileEntryURL={fileEntryURL}
					id={id}
					name={name}
					onClearButtonClicked={event => {
						setCurrentValue(null);

						emit('fieldEdited', event, '{}');
					}}
					onSelectButtonClicked={() =>
						handleSelectButtonClicked({
							itemSelectorAuthToken,
							portletNamespace: store.portletNamespace,
						})
					}
					placeholder={placeholder}
					readOnly={readOnly}
					value={currentValue}
					{...otherProps}
				/>
			</FieldBaseProxy>
		);
	}
);

const ReactDocumentLibraryAdapter = getConnectedReactComponentAdapter(
	DocumentLibraryProxy,
	templates
);

export {ReactDocumentLibraryAdapter};

export default ReactDocumentLibraryAdapter;
