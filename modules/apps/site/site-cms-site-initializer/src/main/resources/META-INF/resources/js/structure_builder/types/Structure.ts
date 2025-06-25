/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Field} from '../utils/field';
import {Uuid} from './Uuid';

type Status = 'new' | 'draft' | 'published';

type Spaces = 'all' | string[];

export type ReferencedStructure = {
	erc: string;
	name: string;
	parent: Uuid;
	type: 'referenced-structure';
	uuid: Uuid;
};

export type RepeatableGroup = {
	erc: string;
	fields: Map<Uuid, Field | ReferencedStructure | RepeatableGroup>;
	label: Liferay.Language.LocalizedValue<string>;
	name: string;
	parent: Uuid;
	type: 'repeatable-group';
	uuid: Uuid;
};

export type Structure = {
	erc: string;
	fields: Map<Uuid, Field | ReferencedStructure | RepeatableGroup>;
	id: number | null;
	label: Liferay.Language.LocalizedValue<string>;
	name: string;
	spaces: Spaces;
	status: Status;
	type?: 'L_CMS_CONTENT_STRUCTURES' | 'L_CMS_FILE_TYPES';
	uuid: Uuid;
};

export type Structures = Map<Structure['erc'], Structure>;
