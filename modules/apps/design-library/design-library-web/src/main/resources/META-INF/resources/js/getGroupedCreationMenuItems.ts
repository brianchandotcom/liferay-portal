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

type CreationMenuSectionItem = CreationMenuItem | CreationMenuSubmenu;

export type CreationMenuSection = {
	items: CreationMenuSectionItem[];
	label: string;
	separator: boolean;
	type: 'group';
};

export default function getGroupedCreationMenuItems(
	resourceTypes: DesignLibraryResourceType[]
): CreationMenuSection[] {
	const creationMenuSections = new Map<string, CreationMenuSection>();

	for (const resourceType of resourceTypes) {
		const creationMenuItems = getCreationMenuItems([resourceType]);

		if (!creationMenuItems.length) {
			continue;
		}

		const items: CreationMenuSectionItem[] =
			creationMenuItems.length > 1 &&
			resourceType.creationItemsSubmenuLabel
				? [
						{
							items: creationMenuItems,
							label: resourceType.creationItemsSubmenuLabel,
							type: 'contextual',
						},
					]
				: creationMenuItems;

		const creationItemsSectionKey =
			resourceType.creationItemsSectionKey ?? resourceType.key;

		const creationMenuSection = creationMenuSections.get(
			creationItemsSectionKey
		);

		if (creationMenuSection) {
			creationMenuSection.items.push(...items);
		}
		else {
			creationMenuSections.set(creationItemsSectionKey, {
				items,
				label: '',
				separator: true,
				type: 'group',
			});
		}
	}

	return Array.from(creationMenuSections.values());
}
