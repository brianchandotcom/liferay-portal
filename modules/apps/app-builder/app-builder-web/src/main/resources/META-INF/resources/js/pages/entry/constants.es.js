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

const ENTRY_STATUS = {
	ANY: -1,
	APPROVED: 0,
	DENIED: 4,
	DRAFT: 2,
	EXPIRED: 3,
	IN_TRASH: 8,
	INACTIVE: 5,
	INCOMPLETE: 6,
	PENDING: 1,
	SCHEDULED: 7,
};

const ENTRY_STATUS_LABEL = {
	[ENTRY_STATUS.ANY]: {
		displayType: 'secondary',
		label: Liferay.Language.get('any'),
	},
	[ENTRY_STATUS.APPROVED]: {
		displayType: 'success',
		label: Liferay.Language.get('approved'),
	},
	[ENTRY_STATUS.DENIED]: {
		displayType: 'danger',
		label: Liferay.Language.get('denied'),
	},
	[ENTRY_STATUS.DRAFT]: {
		displayType: 'secondary',
		label: Liferay.Language.get('draft'),
	},
	[ENTRY_STATUS.EXPIRED]: {
		displayType: 'danger',
		label: Liferay.Language.get('expired'),
	},
	[ENTRY_STATUS.IN_TRASH]: {
		displayType: 'info',
		label: Liferay.Language.get('in-trash'),
	},
	[ENTRY_STATUS.INACTIVE]: {
		displayType: 'secondary',
		label: Liferay.Language.get('inactive'),
	},
	[ENTRY_STATUS.INCOMPLETE]: {
		displayType: 'warning',
		label: Liferay.Language.get('incomplete'),
	},
	[ENTRY_STATUS.PENDING]: {
		displayType: 'info',
		label: Liferay.Language.get('pending'),
	},
	[ENTRY_STATUS.SCHEDULED]: {
		displayType: 'info',
		label: Liferay.Language.get('scheduled'),
	},
};

export {ENTRY_STATUS, ENTRY_STATUS_LABEL};
