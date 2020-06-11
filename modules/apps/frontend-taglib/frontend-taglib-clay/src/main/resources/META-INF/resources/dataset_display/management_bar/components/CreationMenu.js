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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import DatasetDisplayContext from '../../DatasetDisplayContext';
import {triggerAction} from '../../utilities/actionItems/index';

function CreationMenu({items}) {
	const [active, setActive] = useState(false);
	const datasetContext = useContext(DatasetDisplayContext);

	return (
		items?.length && (
			<ul className="navbar-nav">
				<li className="nav-item">
					{items.length > 1 ? (
						<ClayDropDown
							active={active}
							onActiveChange={setActive}
							trigger={<ClayButtonWithIcon symbol="plus" />}
						>
							<ClayDropDown.ItemList>
								{items.map((item, i) => (
									<ClayDropDown.Item
										key={i}
										onClick={(event) => {
											event.preventDefault();
											setActive(false);
											triggerAction(item, datasetContext);
										}}
									>
										{item.label}
									</ClayDropDown.Item>
								))}
							</ClayDropDown.ItemList>
						</ClayDropDown>
					) : (
						<ClayButtonWithIcon
							onClick={() =>
								triggerAction(items[0], datasetContext)
							}
							symbol="plus"
						/>
					)}
				</li>
			</ul>
		)
	);
}

CreationMenu.propTypes = {
	items: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
			target: PropTypes.oneOf(['modal', 'sidePanel', 'event', 'link']),
		})
	).isRequired,
};

export default CreationMenu;
