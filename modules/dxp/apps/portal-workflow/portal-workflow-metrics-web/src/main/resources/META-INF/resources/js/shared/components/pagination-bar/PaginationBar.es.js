/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import pathToRegexp from 'path-to-regexp';
import React, {useCallback, useContext} from 'react';

import {AppContext} from '../../../components/AppContext.es';
import {useRouter} from '../../hooks/useRouter.es';

const PaginationBar = ({
	page,
	pageBuffer,
	pageSize,
	totalCount,
	setPage = () => {},
	setPageSize = () => {},
	withoutRouting,
}) => {
	const {deltaValues} = useContext(AppContext);
	const {
		history,
		location: {search},
		match: {params, path},
	} = useRouter();

	const deltas = deltaValues.map((label) => ({label}));
	const labels = {
		paginationResults: Liferay.Language.get('showing-x-to-x-of-x-entries'),
		perPageItems: Liferay.Language.get('x-entries'),
		selectPerPageItems: Liferay.Language.get('x-entries'),
	};

	const handleChangePageSize = useCallback(
		(newPageSize) => {
			if (!withoutRouting) {
				const pathname = pathToRegexp.compile(path)({
					...params,
					page: 1,
					pageSize: newPageSize,
				});

				history.push({pathname, search});
			}
			else {
				setPage(1);
				setPageSize(newPageSize);
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[params, path, search]
	);

	const handleChangePage = useCallback(
		(newPage) => {
			if (!withoutRouting) {
				const pathname = pathToRegexp.compile(path)({
					...params,
					page: newPage,
				});

				history.push({pathname, search});
			}
			else {
				setPage(newPage);
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[params, path, search]
	);

	if (totalCount <= deltaValues[0]) {
		return <></>;
	}

	return (
		<ClayPaginationBarWithBasicItems
			activeDelta={Number(pageSize)}
			activePage={Number(page)}
			className="mt-2"
			deltas={deltas}
			ellipsisBuffer={pageBuffer}
			labels={labels}
			onDeltaChange={handleChangePageSize}
			onPageChange={handleChangePage}
			totalItems={Number(totalCount)}
		/>
	);
};

export default PaginationBar;
