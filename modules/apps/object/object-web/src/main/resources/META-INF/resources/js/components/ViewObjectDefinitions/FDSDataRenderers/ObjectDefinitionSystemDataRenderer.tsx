/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ObjectDefinitionSystemDataRenderer({
	itemData,
}: {
	itemData: ObjectDefinition;
}) {
	return itemData.system
		? Liferay.Language.get('yes')
		: Liferay.Language.get('no');
}
