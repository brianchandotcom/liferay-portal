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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import React from 'react';

import '../../../css/AutocompleteDropDown.scss';

export default function AutocompleteDropDown({
	active,
	activeItem,
	emptyMessage = Liferay.Language.get('no-results-were-found'),
	emptyResultMessage = Liferay.Language.get('no-results-were-found'),
	id = '',
	items,
	match,
	onSelect,
	setActiveItem,
}) {
	return (
		<ClayAutocomplete.DropDown active={active}>
			<ClayDropDown.ItemList id={`dropDownList${id}`}>
				{items.length > 0 ? (
					items.map((item, index) => (
						<ClayAutocomplete.Item
							className={index === activeItem ? 'active' : ''}
							key={index}
							match={match}
							onMouseDown={() => onSelect(item)}
							onMouseOver={() => setActiveItem(index)}
							style={{cursor: 'pointer'}}
							value={item.name}
						/>
					))
				) : (
					<ClayDropDown.Item className="disabled">
						{!match ? emptyMessage : emptyResultMessage}
					</ClayDropDown.Item>
				)}
			</ClayDropDown.ItemList>
		</ClayAutocomplete.DropDown>
	);
}
