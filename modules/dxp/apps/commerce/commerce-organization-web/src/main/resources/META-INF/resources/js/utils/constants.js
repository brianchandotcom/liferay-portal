/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export const COUNTER_KEYS_MAP = {
	account: 'numberOfAccounts',
	organization: 'numberOfOrganizations',
	user: 'numberOfUsers',
};

export const BRIEFS_KEYS_MAP = {
	account: 'accountBriefs',
	organization: 'organizationBriefs',
};

export const ACTION_KEYS = {
	account: {
		ADD_ENTITIES: 'update',
		DELETE: 'delete',
		MOVE: 'update',
		REMOVE: 'update',
	},
	organization: {
		ADD_ENTITIES: 'update',
		DELETE: 'delete',
		MOVE: 'update',
		REMOVE: 'update',
	},
	user: {
		ADD_ENTITIES: 'update',
		DELETE: 'delete',
		REMOVE: 'update',
	},
};

export const TRANSITIONS_DISABLED = process.env.NODE_ENV === 'test';
export const TRANSITION_TIME = 800;

export const ORGANIZATIONS_PROPERTY_NAME = 'childOrganizations';
export const ACCOUNTS_PROPERTY_NAME = 'organizationAccounts';
export const USERS_PROPERTY_NAME_IN_ORGANIZATION = 'userAccounts';
export const USERS_PROPERTY_NAME_IN_ACCOUNT = 'accountUserAccounts';

export const MAX_NAME_LENGTH = {
	account: 16,
	organization: 18,
	user: 14,
};

export const VIEWS = [
	{
		id: 'chart',
		label: Liferay.Language.get('chart'),
		symbol: 'diagram',
	},
	{
		id: 'list',
		label: Liferay.Language.get('list'),
		symbol: 'list',
	},
	{
		id: 'map',
		label: Liferay.Language.get('map'),
		symbol: 'geolocation',
	},
];

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

export const DRAGGING_THRESHOLD = 50;
export const ZOOM_EXTENT = [0.25, 1];
export const RECT_PADDING = 16;
export const ICON_RADIUS = 16;
export const COLUMN_SIZE = RECT_SIZES.organization[0];
export const COLUMN_GAP = 60;
export const MARGIN_LEFT = 40;
export const DX = 90;
export const DY = 400;

export const NODE_BUTTON_WIDTH = 28;
export const NODE_PADDING = 14;

export const SYMBOLS_MAP = {
	account: 'users',
	organization: 'organizations',
	user: 'user',
};

export const ORGANIZATIONS_ROLE_TYPE_ID = 3;
export const ACCOUNTS_ROLE_TYPE_ID = 6;
