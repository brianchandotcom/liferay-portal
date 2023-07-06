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

import {ADD_FRAGMENT_ENTRY_LINKS} from './types';

import type {LayoutData} from '../../types/LayoutData';
import type {EditableType} from '../config/constants/editableTypes';
import type {FragmentEntryType} from '../config/constants/fragmentEntryTypes';
import type {FragmentEntryLinkComment} from './addFragmentEntryLinkComment';

export interface FragmentEntryLink<
	EditableId extends string = string,
	ConfigurationFieldId extends string = string
> {
	comments: FragmentEntryLinkComment[];
	configuration: Record<string, unknown>;
	content: string;
	cssClass: string;
	defaultConfigurationValues: {
		[key in ConfigurationFieldId]: string;
	};
	editableTypes: {
		[key in EditableId]: EditableType;
	};
	editableValues: {
		'com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor': {
			[key in EditableId]: unknown;
		};
		'com.liferay.fragment.entry.processor.freemarker.FreeMarkerFragmentEntryProcessor': {
			[key in ConfigurationFieldId]: unknown;
		};
	};
	fragmentEntryId: string;
	fragmentEntryKey: string;
	fragmentEntryLinkId: string;
	fragmentEntryType: FragmentEntryType;
	groupId: string;
	icon: string;
	name: string;
	segmentsExperienceId: string;
}

export default function addFragmentEntryLinks({
	addedItemId,
	fragmentEntryLinks,
	layoutData,
}: {
	addedItemId: string;
	fragmentEntryLinks: FragmentEntryLink[];
	layoutData: LayoutData;
}) {
	return {
		fragmentEntryLinks,
		itemId: addedItemId,
		layoutData,
		type: ADD_FRAGMENT_ENTRY_LINKS,
	} as const;
}
