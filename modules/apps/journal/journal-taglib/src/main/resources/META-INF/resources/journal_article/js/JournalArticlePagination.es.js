/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
