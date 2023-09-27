/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LocalizedValue} from '../types';

export function FieldBase(props: IFieldBase): JSX.Element;

interface IFieldBase {
	accessible?: boolean;
	children?: any;
	displayErrors?: any;
	errorMessage?: any;
	fieldName?: any;
	hideEditedFlag?: any;
	hideField?: any;
	id?: any;
	label?: any;
	localizedValue?: LocalizedValue<any>;
	name?: any;
	nestedFields?: any;
	onClick?: any;
	overMaximumRepetitionsLimit?: boolean;
	readOnly?: any;
	repeatable?: any;
	required?: any;
	showLabel?: boolean;
	style?: any;
	text?: any;
	tip?: any;
	tooltip?: any;
	type?: any;
	valid?: any;
	visible?: any;
}
