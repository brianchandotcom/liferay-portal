/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
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

		if (Liferay.FeatureFlags['LPD-19465']) {
			updatedSortedElements = sorts.map((element) =>
				element.key === sortingKey
					? {
							...element,
							active: true,
							direction:
								element.direction === 'asc' ? 'desc' : 'asc',
					  }
					: {
							...element,
							active: false,
					  }
			);
		}
		else {
			updatedSortedElements = sortingMatch
				? sorts.map((element) =>
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
		}

		viewsDispatch({
			type: VIEWS_ACTION_TYPES.UPDATE_SORTING,
			value: updatedSortedElements,
		});
	}

	const content = Liferay.FeatureFlags['LPS-193005'] ? (
		<ClayLink
			className="inline-item text-truncate-inline"
			href="#"
			onClick={handleSortingCellClick}
		>
			{!hideColumnLabel && <span className="text-truncate">{label}</span>}

			{sortingMatch && (
				<span className="inline-item inline-item-after">
					<ClayIcon
						symbol={
							Liferay.FeatureFlags['LPD-19465']
								? sortingMatch?.active &&
								  sortingMatch?.direction === 'asc'
									? 'order-arrow-up'
									: 'order-arrow-down'
								: sortingMatch?.direction === 'asc'
								? 'order-arrow-up'
								: 'order-arrow-down'
						}
					/>
				</span>
			)}
		</ClayLink>
	) : (
		<ClayButton
			className="btn-sorting inline-item text-nowrap text-truncate-inline"
			displayType="unstyled"
			onClick={handleSortingCellClick}
			size="sm"
		>
			{!hideColumnLabel && label}

			{(Liferay.FeatureFlags['LPD-19465'] ? sortingMatch : true) && (
				<span className="inline-item inline-item-after sorting-icons-wrapper">
					<ClayIcon
						className={classNames('sorting-icon', {
							active: Liferay.FeatureFlags['LPD-19465']
								? sortingMatch?.direction === 'asc' &&
								  sortingMatch?.active
								: sortingMatch?.direction === 'asc',
						})}
						symbol="order-arrow-up"
					/>

					<ClayIcon
						className={classNames('sorting-icon', {
							active: Liferay.FeatureFlags['LPD-19465']
								? sortingMatch?.direction === 'desc' &&
								  sortingMatch?.active
								: sortingMatch?.direction === 'desc',
						})}
						symbol="order-arrow-down"
					/>
				</span>
			)}
		</ClayButton>
	);

	return (
		<Cell
			className={classNames({
				[`content-renderer-${contentRenderer}`]: contentRenderer,
			})}
			columnName={String(fieldName)}
			heading
			resizable
		>
			{sortable ? content : !hideColumnLabel && label}
		</Cell>
	);
};
export default TableHeadCell;
