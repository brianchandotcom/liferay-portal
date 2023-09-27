/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import React from 'react';

const DropDown = ({
	active,
	activeItem,
	id = '',
	items,
	match,
	onSelect,
	setActiveItem,
}) => {
	return (
		<ClayAutocomplete.DropDown active={active}>
			<ClayDropDown.ItemList id={`dropDownList${id}`}>
				{items.length ? (
					items.map((item, index) => (
						<ClayAutocomplete.Item
							className={index === activeItem ? 'active' : ''}
							key={index}
							match={match}
							onMouseDown={() => onSelect(item)}
							onMouseOver={() => setActiveItem(index)}
							value={item.name}
						/>
					))
				) : (
					<ClayDropDown.Item className="disabled">
						{Liferay.Language.get('no-results-were-found')}
					</ClayDropDown.Item>
				)}
			</ClayDropDown.ItemList>
		</ClayAutocomplete.DropDown>
	);
};

export {DropDown};
