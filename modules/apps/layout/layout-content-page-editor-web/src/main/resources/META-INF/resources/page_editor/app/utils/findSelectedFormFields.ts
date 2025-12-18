/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormLayoutDataItem} from '../../types/layout_data/FormLayoutDataItem';
import {State} from '../reducers';
import {visitSelectedInputLayoutDataItems} from './visitSelectedInputLayoutDataItems';

export function findSelectedFormFields(
	state: State,
	formId: FormLayoutDataItem['itemId']
): string[] {
	const selectedFields: string[] = [];

	visitSelectedInputLayoutDataItems(
		state,
		formId,

		(_item, fieldId) => selectedFields.push(fieldId)
	);

	return selectedFields;
}
