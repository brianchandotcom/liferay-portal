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

import {API, PicklistEntryBaseField} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {getUpdatedDefaultValueFieldSettings} from '../../../utils/defaultValues';
import {fixLocaleKeys} from '../../ListTypeDefinition/utils';
import {InputAsValueFieldComponentProps} from '../Tabs/Advanced/DefaultValueContainer';

const PicklistDefaultValueSelect: React.FC<InputAsValueFieldComponentProps> = ({
	creationLanguageId,
	defaultValue,
	error,
	label,
	required,
	setValues,
	values,
}: InputAsValueFieldComponentProps) => {
	const [picklistItems, setPicklistItems] = useState<PickListItem[]>();

	const handleChange = (selected?: PickListItem) => {
		if (selected) {
			setValues({
				objectFieldSettings: getUpdatedDefaultValueFieldSettings(
					values,
					selected.key,
					'inputAsValue'
				),
			});
		}
	};

	useEffect(() => {
		if (values.listTypeDefinitionId) {
			API.getPickListItems(values.listTypeDefinitionId).then((items) => {
				if (items.length) {
					setPicklistItems(
						items.map((item) => ({
							...item,
							name_i18n: fixLocaleKeys(item.name_i18n),
						}))
					);
				}
			});
		}
	}, [defaultValue, setValues, values, values.listTypeDefinitionId]);

	return (
		<>
			{picklistItems && values.listTypeDefinitionId && (
				<PicklistEntryBaseField
					creationLanguageId={creationLanguageId}
					error={error}
					label={label}
					onChange={handleChange}
					picklistItems={picklistItems}
					placeholder={Liferay.Language.get('choose-an-option')}
					required={required}
					selectedPicklistItemKey={defaultValue as string | undefined}
				/>
			)}
		</>
	);
};

export default PicklistDefaultValueSelect;
