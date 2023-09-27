/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import {PropTypes} from 'prop-types';
import React, {useState} from 'react';

import {UNMAPPED_OPTION} from '../constants';
import MappingPanel from './MappingPanel';

function MappingSelector({
	fieldTypes = [],
	fields: initialFields,
	helpMessage,
	label,
	name,
	selectedFieldKey,
	selectedSource,
}) {
	const fields = [UNMAPPED_OPTION, ...initialFields];
	const [source, setSource] = useState(selectedSource);
	const [field, setField] = useState(
		fields.find(({key}) => key === selectedFieldKey) || UNMAPPED_OPTION
	);

	const isActive = !!field && field.key !== UNMAPPED_OPTION.key;

	const inititalSourceLabel = selectedSource
		? selectedSource.classTypeLabel || selectedSource.classNameLabel
		: '';

	const handleOnSelect = ({field, source}) => {
		setSource(source);
		setField(field);
	};

	return (
		<ClayForm.Group>
			<label className="control-label" htmlFor={name}>
				{label}
			</label>

			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						className="dpt-mapping-input"
						id={name}
						readOnly
						type="text"
						value={`${
							(isActive &&
								inititalSourceLabel &&
								`${inititalSourceLabel}: `) ||
							''
						}${field.label}`}
					/>

					<ClayInput name={name} type="hidden" value={field.key} />
				</ClayInput.GroupItem>

				<ClayInput.GroupItem shrink>
					<MappingPanel
						field={field}
						fieldTypes={fieldTypes}
						fields={fields}
						isActive={isActive}
						name={name}
						onSelect={handleOnSelect}
						source={{
							...source,
							initialValue: inititalSourceLabel,
						}}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			{helpMessage && <ClayForm.Text>{helpMessage}</ClayForm.Text>}
		</ClayForm.Group>
	);
}

MappingSelector.propTypes = {
	fieldTypes: PropTypes.array,
	helpMessage: PropTypes.string,
	name: PropTypes.string.isRequired,
	selectedFieldKey: PropTypes.string,
	selectedSource: PropTypes.shape({
		classNameLabel: PropTypes.string,
		classTypeLabel: PropTypes.string,
	}).isRequired,
};

export default MappingSelector;
