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

import ClayForm, {ClayInput} from '@clayui/form';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import {PropTypes} from 'prop-types';
import React, {useRef, useState} from 'react';

import MappingPanel from './MappingPanel';

const fieldTemplate = (key) => ` $\{${key}} `;

function MappingInput({
	component,
	fieldType,
	fields,
	helpMessage,
	label,
	name,
	selectedSource,
	value: initialValue,
}) {
	const [source, setSource] = useState(selectedSource);
	const [value, setValue] = useState(initialValue || '');
	const inputEl = useRef(null);
	const isMounted = useIsMounted();

	const isActive = !!value.trim();

	const inititalSourceLabel = selectedSource
		? selectedSource.classTypeLabel || selectedSource.classNameLabel
		: '';

	const handleOnSelect = ({field, source}) => {
		setSource(source);
		addNewVar(field);
	};

	const addNewVar = ({key}) => {
		const selectionStart = inputEl.current.selectionStart;
		const selectionEnd = inputEl.current.selectionEnd;
		const fieldVariable = fieldTemplate(key);

		setValue(
			(value) =>
				`${value.slice(0, selectionStart)}${fieldVariable}${value.slice(
					selectionEnd
				)}`
		);

		setTimeout(() => {
			if (isMounted()) {
				inputEl.current.selectionStart = inputEl.current.selectionEnd =
					selectionStart + fieldVariable.length;
				inputEl.current.focus();
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
						ref={inputEl}
						value={value}
					/>
				</ClayInput.GroupItem>
				<ClayInput.GroupItem shrink>
					<MappingPanel
						clearSelectionOnClose
						fieldType={fieldType}
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
