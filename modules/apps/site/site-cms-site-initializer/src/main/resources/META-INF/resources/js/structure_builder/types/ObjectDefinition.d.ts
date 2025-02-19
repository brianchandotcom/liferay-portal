/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FieldBusinessType} from '../utils/fieldType';

export type ObjectField = {
	businessType: FieldBusinessType;
	externalReferenceCode?: number;
	label: {
		en_US: string;
	};
	localized: boolean;
	name: string;
	required: boolean;
};

export type ObjectDefinition = {
	id?: number;
	label: {
		en_US: string;
	};
	name?: string;
	objectFields?: ObjectField[];
	pluralLabel: {
		en_US: string;
	};
	scope: 'company';
};
