/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {getLocalizableLabel} from '../../utils/string';
import {SingleSelect} from '../Select/SingleSelect';

type SelectedPicklistOption = {
	label: string;
	value: string;
};

interface ListTypeEntry {
	externalReferenceCode: string;
	id: number;
	key: string;
	listTypeDefinitionId: number;
	name: string;
	name_i18n: LocalizedValue<string>;
}
interface ListTypeEntryBaseFieldProps {
	creationLanguageId?: Liferay.Language.Locale;
	error?: string;
	label: string;
	onChange: (selected: ListTypeEntry | undefined) => void;
	picklistItems: ListTypeEntry[];
	placeholder?: string;
	required?: boolean;
	selectedPicklistItemKey?: string;
}

export function ListTypeEntryBaseField({
	creationLanguageId,
	error,
	label,
	onChange,
	picklistItems,
	placeholder,
	required,
	selectedPicklistItemKey,
}: ListTypeEntryBaseFieldProps) {
	const handleChange = (selectedPicklistOption: SelectedPicklistOption) => {
		onChange(
			picklistItems.find(
				(item) => item.key === selectedPicklistOption.value
			)
		);
	};

	return (
		<>
			{picklistItems.length ? (
				<SingleSelect
					error={error}
					label={label}
					onChange={handleChange}
					options={picklistItems.map((item) => ({
						label: creationLanguageId
							? getLocalizableLabel(
									creationLanguageId,
									item.name_i18n
							  )
							: item.name,
						value: item.key,
					}))}
					placeholder={placeholder}
					required={required}
					value={
						picklistItems.find(
							(item) => item.key === selectedPicklistItemKey
						)?.name ?? ''
					}
				/>
			) : null}
		</>
	);
}
