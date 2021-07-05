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

import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useMemo, useState} from 'react';

import {AppContext} from './AppContext';
import {ButtonList} from './ButtonList';
import {CollapsableButtonList} from './CollapsableButtonList';

const SEARCH_INPUT_ID = 'ddm_template_editor_Sidebar-SearchInputId';

export const ElementsSidebarPanel = ({className}) => {
	const [filteredItems, setFilteredItems] = useState(null);

	const {
		inputChannel,
		templateVariableGroups: initialTemplateVariableGroups,
	} = useContext(AppContext);

	const templateVariableGroups = useMemo(
		() =>
			initialTemplateVariableGroups.map((group) => ({
				...group,
				items: group.items.map((item) =>
					item.repeatable ? {...item, label: `${item.label}*`} : item
				),
			})),
		[initialTemplateVariableGroups]
	);

	const onButtonClick = (item) => inputChannel.sendData(item.content);

	const handleSearchInputChange = (event) => {
		const slugify = (str) => str.toLowerCase().replace(/\s/g, '');
		const query = slugify(event.target.value);

		if (query) {
			setFilteredItems(
				templateVariableGroups
					.map(({items}) =>
						items.filter((item) =>
							slugify(item.label).includes(query)
						)
					)
					.reduce((a, b) => a.concat(b), [])
			);
		}
		else {
			setFilteredItems(null);
		}
	};

	return (
		<div className={classNames(className, 'px-3')}>
			<h1 className="ddm_template_editor__App-sidebar-title my-3">
				{Liferay.Language.get('elements')}
			</h1>
			<ClayForm.Group small>
				<label className="sr-only" htmlFor={SEARCH_INPUT_ID}>
					{Liferay.Language.get('search')}
				</label>

				<ClayInput
					id={SEARCH_INPUT_ID}
					onChange={handleSearchInputChange}
					placeholder={`${Liferay.Language.get('search')}...`}
					type="search"
				/>
			</ClayForm.Group>

			{filteredItems ? (
				<ButtonList
					items={filteredItems}
					onButtonClick={onButtonClick}
				/>
			) : (
				templateVariableGroups.map(({items, label}) => (
					<CollapsableButtonList
						items={items}
						key={label}
						label={label}
						onButtonClick={onButtonClick}
					/>
				))
			)}
		</div>
	);
};

ElementsSidebarPanel.propTypes = {
	templateVariableGroups: PropTypes.arrayOf(
		PropTypes.shape({
			items: PropTypes.array.isRequired,
			label: PropTypes.string.isRequired,
		})
	),
};
