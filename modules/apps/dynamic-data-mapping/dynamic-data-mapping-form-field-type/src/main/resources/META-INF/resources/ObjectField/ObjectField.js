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
import {getFields} from 'data-engine-js-components-web/js/utils/fields.es';
import {
	getObjectFieldName,
	getSelectedValue,
} from 'data-engine-js-components-web/js/utils/objectFields';
import React, {useMemo} from 'react';

import Select from '../Select/Select.es';

const ObjectField = ({
	label,
	objectFields,
	onChange,
	readOnly,
	spritemap,
	value = {},
	visible,
}) => {
	const {builderPages} = useFormState();

	const options = useMemo(() => {
		const mappedOptions = getFields(builderPages)
			.map(({settingsContext}) => {
				const objectFieldName = getObjectFieldName(settingsContext);

				return (
					objectFieldName && getSelectedValue(objectFieldName.value)
				);
			})
			.filter(Boolean);

		return objectFields.map(({name}) => {
			return {
				disabled: !!mappedOptions.includes(name),
				label: name,
				value: name,
			};
		});
	}, [builderPages, objectFields]);

	return (
		<Select
			label={label}
			name="selectedObjectField"
			onChange={onChange}
			options={options}
			placeholder={Liferay.Language.get('choose-an-option')}
			readOnly={readOnly}
			spritemap={spritemap}
			value={getSelectedValue(value)}
			visible={visible}
		/>
	);
};

const ObjectFieldWrapper = (props) => {
	const {objectFields} = useFormState();

	if (!objectFields.length) {
		return null;
	}

	return <ObjectField objectFields={objectFields} {...props} />;
};

export default ObjectFieldWrapper;
