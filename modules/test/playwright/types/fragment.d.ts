/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type FragmentConfiguration = {
	fieldSets: FieldSet[];
};

type FieldSet = {
	fields: Field[];
};

type Field = {
	label: string;
	name: string;
	type: 'itemSelector';
	typeOptions: {
		enableSelectTemplate: boolean;
		itemSubtype: string;
		itemType: string;
	};
};

type FragmentCollection = {
	fragmentCollectionId: string;
	groupId: string;
};

type FragmentEntry = {
	fragmentEntryId: string;
	groupId: string;
};
