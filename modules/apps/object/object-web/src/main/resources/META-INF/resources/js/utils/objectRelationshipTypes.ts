/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export const objectRelationshipTypes = [
	{
		description: Liferay.Language.get(
			"one-object's-entry-interacts-only-with-one-other-object's-entry"
		),
		label: Liferay.Language.get('one-to-one'),
		value: 'oneToOne',
	},
	{
		description: Liferay.Language.get(
			"one-object's-entry-interacts-with-many-others-object's-entries"
		),
		label: Liferay.Language.get('one-to-many'),
		value: 'oneToMany',
	},
	{
		description: Liferay.Language.get(
			"multiple-object's-entries-can-interact-with-many-others-object's-entries"
		),
		label: Liferay.Language.get('many-to-many'),
		value: 'manyToMany',
	},
];
