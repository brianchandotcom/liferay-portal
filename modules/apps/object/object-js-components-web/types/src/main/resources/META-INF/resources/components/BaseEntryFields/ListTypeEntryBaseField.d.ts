/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

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
export declare function ListTypeEntryBaseField({
	creationLanguageId,
	error,
	label,
	onChange,
	picklistItems,
	placeholder,
	required,
	selectedPicklistItemKey,
}: ListTypeEntryBaseFieldProps): JSX.Element;
export {};
