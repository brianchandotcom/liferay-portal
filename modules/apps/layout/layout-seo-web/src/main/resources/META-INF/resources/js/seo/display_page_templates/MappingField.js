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
import React, {useEffect, useState} from 'react';

import MappingPanel from './MappingPanel';

function MappingField({fields, label, name, selectedField, selectedSource}) {
	const inititalValue = `${
		selectedSource && (selectedSource.classType || selectedSource.className)
	}`;
	const [source, setSource] = useState(selectedSource);
	const [field, setField] = useState(selectedField);
	const [value, setValue] = useState(inititalValue);

	const inititalSourceLabel = `${
		selectedSource &&
		(selectedSource.classTypeLabel || selectedSource.classNameLabel)
	}`;

	useEffect(() => {
		if (source) {
			setValue(`${source.classType || source.className}#${field.key}`);
		}
	}, [field.key, source]);

	const handleOnchange = ({field, source}) => {
		setSource(source);
		setField(field);
	};

	return (
		<ClayForm.Group>
			<label className="dpt-mapping-label" htmlFor="fieldSelector">
				<div className="control-label">{label}</div>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							className="dpt-mapping-input"
							readOnly
							type="text"
							value={`${inititalSourceLabel}: ${field.label}`}
						/>
						<ClayInput name={name} type="hidden" value={value} />
					</ClayInput.GroupItem>
					<ClayInput.GroupItem shrink>
						<MappingPanel
							field={field}
							fields={fields}
							onChange={handleOnchange}
							source={{
								...source,
								initialValue: inititalSourceLabel,
							}}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</label>
		</ClayForm.Group>
	);
}

export default MappingField;
