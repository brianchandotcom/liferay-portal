/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IDisplayType} from './types';

export const DISPLAY_TYPES = [
	'info',
	'warning',
	'success',
	'secondary',
	'danger',
	'unstyled',
] as const;

export const KANBAN_COLUMN_ORDER = [
	'notStarted',
	'inProgress',
	'blocked',
	'done',
] as const;

export const mapStateKeyToLabel: {
	[key: string]: string;
} = {
	blocked: Liferay.Language.get('blocked'),
	done: Liferay.Language.get('done'),
	inProgress: Liferay.Language.get('in-progress'),
	notStarted: Liferay.Language.get('not-started'),
	overdue: Liferay.Language.get('overdue'),
};

export const mapStateKeyToDisplayType: {
	[key: string]: IDisplayType;
} = {
	blocked: 'danger',
	done: 'success',
	inProgress: 'info',
	notStarted: 'secondary',
	overdue: 'warning',
};

export const mapStateKeyToIcon: {
	[key: string]: {
		color: string;
		name: string;
	};
} = {
	blocked: {color: '#C31212', name: 'block'},
	done: {color: '#287D3C', name: 'check'},
	inProgress: {color: '#2E5AAC', name: 'analytics'},
	notStarted: {color: '', name: ''},
	overdue: {color: '', name: ''},
};
