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

const DocumentRenderer = ({displayType, value = {}}) => {
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
			{displayType === 'list' ? (
				<StringRenderer value={title} />
			) : fileEntryId ? (
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
			) : (
				<StringRenderer value={' - '} />
			)}
		</>
	);
};

const OptionsRenderer = ({displayType, options, values}) => {
	const labels = values.map(value => getOptionLabel(options, value));

	if (displayType === 'list') {
		return <StringRenderer value={labels.join(', ')} />;
	}

	return (
		<ul>
			{labels.map((label, index) => (
				<li key={index}>{label}</li>
			))}
		</ul>
	);
};

const StringRenderer = ({value}) => (
	<p className="text-dark">{value || ' - '}</p>
);

const getFieldValueRenderer = (dataDefinitionField, displayType) => {
	const {customProperties, fieldType} = dataDefinitionField;

	if (fieldType === 'checkbox_multiple') {
		const {options} = customProperties;

		return ({value}) => (
			<OptionsRenderer
				displayType={displayType}
				options={options}
				values={value}
			/>
		);
	}

	if (fieldType === 'document_library') {
		return ({value}) => (
			<DocumentRenderer displayType={displayType} value={value} />
		);
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
				<OptionsRenderer
					displayType={displayType}
					options={options}
					values={value}
				/>
			);
		}

		return ({value}) => (
			<StringRenderer value={getOptionLabel(options, value[0] || '')} />
		);
	}

	return ({value}) => <StringRenderer value={value} />;
};

export const FieldValuePreview = ({
	dataDefinition,
	dataRecordValues,
	displayType = 'form',
	fieldName
}) => {
	const dataDefinitionField = getDataDefinitionField(
		dataDefinition,
		fieldName
	);

	const Renderer = getFieldValueRenderer(dataDefinitionField, displayType);

	const value = dataRecordValues[fieldName];

	return <Renderer value={value} />;
};

export default ({dataDefinition, dataRecordValues, fieldName}) => {
	const label = getFieldLabel(dataDefinition, fieldName);

	return (
		<div className="data-record-field-preview">
			<label>{label}</label>

			<FieldValuePreview
				dataDefinition={dataDefinition}
				dataRecordValues={dataRecordValues}
				fieldName={fieldName}
			/>
		</div>
	);
};
