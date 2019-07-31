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
import React, {useState} from 'react';

const {Group, Item, ItemList} = ClayDropDown;

export default function SearchSort({columns, onSort}) {
	let defaultColumn = columns.find(column => column.hasOwnProperty('asc'));

	if (defaultColumn === undefined) {
		const key = Object.keys(columns[0])[0];

		defaultColumn = {
			[key]: key,
			asc: true
		};
	}

	const [state, setState] = useState({
		active: false,
		column: Object.keys(defaultColumn)[0],
		asc: defaultColumn.asc
	});

	const sort = (asc, column) => {
		setState({
			active: false,
			asc,
			column
		});

		onSort(`${column}:${asc ? 'asc' : 'desc'}`);
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
						<button
							aria-expanded="false"
							aria-haspopup="true"
							className="btn nav-link btn-unstyled"
							type="button"
						>
							<span className="navbar-breakpoint-down-d-none">
								{Liferay.Language.get('filter-and-order')}

								<ClayIcon
									className="inline-item inline-item-after"
									spritemap={`${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`}
									symbol="caret-bottom"
								/>
							</span>
						</button>
					}
				>
					<ItemList>
						<Group header={Liferay.Language.get('order-by')}>
							{columns.map((columnObject, index) => (
								<Item
									active={
										column === Object.keys(columnObject)[0]
									}
									href="javascript:;"
									key={index}
									onClick={() =>
										sort(asc, Object.keys(columnObject)[0])
									}
								>
									{Object.values(columnObject)[0]}
								</Item>
							))}
						</Group>
					</ItemList>
				</ClayDropDown>
			</li>

			<li className="nav-item">
				<a
					className={`nav-link nav-link-monospaced ${
						asc
							? 'order-arrow-up-active'
							: 'order-arrow-down-active'
					}`}
					href="javascript:;"
					onClick={() => sort(!asc, column)}
					title={Liferay.Language.get('reverse-sort-direction')}
				>
					<ClayIcon
						spritemap={`${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`}
						symbol="order-arrow"
					/>
				</a>
			</li>
		</ul>
	);
}
