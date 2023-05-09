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

import ClayEmptyState from '@clayui/empty-state';
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import Actions from '../../actions/Actions';
import ImageRenderer from '../../cell_renderers/ImageRenderer';

const List = ({items, schema}) => {
	const {selectedItemsKey} = useContext(FrontendDataSetContext);

	return items?.length ? (
		<ClayList>
			{items.map((item, index) => {
				return (
					<ListItem
						item={item}
						key={item[selectedItemsKey] || index}
						schema={schema}
					/>
				);
			})}
		</ClayList>
	) : (
		<ClayEmptyState
			description={Liferay.Language.get('sorry,-no-results-were-found')}
			imgSrc={`${themeDisplay.getPathThemeImages()}/states/search_state.gif`}
			title={Liferay.Language.get('no-results-found')}
		/>
	);
};

const ListItem = ({item, schema}) => {
	const {
		itemsActions,
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
		selectionType,
	} = useContext(FrontendDataSetContext);

	const [menuActive, setMenuActive] = useState(false);

	const {description, image, sticker, symbol, title} = schema;

	return (
		<ClayList.Item
			className={classNames({
				'menu-active': menuActive,
			})}
			flex
		>
			{selectable && (
				<ClayList.ItemField className="justify-content-center">
					{selectionType === 'single' ? (
						<ClayRadio
							checked={selectedItemsValue
								.map((element) => String(element))
								.includes(String(item[selectedItemsKey]))}
							onChange={() => selectItems(item[selectedItemsKey])}
						/>
					) : (
						<ClayCheckbox
							checked={selectedItemsValue
								.map((element) => String(element))
								.includes(String(item[selectedItemsKey]))}
							onChange={() => selectItems(item[selectedItemsKey])}
						/>
					)}
				</ClayList.ItemField>
			)}

			{image && item[image] ? (
				<ClayList.ItemField>
					<ImageRenderer
						sticker={sticker && item[sticker]}
						value={{src: item[image]}}
					/>
				</ClayList.ItemField>
			) : (
				symbol &&
				item[symbol] && (
					<ClayList.ItemField>
						<ClaySticker {...(sticker && item[sticker])}>
							{item[symbol] && <ClayIcon symbol={item[symbol]} />}
						</ClaySticker>
					</ClayList.ItemField>
				)
			)}

			<ClayList.ItemField className="justify-content-center" expand>
				{title && (
					<ClayList.ItemTitle>{item[title]}</ClayList.ItemTitle>
				)}

				{description && (
					<ClayList.ItemText>{item[description]}</ClayList.ItemText>
				)}
			</ClayList.ItemField>

			<ClayList.ItemField>
				{(itemsActions || item.actionDropdownItems) && (
					<Actions
						actions={itemsActions || item.actionDropdownItems}
						itemData={item}
						itemId={item[selectedItemsKey]}
						menuActive={menuActive}
						onMenuActiveChange={setMenuActive}
					/>
				)}
			</ClayList.ItemField>
		</ClayList.Item>
	);
};

List.propTypes = {
	context: PropTypes.any,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			id: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
				.isRequired,
		})
	),
	schema: PropTypes.shape({
		description: PropTypes.string,
		selectedItemValue: PropTypes.string,
		thumbnail: PropTypes.string,
		title: PropTypes.string,
	}),
};

List.defaultTypes = {
	activeItemValue: '',
};

export default List;
