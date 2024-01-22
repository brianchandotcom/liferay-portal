/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {TGenericFieldProps} from './FieldsWrapper';
import BooleanField from './fields/BooleanField';
import DateField from './fields/DateField';
import InputField from './fields/InputField';
import MultiselectPicklist from './fields/MultiselectPicklist';
import Picklist from './fields/Picklist';

export const FIELDS_MAPPER: {
	[key: string]: {
		component: React.ComponentType<TGenericFieldProps>;
		props?: any;
	};
} = {
	Boolean: {component: BooleanField},
	Date: {component: DateField},
	DateTime: {component: DateField, props: {time: true}},
	Decimal: {component: InputField, props: {type: 'number'}},
	Integer: {
		component: InputField,
		props: {max: 2147483647, step: 1, type: 'number'},
	},
	LongInteger: {component: InputField, props: {step: 1, type: 'number'}},
	LongText: {
		component: InputField,
		props: {component: 'textarea', type: 'string'},
	},
	MultiselectPicklist: {component: MultiselectPicklist},
	Picklist: {component: Picklist},
	PrecisionDecimal: {component: InputField, props: {type: 'number'}},
	Text: {component: InputField, props: {type: 'string'}},
};

export default FIELDS_MAPPER;
