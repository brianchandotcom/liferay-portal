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

import {atom} from 'jotai';
import i18n from '~/i18n';
import {Action} from '~/types';

export type HeaderActions = {
	actions: Action[];
	item?: any;
	mutate?: any;
};

type DropdownItem = {
	divider?: boolean;
	icon?: string;
	label: string;
	onClick?: () => void;
	path?: string;
};

type DropdownSection = {
	items: DropdownItem[];
	title?: string;
};

export type Dropdown = DropdownSection[];

export type HeaderTabs = {
	active: boolean;
	path: string;
	title: string;
};

export type HeaderTitle = {
	category?: string;
	path?: string;
	title: string;
};

export const headerAtom = {
	dropdown: atom<Dropdown>([]),
	headerActions: atom<HeaderActions>({actions: []}),
	heading: atom<HeaderTitle[]>([
		{
			category: i18n.translate('project'),
			title: i18n.translate('project-directory'),
		},
	]),
	symbol: atom<string>(''),
	tabs: atom<HeaderTabs[]>([]),
};
