/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FieldBusinessType} from '../utils/field';

export type ObjectField = {
	DBType: string;
	businessType: FieldBusinessType;
	externalReferenceCode: string;
	indexed: boolean;
	indexedAsKeyword?: boolean;
	indexedLanguageId?: Liferay.Language.Locale | '';
	label: Liferay.Language.LocalizedValue<string>;
	listTypeDefinitionId?: string;
	localized: boolean;
	name: string;
	objectFieldSettings?: {name: string; value: boolean | string | number}[];
	required: boolean;
	system?: boolean;
};

export type ObjectDefinition = {
	enableIndexSearch: boolean;
	enableLocalization: boolean;
	enableObjectEntryDraft: boolean;
	externalReferenceCode: string;
	id?: number;
	label: Liferay.Language.LocalizedValue<string>;
	name?: string;
	objectDefinitionSettings?: {
		name: 'acceptedGroupExternalReferenceCodes' | 'acceptAllGroups';
		value: string;
	}[];
	objectFields?: ObjectField[];
	objectFolderExternalReferenceCode?: string;
	pluralLabel: Liferay.Language.LocalizedValue<string>;
	scope: 'company' | 'depot' | 'site';
	status?: {
		label: string;
	};
};
