/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import {Col} from '@clayui/layout';
import React, {useEffect, useReducer, useState} from 'react';

import {
	CSV_FORMAT,
	DISALLOWED_CSV_ENTITY_TYPES,
	EXPORT_FILE_FORMAT_SELECTED_EVENT,
} from '../constants';

function ExportSettings({
	externalTypeId,
	externalTypeInitialOptions,
	externalTypeLabel,
	externalTypeName,
	internalClassNameKeyId,
	internalClassNameKeyInitialOptions,
	internalClassNameKeyLabel,
	internalClassNameKeyName,
}) {
	const [
		selectedExternalTypeOption,
		setSelectedExternalTypeOption,
	] = useState(externalTypeInitialOptions[0].value);
	const [
		selectedinternalClassNameKeyName,
		setSelectedinternalClassNameKeyName,
	] = useState();

	const [
		internalClassNameKeyOptions,
		dispatchInternalClassNameKeyOptions,
	] = useReducer((state, action) => {
		if (action === 'update') {
			if (selectedExternalTypeOption === CSV_FORMAT.toUpperCase()) {
				return internalClassNameKeyInitialOptions.filter(
					(item) => !DISALLOWED_CSV_ENTITY_TYPES.includes(item.value)
				);
			}
			else {
				return internalClassNameKeyInitialOptions;
			}
		}
	}, internalClassNameKeyInitialOptions);

	useEffect(() => {
		dispatchInternalClassNameKeyOptions('update');
	}, [selectedExternalTypeOption]);

	useEffect(() => {
		Liferay.fire(EXPORT_FILE_FORMAT_SELECTED_EVENT, {
			selectedExportFileFormat: selectedExternalTypeOption,
			selectedSchema: selectedinternalClassNameKeyName,
		});
	}, [selectedExternalTypeOption, selectedinternalClassNameKeyName]);

	return (
		<>
			<Col className="col-md-6">
				<div className="form-group">
					<label htmlFor={internalClassNameKeyId}>
						{internalClassNameKeyLabel}
					</label>

					<ClaySelect
						aria-label={internalClassNameKeyLabel}
						id={internalClassNameKeyId}
						name={internalClassNameKeyName}
						onChange={(event) =>
							setSelectedinternalClassNameKeyName(
								event.target.value
							)
						}
					>
						{internalClassNameKeyOptions.map((item) => (
							<ClaySelect.Option
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}
					</ClaySelect>
				</div>
			</Col>
			<Col className="col-md-6">
				<div className="form-group">
					<label htmlFor={externalTypeId}>{externalTypeLabel}</label>

					<ClaySelect
						aria-label={externalTypeLabel}
						id={externalTypeId}
						name={externalTypeName}
						onChange={(event) =>
							setSelectedExternalTypeOption(event.target.value)
						}
					>
						{externalTypeInitialOptions.map((item) => (
							<ClaySelect.Option
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}
					</ClaySelect>
				</div>
			</Col>
		</>
	);
}

export default ExportSettings;
