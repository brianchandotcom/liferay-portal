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

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import pathToRegexp from 'path-to-regexp';
import React, {useCallback, useContext, useMemo} from 'react';

import {AppContext} from '../../../components/AppContext.es';
import {useRouter} from '../../hooks/useRouter.es';

const PaginationBar = ({
	routing = true,
	page,
	pageBuffer,
	pageSize,
	totalCount,
	setPage = () => {},
	setPageSize = () => {}
}) => {
	const {deltaValues} = useContext(AppContext);
	const {
		history,
		location: {search},
		match: {params, path}
	} = useRouter();

	const deltas = useMemo(() => deltaValues.map(label => ({label})), [
		deltaValues
	]);

	const labels = useMemo(
		() => ({
			paginationResults: Liferay.Language.get(
				'showing-x-to-x-of-x-entries'
			),
			perPageItems: Liferay.Language.get('x-entries'),
			selectPerPageItems: Liferay.Language.get('x-entries')
		}),
		[]
	);

	const handleChangePageSize = useCallback(
		newPageSize => {
			if (routing) {
				const pathname = pathToRegexp.compile(path)({
					...params,
					page: 1,
					pageSize: newPageSize
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
		newPage => {
			if (routing) {
				const pathname = pathToRegexp.compile(path)({
					...params,
					page: newPage
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

	const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`;

	return (
		<ClayPaginationBarWithBasicItems
			activeDelta={Number(pageSize)}
			activePage={Number(page)}
			deltas={deltas}
			ellipsisBuffer={pageBuffer}
			labels={labels}
			onDeltaChange={handleChangePageSize}
			onPageChange={handleChangePage}
			spritemap={spritemap}
			totalItems={Number(totalCount)}
		/>
	);
};

export default PaginationBar;
