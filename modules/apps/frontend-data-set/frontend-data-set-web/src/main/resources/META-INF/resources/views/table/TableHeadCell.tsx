/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useContext, useEffect, useState} from 'react';

import {TSort} from '../..';
import ViewsContext from '../ViewsContext';

// @ts-ignore

import {VIEWS_ACTION_TYPES} from '../viewsReducer';
import Cell from './dnd_table/Cell';

const TableHeadCell = ({
	contentRenderer,
	fieldName,
	hideColumnLabel,
	label,
	sortable,
	sortingKey: sortingKeyProp,
}: {
	contentRenderer?: string;
	fieldName: string | Array<string>;
	hideColumnLabel?: boolean;
	label: string;
	sortable?: boolean;
	sortingKey?: string;
}) => {
	const [{sorts}, viewsDispatch] = useContext(ViewsContext);

	const [sortingKey, setSortingKey] = useState<string | null>(null);
	const [sortingMatch, setSortingMatch] = useState<any>(null);

	useEffect(() => {
		const newSortingKey: string =
			sortingKeyProp ||
			(Array.isArray(fieldName) ? fieldName[0] : fieldName);

		setSortingKey(newSortingKey);
		setSortingMatch(sorts.find((element) => element.key === newSortingKey));
	}, [fieldName, sorts, sortingKeyProp]);

	function handleSortingCellClick(event: any) {
		event.preventDefault();

		let updatedSortedElements: TSort[] = [];

		updatedSortedElements = sorts.map((element) =>
			element.key === sortingKey
				? {
						...element,
						active: true,
						direction: element.direction === 'asc' ? 'desc' : 'asc',
					}
				: {
						...element,
						active: false,
					}
		);

		if (!sortingMatch && sortingKey) {
			updatedSortedElements.push({
				active: true,
				direction: 'asc',
				key: sortingKey,
			});
		}

		viewsDispatch({
			type: VIEWS_ACTION_TYPES.UPDATE_SORTING,
			value: updatedSortedElements,
		});
	}

	return (
		<Cell
			className={classNames({
				[`content-renderer-${contentRenderer}`]: contentRenderer,
			})}
			columnName={String(fieldName)}
			heading
			resizable
		>
			{sortable ? (
				<ClayButton
					className="btn-sorting inline-item text-nowrap text-truncate-inline"
					displayType="unstyled"
					onClick={handleSortingCellClick}
					size="sm"
				>
					{!hideColumnLabel && label}

					<span className="inline-item inline-item-after sorting-icons-wrapper">
						<ClayIcon
							className={classNames('sorting-icon', {
								active:
									sortingMatch?.direction === 'asc' &&
									sortingMatch?.active,
							})}
							symbol="order-arrow-up"
						/>

						<ClayIcon
							className={classNames('sorting-icon', {
								active:
									sortingMatch?.direction === 'desc' &&
									sortingMatch?.active,
							})}
							symbol="order-arrow-down"
						/>
					</span>
				</ClayButton>
			) : (
				!hideColumnLabel && label
			)}
		</Cell>
	);
};
export default TableHeadCell;
