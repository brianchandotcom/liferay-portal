/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ListTypeEntryBaseField} from '@liferay/object-js-components-web';
import React from 'react';

import {getUpdatedDefaultValueFieldSettings} from '../../../utils/defaultValues';
import {InputAsValueFieldComponentProps} from '../Tabs/Advanced/DefaultValueContainer';
import {useListTypeEntries} from './useListTypeEntries';

const ListTypeDefaultValueSelect: React.FC<
	{children?: React.ReactNode | undefined} & InputAsValueFieldComponentProps
> = ({
	creationLanguageId,
	defaultValue,
	error,
	label,
	onSubmit,
	required,
	setValues,
	values,
}: InputAsValueFieldComponentProps) => {
	const listTypeEntries = useListTypeEntries(values.listTypeDefinitionId);

	const handleChange = (selected?: ListTypeEntry) => {
		if (selected) {
			const newObjectFieldSettings = getUpdatedDefaultValueFieldSettings(
				values,
				selected.key,
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

	if (!listTypeEntries || !values.listTypeDefinitionId) {
		return null;
	}

	return (
		<ListTypeEntryBaseField
			creationLanguageId={creationLanguageId}
			error={error}
			label={label}
			onChange={handleChange}
			picklistItems={listTypeEntries}
			placeholder={Liferay.Language.get('choose-an-option')}
			required={required}
			selectedPicklistItemKey={defaultValue as string | undefined}
		/>
	);
};

export default ListTypeDefaultValueSelect;
