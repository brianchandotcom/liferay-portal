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

import {useFormState} from 'data-engine-js-components-web';
import React, {useMemo} from 'react';

import Select from '../Select/Select.es';

const getSelectedValue = (value) => {
	return typeof value === 'string' && value !== ''
		? JSON.parse(value)
		: value;
};

const ObjectField = ({
	label,
	onChange,
	readOnly,
	spritemap,
	value = {},
	visible,
}) => {
	const {objectFields} = useFormState();
	const selectedValue = getSelectedValue(value);

	const options = useMemo(() => {
		return objectFields.map(({name}) => ({label: name, value: name}));
	}, [objectFields]);

	if (!objectFields.length) {
		return null;
	}

	return (
		<Select
			label={label}
			name="selectedObjectField"
			onChange={onChange}
			options={options}
			placeholder={Liferay.Language.get('choose-an-option')}
			readOnly={readOnly}
			spritemap={spritemap}
			value={selectedValue}
			visible={visible}
		/>
	);
};

export default ObjectField;
