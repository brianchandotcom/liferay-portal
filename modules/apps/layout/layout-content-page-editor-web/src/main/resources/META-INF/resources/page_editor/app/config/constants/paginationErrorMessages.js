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

export const PAGINATION_ERROR_MESSAGES = {
	maximumItems: Liferay.Language.get(
		'the-current-number-of-items-in-this-collection-is-x'
	),
	maximumItemsPerPage: Liferay.Language.get(
		'you-can-only-display-a-maximum-of-x-items-per-page'
	),
	noItems: Liferay.Language.get('this-collection-has-no-items'),
};
