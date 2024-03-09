/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const ACCOUNTS_PROPERTY_NAME = 'organizationAccounts';
export const ACCOUNTS_ROLE_TYPE_ID = 6;
export const ACTION_KEYS = {
	account: {
		ADD_ENTITIES: 'update',
		DELETE: 'delete',
		MOVE: 'update',
		REMOVE: 'update',
		UPDATE: 'update',
		VIEW: 'get',
	},
	organization: {
		ADD_ENTITIES: 'update',
		DELETE: 'delete',
		MOVE: 'update',
		REMOVE: 'update',
		UPDATE: 'update',
		VIEW: 'get',
	},
	user: {
		ADD_ENTITIES: 'update',
		DELETE: 'delete-user-account',
		REMOVE: 'patch-user-account',
		UPDATE: 'patch-user-account',
		VIEW: 'get-user-account',
	},
};
export const BRIEFS_KEYS_MAP = {
	account: 'accountBriefs',
	organization: 'organizationBriefs',
};
export const COLUMN_GAP = 60;
export const COUNTER_KEYS_MAP = {
	account: 'numberOfAccounts',
	organization: 'numberOfOrganizations',
	user: 'numberOfUsers',
};
export const DEFAULT_IMAGE_PATHS_MAP = {
	account: '/organization_logo?img_id=0',
	organization: '/user_portrait?img_id=0',
	user: '/user_portrait?img_id=0',
};
export const DEFAULT_PAGE_SIZE = 50;
export const DEFAULT_USER_ACCOUNT_FULL_NAME_DEFINITION_FIELDS = [
	{
		key: 'first-name',
		required: true,
	},
	{
		key: 'last-name',
		required: true,
	},
];
export const DRAGGING_THRESHOLD = 50;
export const DX = 90;
export const DY = 400;
export const ICON_RADIUS = 16;
export const INFO_PANEL_OPEN_EVENT = 'info-item-open-event';
export const INFO_PANEL_MODE_MAP = {
	click: 'click',
	edit: 'edit',
	view: 'view',
};
export const MARGIN_LEFT = 40;
export const MAX_NAME_LENGTH = {
	account: 16,
	organization: 18,
	user: 14,
};
export const MODEL_TYPE_MAP = {
	account: 'account',
	organization: 'organization',
	user: 'user',
};
export const MAX_DISPLAYED_ORGANIZATIONS = 10;
export const NODE_BUTTON_WIDTH = 28;
export const NODE_PADDING = 14;
export const ORGANIZATIONS_PROPERTY_NAME = 'childOrganizations';
export const ORGANIZATIONS_ROLE_TYPE_ID = 3;
export const RECT_PADDING = 16;
export const RECT_SIZES = {
	account: {
		height: 64,
		width: 260,
	},
	fakeRoot: {
		height: 56,
		width: 240,
	},
	organization: {
		height: 72,
		width: 280,
	},
	user: {
		height: 56,
		width: 240,
	},
};
export const COLUMN_SIZE = RECT_SIZES.organization[0];
export const SYMBOLS_MAP = {
	account: 'briefcase',
	organization: 'organizations',
	user: 'user',
};
export const TRANSITION_TIME = 800;
export const TRANSITIONS_DISABLED = process.env.NODE_ENV === 'test';
export const USERS_PROPERTY_NAME_IN_ACCOUNT = 'accountUserAccounts';
export const USERS_PROPERTY_NAME_IN_ORGANIZATION = 'userAccounts';
export const VIEWS = [
	{
		id: 'chart',
		label: Liferay.Language.get('chart[noun]'),
		symbol: 'diagram',
	},
	{
		id: 'list',
		label: Liferay.Language.get('list[noun]'),
		symbol: 'list',
	},
	{
		id: 'map',
		label: Liferay.Language.get('map'),
		symbol: 'geolocation',
	},
];
export const ZOOM_EXTENT = [0.25, 1];
