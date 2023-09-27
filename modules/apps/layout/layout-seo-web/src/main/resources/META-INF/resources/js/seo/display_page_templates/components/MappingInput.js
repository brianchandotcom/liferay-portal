/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {PropTypes} from 'prop-types';
import React, {useRef, useState} from 'react';

import MappingPanel from './MappingPanel';

const sanitizeLabel = (label) => label.replace(/}|[\r\n]+/gm, '');
const fieldTemplate = (key, label) => ` $\{${key}:${sanitizeLabel(label)}} `;

function MappingInput({
	component,
	fieldTypes,
	fields,
	helpMessage,
	label,
	name,
	selectedSource,
	value: initialValue,
}) {
	const [source, setSource] = useState(selectedSource);
	const [value, setValue] = useState(initialValue || '');
	const inputElRef = useRef(null);
	const isMounted = useIsMounted();

	const isActive = !!value.trim();

	const inititalSourceLabel = selectedSource
		? selectedSource.classTypeLabel || selectedSource.classNameLabel
		: '';

	const handleOnSelect = ({field, source}) => {
		setSource(source);
		addNewVar(field);
	};

	const addNewVar = ({key, label}) => {
		const selectionStart = inputElRef.current.selectionStart;
		const selectionEnd = inputElRef.current.selectionEnd;
		const fieldVariable = fieldTemplate(key, label);

		setValue((value) =>
			`${value.slice(0, selectionStart)}${fieldVariable}${value.slice(
				selectionEnd
			)}`.trim()
		);

		setTimeout(() => {
			if (isMounted()) {
				inputElRef.current.selectionStart = inputElRef.current.selectionEnd =
					selectionStart + fieldVariable.length;
				inputElRef.current.focus();
			}
		}, 100);
	};

	return (
		<ClayForm.Group>
			<label className="control-label" htmlFor={name}>
				{label}
			</label>

			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						component={component}
						id={name}
						name={name}
						onChange={(event) => {
							setValue(event.target.value);
						}}
						ref={inputElRef}
						value={value}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem shrink>
					<MappingPanel
						clearSelectionOnClose
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

MappingInput.propTypes = {
	component: PropTypes.string,
	helpMessage: PropTypes.string,
	name: PropTypes.string.isRequired,
	selectedSource: PropTypes.shape({
		classNameLabel: PropTypes.string,
		classTypeLabel: PropTypes.string,
	}).isRequired,
	value: PropTypes.string,
};

export default MappingInput;
