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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useEffect, useState} from 'react';

const getDataDefinitionField = (dataDefinition, fieldName) => {
	return dataDefinition.dataDefinitionFields.find(field => {
		return field.name === fieldName;
	}, fieldName);
};

const getFieldLabel = (dataDefinition, fieldName) => {
	const field = getDataDefinitionField(dataDefinition, fieldName);

	return field ? field.label[themeDisplay.getLanguageId()] : fieldName;
};

const getOptionLabel = (options = {}, value) => {
	return (options[themeDisplay.getLanguageId()] || []).reduce(
		(result, option) => {
			if (option.value === value) {
				return option.label;
			}

			return result;
		},
		value
	);
};

const createFileEntryPreviewURL = (groupId, fileEntryId) => {
	const portletURL = Liferay.PortletURL.createURL(
		themeDisplay.getLayoutRelativeControlPanelURL()
	);

	portletURL.setParameter('doAsGroupId', groupId);
	portletURL.setParameter('fileEntryId', fileEntryId);
	portletURL.setParameter(
		'mvcRenderCommandName',
		'/document_library/view_file_entry'
	);
	portletURL.setParameter('p_p_auth', Liferay.authToken);
	portletURL.setParameter('refererGroupId', themeDisplay.getScopeGroupId());

	portletURL.setPortletId(Liferay.PortletKeys.DOCUMENT_LIBRARY);
	portletURL.setPortletMode('view');
	portletURL.setWindowState('pop_up');

	return portletURL.toString();
};

const getDocumentIcon = fileName => {
	const extension = fileName.split('.').pop();

	if (extension === 'pdf') {
		return 'document-pdf';
	}

	if (extension === 'doc' || extension === 'txt') {
		return 'document-text';
	}

	return 'document-default';
};

const DocumentRenderer = ({value = {}}) => {
	let fileEntry = {};

	if (typeof value === 'string' && value.startsWith('{')) {
		fileEntry = JSON.parse(value);
	}

	const {fileEntryId, folderId, groupId, title = ''} = fileEntry;
	const [previewURL, setPreviewURL] = useState('');

	useEffect(() => {
		AUI().use('liferay-portlet-url', () => {
			setPreviewURL(createFileEntryPreviewURL(groupId, fileEntryId));
		});
	});

	const onClickPreview = () => {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				modal: true
			},
			title: Liferay.Language.get('file-preview'),
			uri: previewURL
		});
	};

	const onClickDownload = () => {
		location.href = `${themeDisplay.getPathContext()}/documents/${groupId}/${folderId ||
			'0'}/${encodeURIComponent(title)}?download=true`;
	};

	return (
		<>
			{fileEntryId && (
				<ClayTooltipProvider>
					<ClayButton.Group className="data-record-document-field">
						<ClayButton
							data-tooltip-align="bottom"
							data-tooltip-delay="200"
							displayType="secondary"
							onClick={onClickPreview}
							title={Liferay.Language.get('file-preview')}
						>
							<ClayIcon
								className="mr-2"
								symbol={getDocumentIcon(title)}
							/>
							{title}
						</ClayButton>
						<ClayButtonWithIcon
							data-tooltip-align="bottom"
							data-tooltip-delay="200"
							displayType="secondary"
							onClick={onClickDownload}
							symbol="download"
							title={Liferay.Language.get('download')}
						/>
					</ClayButton.Group>
				</ClayTooltipProvider>
			)}
		</>
	);
};

const OptionsRenderer = ({options, values}) => (
	<ul>
		{values.map((value, index) => (
			<li key={index}>{getOptionLabel(options, value)}</li>
		))}
	</ul>
);

const StringRenderer = ({value}) => <p>{value}</p>;

const getFieldValueRenderer = dataDefinitionField => {
	const {customProperties, fieldType} = dataDefinitionField;

	if (fieldType === 'checkbox_multiple') {
		const {options} = customProperties;

		return ({value}) => (
			<OptionsRenderer options={options} values={value} />
		);
	}

	if (fieldType === 'document_library') {
		return ({value}) => <DocumentRenderer value={value} />;
	}

	if (fieldType === 'grid') {
		return ({value}) => <StringRenderer value={Object.keys(value)} />;
	}

	if (fieldType === 'radio') {
		const {options} = customProperties;

		return ({value}) => (
			<StringRenderer value={getOptionLabel(options, value)} />
		);
	}

	if (fieldType === 'select') {
		const {multiple, options} = customProperties;

		if (multiple) {
			return ({value}) => (
				<OptionsRenderer options={options} values={value} />
			);
		}

		return ({value}) => (
			<StringRenderer value={getOptionLabel(options, value[0] || '')} />
		);
	}

	return ({value}) => <StringRenderer value={value} />;
};

export default ({dataDefinition, dataRecordValues, fieldName}) => {
	const dataDefinitionField = getDataDefinitionField(
		dataDefinition,
		fieldName
	);
	const label = getFieldLabel(dataDefinition, fieldName);
	const value = dataRecordValues[fieldName];

	const Renderer = getFieldValueRenderer(dataDefinitionField);

	return (
		<div className="data-record-field-preview">
			<label>{label}</label>

			<Renderer value={value} />
		</div>
	);
};
