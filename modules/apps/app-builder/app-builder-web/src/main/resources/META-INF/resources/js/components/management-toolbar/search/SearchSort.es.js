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

import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useContext, useState} from 'react';

import Button from '../../../components/button/Button.es';
import SearchContext from './SearchContext.es';

const {Group, Item, ItemList} = ClayDropDown;

export default ({columns, disabled}) => {
	const [, dispatch] = useContext(SearchContext);
	columns = columns.filter(column => column.sortable);

	let defaultColumn = columns.find(column =>
		Object.hasOwnProperty.call(column, 'asc')
	);

	if (!defaultColumn) {
		defaultColumn = {
			...columns[0],
			asc: true
		};
	}

	const [state, setState] = useState({
		active: false,
		asc: defaultColumn.asc,
		column: defaultColumn.key
	});

	const sort = (asc, column) => {
		setState({
			active: false,
			asc,
			column
		});

		dispatch({sort: `${column}:${asc ? 'asc' : 'desc'}`, type: 'SORT'});
	};

	const {active, asc, column} = state;

	return (
		<ul className="navbar-nav">
			<li className="dropdown nav-item">
				<ClayDropDown
					active={active}
					alignmentPosition={Align.BottomLeft}
					onActiveChange={newActive =>
						setState(prevState => ({
							...prevState,
							active: newActive
						}))
					}
					trigger={
						<Button
							className="nav-link"
							disabled={disabled}
							displayType="unstyled"
						>
							<span className="navbar-breakpoint-down-d-none">
								{Liferay.Language.get('filter-and-order')}

								<ClayIcon
									className="inline-item inline-item-after"
									symbol="caret-bottom"
								/>
							</span>
						</Button>
					}
				>
					<ItemList>
						<Group header={Liferay.Language.get('order-by')}>
							{columns.map((columnObject, index) => (
								<Item
									active={column === columnObject.key}
									href=""
									key={index}
									onClick={event => {
										event.preventDefault();

										sort(asc, columnObject.key);
									}}
								>
									{columnObject.value}
								</Item>
							))}
						</Group>
					</ItemList>
				</ClayDropDown>
			</li>

			<li className="nav-item">
				<Button
					className={classNames('nav-link', 'nav-link-monospaced', {
						'order-arrow-down-active': !asc,
						'order-arrow-up-active': asc
					})}
					disabled={disabled}
					displayType="unstyled"
					onClick={() => sort(!asc, column)}
					symbol="order-arrow"
					tooltip={Liferay.Language.get('reverse-sort-direction')}
				/>
			</li>
		</ul>
	);
};
