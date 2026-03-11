/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizedValue} from './getLocalizedValue';
import {DisplayType, ILabelSchema} from './types';

const getLabels = (
	item: any,
	labels: ILabelSchema[] | undefined
): Array<{
	displayType: DisplayType;
	value: string;
}> => {
	if (!labels) {
		return [];
	}

	return labels.flatMap((label: ILabelSchema) => {
		const {displayTypeKey, displayTypeValues} = label;
		let {displayType} = label;

		if (!displayType && displayTypeValues && displayTypeKey) {
			const keyValue = getLocalizedValue(item, displayTypeKey)?.value;

			displayType = displayTypeValues[keyValue!];
		}

		const value = getLocalizedValue(item, label.value)?.value;

		if (!value) {
			return [];
		}

		return [
			{
				displayType: displayType || DisplayType.UNSTYLED,
				value,
			},
		];
	});
};

export default getLabels;
