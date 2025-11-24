/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectRelationshipEdgeData, RightSidebarType} from '../types';

export function getRightSidebarWidth(
	rightSidebarType?: RightSidebarType,
	selectedObjectField?: ObjectFieldNodeRow,
	selectedObjectRelationship?: ObjectRelationshipEdgeData | null
) {
	const hasDefaultValue =
		(Liferay.FeatureFlags['LPD-46451'] &&
			(selectedObjectField?.businessType === 'Boolean' ||
				selectedObjectField?.businessType === 'LongText' ||
				selectedObjectField?.businessType === 'RichText' ||
				selectedObjectField?.businessType === 'Text')) ||
		selectedObjectField?.businessType === 'Picklist';

	if (rightSidebarType === 'objectDefinitionDetails') {
		return 500;
	}

	if (selectedObjectField) {
		if (selectedObjectField.businessType === 'Aggregation') {
			return 950;
		}

		if (hasDefaultValue) {
			return 500;
		}
	}

	if (selectedObjectRelationship) {
		return 360;
	}

	return 320;
}
