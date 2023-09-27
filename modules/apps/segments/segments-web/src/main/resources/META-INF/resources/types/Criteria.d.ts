/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {Conjunction, PropertyType} from "../js/utils/constants";

export interface Property {
	label: string;
	name: string;
	type: PropertyType;
}

export interface CriteriaItem {
	displayValue?: string;
	operatorName: string;
	propertyName: string;
	type?: PropertyType;
	value: string;
}

export interface Criteria {
	conjunctionName: Conjunction;
	groupId: string,
	items: Array<Criteria | CriteriaItem>;
}
