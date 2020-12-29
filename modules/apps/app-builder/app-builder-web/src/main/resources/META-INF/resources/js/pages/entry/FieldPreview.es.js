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
import {SheetSection} from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {DataDefinitionUtils} from 'data-engine-taglib';
import React, {useContext, useEffect, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import {getLocalizedUserPreferenceValue} from '../../utils/lang.es';

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

const getDocumentIcon = (fileName) => {
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
				modal: true,
			},
			title: Liferay.Language.get('file-preview'),
			uri: previewURL,
		});
	};

	const onClickDownload = () => {
		location.href = `${themeDisplay.getPathContext()}/documents/${groupId}/${
			folderId || '0'
		}/${encodeURIComponent(title)}?download=true`;
	};

	return (
		<>
			{displayType === 'list' ? (
				<StringRenderer value={title} />
			) : fileEntryId ? (
				<ClayTooltipProvider>
					<ClayButton.Group className="data-record-document-field mb-2">
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
				<StringRenderer />
			)}
		</>
	);
};

const getRepeatableOptionValues = (value = '') => {
	if (Array.isArray(value)) {
		return value;
	}

	return value
		.substring(1, value.length - 1)
		.split(',')
		.map((v) => v.trim());
};

const OptionsRenderer = ({
	dataDefinitionField,
	displayType,
	getOptionValue,
	values = [],
}) => {
	const {repeatable} = dataDefinitionField;

	const labels = values.map((value) => {
		if (repeatable) {
			let newValue = value;
			newValue = getRepeatableOptionValues(newValue)
				.map(getOptionValue)
				.join(', ');

			return newValue;
		}

		return getOptionValue(value);
	});

	if (displayType === 'list' || labels.length === 0) {
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
	<span className="d-block">
		{(Array.isArray(value) ? value.join(', ') : value) || ' - '}
	</span>
);

export const SectionRenderer = ({
	children,
	collapsible,
	dataDefinition,
	fieldName,
}) => {
	const {userLanguageId} = useContext(AppContext);
	const {label} = DataDefinitionUtils.getDataDefinitionField(
		dataDefinition,
		fieldName
	);
	const localizedLabel = getLocalizedUserPreferenceValue(
		label,
		userLanguageId,
		dataDefinition.defaultLanguageId
	);

	return (
		<ClayPanel
			className="data-record-section"
			collapsable={collapsible}
			defaultExpanded
			displayTitle={
				<SheetSection>
					<div className="autofit-row sheet-subtitle">
						<span className="autofit-col autofit-col-expand">
							<label className="text-uppercase">
								{localizedLabel}
							</label>
						</span>
					</div>
				</SheetSection>
			}
			showCollapseIcon
		>
			<ClayPanel.Body>{children}</ClayPanel.Body>
		</ClayPanel>
	);
};

const getFieldValueRenderer = (
	dataDefinitionField,
	displayType,
	userLanguageId
) => {
	const {
		customProperties,
		defaultLanguageId,
		fieldType,
		repeatable,
	} = dataDefinitionField;
	const {multiple, options} = customProperties;

	const getOptionValue = (value) =>
		DataDefinitionUtils.getOptionLabel(
			options,
			value,
			defaultLanguageId,
			userLanguageId
		);

	const OptionsRendererWrapper = ({value}) => (
		<OptionsRenderer
			dataDefinitionField={dataDefinitionField}
			displayType={displayType}
			getOptionValue={getOptionValue}
			values={value}
		/>
	);

	if (fieldType === 'checkbox_multiple') {
		return OptionsRendererWrapper;
	}

	if (fieldType === 'document_library') {
		return ({value}) => {
			if (repeatable) {
				return value.map((repeatableValue, key) => (
					<DocumentRenderer
						displayType={displayType}
						key={key}
						value={repeatableValue}
					/>
				));
			}

			return <DocumentRenderer displayType={displayType} value={value} />;
		};
	}

	if (fieldType === 'radio') {
		return ({value}) => {
			let newValue = getOptionValue(value);

			if (repeatable) {
				newValue = getRepeatableOptionValues(value).map(getOptionValue);
			}

			return <StringRenderer value={newValue} />;
		};
	}

	if (fieldType === 'select') {
		if (multiple) {
			return OptionsRendererWrapper;
		}

		return ({value = []}) => {
			let newValue = getOptionValue(value[0]);
			if (repeatable) {
				newValue = value
					.map(getRepeatableOptionValues)
					.map((value) => getOptionValue(value[0]));
			}

			return <StringRenderer value={newValue} />;
		};
	}

	return ({value}) => <StringRenderer value={value} />;
};

export const FieldValuePreview = ({
	dataDefinition,
	dataRecordValues,
	displayType = 'form',
	fieldName,
}) => {
	const {userLanguageId} = useContext(AppContext);
	const {defaultLanguageId} = dataDefinition;
	const dataDefinitionField = DataDefinitionUtils.getDataDefinitionField(
		dataDefinition,
		fieldName
	);
	const Renderer = getFieldValueRenderer(
		dataDefinitionField,
		displayType,
		userLanguageId
	);
	const value = dataRecordValues[fieldName];

	if (dataDefinitionField.localizable) {
		return (
			<Renderer value={value ? value[defaultLanguageId] : undefined} />
		);
	}

	return <Renderer value={value} />;
};

export default ({
	dataDefinition,
	dataRecordValues,
	defaultLanguageId,
	fieldName,
}) => {
	const {userLanguageId} = useContext(AppContext);
	const {label} = DataDefinitionUtils.getDataDefinitionField(
		dataDefinition,
		fieldName
	);
	const localizedLabel = getLocalizedUserPreferenceValue(
		label,
		userLanguageId,
		defaultLanguageId
	);

	return (
		<div className="data-record-field-preview">
			<label>{localizedLabel}</label>

			<FieldValuePreview
				dataDefinition={dataDefinition}
				dataRecordValues={dataRecordValues}
				fieldName={fieldName}
			/>
		</div>
	);
};
