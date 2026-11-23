/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCreationMenuItems, {CreationMenuItem} from './getCreationMenuItems';
import {DesignLibraryResourceType} from './types';

type CreationMenuSubmenu = {
	items: CreationMenuItem[];
	label: string;
	type: 'contextual';
};

export default function getGroupedCreationMenuItems(
	resourceTypes: DesignLibraryResourceType[]
): Array<CreationMenuSubmenu | CreationMenuItem> {
	return resourceTypes.flatMap<CreationMenuSubmenu | CreationMenuItem>(
		(resourceType) => {
			const creationMenuItems = getCreationMenuItems([resourceType]);

			if (
				creationMenuItems.length > 1 &&
				resourceType.creationItemsGroupLabel
			) {
				return [
					{
						items: creationMenuItems,
						label: resourceType.creationItemsGroupLabel,
						type: 'contextual',
					},
				];
			}

			return creationMenuItems;
		}
	);
}
