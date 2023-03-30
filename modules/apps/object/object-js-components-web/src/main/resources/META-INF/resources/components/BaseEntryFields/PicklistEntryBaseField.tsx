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

import React from 'react';

import {getLocalizableLabel} from '../../utils/string';
import {SingleSelect} from '../Select/SingleSelect';

type SelectedPicklistOption = {
	label: string;
	value: string;
};

interface PickListItem {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: LocalizedValue<string>;
}
interface PicklistEntryBaseFieldProps {
	creationLanguageId?: Liferay.Language.Locale;
	error?: string;
	label: string;
	onChange: (selected: PickListItem | undefined) => void;
	picklistItems: PickListItem[];
	placeholder?: string;
	required?: boolean;
	selectedPicklistItemKey?: string;
}

export function PicklistEntryBaseField({
	creationLanguageId,
	error,
	label,
	onChange,
	picklistItems,
	placeholder,
	required,
	selectedPicklistItemKey,
}: PicklistEntryBaseFieldProps) {
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
