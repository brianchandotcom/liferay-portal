/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import ViewsContext from '../ViewsContext';
import {VIEWS_ACTION_TYPES} from '../viewsReducer';
import Cell from './dnd_table/Cell';

function TableHeadCell({
	contentRenderer,
	fieldName,
	hideColumnLabel,
	label,
	sortable,
	sortingKey: sortingKeyProp,
}) {
	const [{sorting}, viewsDispatch] = useContext(ViewsContext);

	const [sortingKey, setSortingKey] = useState(null);
	const [sortingMatch, setSortingMatch] = useState(null);

	useEffect(() => {
		const newSortingKey =
			sortingKeyProp ||
			(Array.isArray(fieldName) ? fieldName[0] : fieldName);

		setSortingKey(newSortingKey);
		setSortingMatch(
			sorting.find((element) => element.key === newSortingKey)
		);
	}, [fieldName, sorting, sortingKeyProp]);

	function handleSortingCellClick(event) {
		event.preventDefault();

		const updatedSortedElements = sortingMatch
			? sorting.map((element) =>
					element.key === sortingKey
						? {
								...element,
								direction:
									element.direction === 'asc'
										? 'desc'
										: 'asc',
						  }
						: element
			  )
			: [
					{
						direction: 'asc',
						fieldName,
						key: sortingKey,
					},
			  ];

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
				<ClayLink
					className="inline-item text-truncate-inline"
					href="#"
					onClick={handleSortingCellClick}
				>
					{!hideColumnLabel && label && (
						<span className="text-truncate">{label}</span>
					)}

					<span className="inline-item inline-item-after sorting-icons-wrapper">
						<ClayIcon
							className={classNames(
								'sorting-icon',
								sortingMatch?.direction === 'asc' && 'active'
							)}
							draggable
							symbol="order-arrow-up"
						/>

						<ClayIcon
							className={classNames(
								'sorting-icon',
								sortingMatch?.direction === 'desc' && 'active'
							)}
							draggable
							symbol="order-arrow-down"
						/>
					</span>
				</ClayLink>
			) : (
				!hideColumnLabel && label
			)}
		</Cell>
	);
}

TableHeadCell.proptypes = {
	contentRenderer: PropTypes.string,
	fieldName: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.arrayOf(PropTypes.string),
	]),
	hideColumnLabel: PropTypes.bool,
	label: PropTypes.string,
	sortable: PropTypes.bool,
	sortingKey: PropTypes.string,
};

export default TableHeadCell;
