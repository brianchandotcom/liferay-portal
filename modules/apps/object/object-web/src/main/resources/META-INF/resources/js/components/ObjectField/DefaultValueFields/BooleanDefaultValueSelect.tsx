/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {BooleanEntryBaseField} from '@liferay/object-js-components-web';
import React from 'react';

import {getUpdatedDefaultValueFieldSettings} from '../../../utils/defaultValues';
import {InputAsValueFieldComponentProps} from '../Tabs/Advanced/DefaultValueContainer';

const BooleanDefaultValueSelect: React.FC<
	{children?: React.ReactNode | undefined} & InputAsValueFieldComponentProps
> = ({
	defaultValue,
	error,
	label,
	onSubmit,
	required,
	setValues,
	values,
}: InputAsValueFieldComponentProps) => {
	const handleChange = (selected?: string) => {
		if (selected) {
			const newObjectFieldSettings = getUpdatedDefaultValueFieldSettings(
				values,
				selected,
				'inputAsValue'
			);

			setValues({
				objectFieldSettings: newObjectFieldSettings,
			});

			if (onSubmit) {
				onSubmit({
					...values,
					objectFieldSettings: newObjectFieldSettings,
				});
			}
		}
	};

	return (
		<BooleanEntryBaseField
			error={error}
			label={label}
			onChange={handleChange}
			placeholder={Liferay.Language.get('choose-an-option')}
			required={required}
			selectedBooleanItem={
				typeof defaultValue === 'string' ? defaultValue : ''
			}
		/>
	);
};

export default BooleanDefaultValueSelect;
