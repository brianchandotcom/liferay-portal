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

import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import {addParams} from 'frontend-js-web';
import React from 'react';

function JournalArticlePagination({
	activePage,
	namespace,
	paginationURL,
	totalPages,
}) {
	return (
		<>
			<ClayPaginationWithBasicItems
				active={activePage}
				disableEllipsis
				onActiveChange={(value) => {
					location.href = addParams(
						`${namespace}page=${value}`,
						paginationURL
					);
				}}
				totalPages={(Number.isFinite(totalPages) && totalPages) || 1}
			/>
		</>
	);
}

export default JournalArticlePagination;
