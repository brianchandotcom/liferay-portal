/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API, PicklistEntryBaseField} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {getUpdatedDefaultValueFieldSettings} from '../../../utils/defaultValues';
import {fixLocaleKeys} from '../../ListTypeDefinition/utils';
import {InputAsValueFieldComponentProps} from '../Tabs/Advanced/DefaultValueContainer';
import {
	getListTypeDefinitionListTypeEntries
} from "@liferay/object-js-components-web/types/src/main/resources/META-INF/resources/utils/api";

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
			API.getListTypeDefinitionListTypeEntries(values.listTypeDefinitionId).then((items) => {
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
