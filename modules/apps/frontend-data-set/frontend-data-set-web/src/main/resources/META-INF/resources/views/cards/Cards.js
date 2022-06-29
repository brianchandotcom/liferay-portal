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

import {ClayCardWithInfo} from '@clayui/card';
import ClayEmptyState from '@clayui/empty-state';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useRef} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import {handleAction, isLink} from '../../actions/Actions';

const Cards = ({items, schema}) => {
	const {selectedItemsKey, style} = useContext(FrontendDataSetContext);

	return items?.length ? (
		<div
			className={classNames(
				'cards-container mb-n4',
				style === 'default' && 'px-3 pt-4'
			)}
		>
			<div className="row">
				{items.map((item, index) => {
					return (
						<div
							className="col-md-3"
							key={item[selectedItemsKey] || index}
						>
							<Card item={item} schema={schema} />
						</div>
					);
				})}
			</div>
		</div>
	) : (
		<ClayEmptyState
			description={Liferay.Language.get('sorry,-no-results-were-found')}
			imgSrc={`${themeDisplay.getPathThemeImages()}/states/search_state.gif`}
			title={Liferay.Language.get('no-results-found')}
		/>
	);
};

const Card = ({item, schema}) => {
	const {
		executeAsyncItemAction,
		highlightItems,
		itemsActions,
		onActionDropdownItemClick,
		openModal,
		openSidePanel,
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
	} = useContext(FrontendDataSetContext);

	const actionsRef = useRef(itemsActions || item.actionDropdownItems);

	return (
		<ClayCardWithInfo
			actions={actionsRef.current?.map((action) => ({
				...action,
				href: isLink(action.target, null) ? action.href : null,
				onClick: (event) => {
					if (onActionDropdownItemClick) {
						onActionDropdownItemClick({
							action,
							event,
							itemData: item,
						});
					}

					handleAction(
						{
							event,
							itemId: item[selectedItemsKey],
							method: action.data?.method,
							url: action.href,
							...action,
						},
						{
							executeAsyncItemAction,
							highlightItems,
							openModal,
							openSidePanel,
						}
					);
				},
			}))}
			description={schema.description && item[schema.description]}
			href={(schema.href && item[schema.href]) || null}
			imgProps={schema.image && item[schema.image]}
			onSelectChange={
				selectable && (() => selectItems(item[selectedItemsKey]))
			}
			selected={
				selectable &&
				!!selectedItemsValue.find(
					(element) => element === item[selectedItemsKey]
				)
			}
			stickerProps={schema.sticker && item[schema.sticker]}
			symbol={schema.symbol && item[schema.symbol]}
			title={schema.title && item[schema.title]}
		/>
	);
};

Cards.propTypes = {
	items: PropTypes.array,
	schema: PropTypes.shape({
		description: PropTypes.string,
		href: PropTypes.string,
		imgProps: PropTypes.imgProps,
		labels: PropTypes.arrayOf(PropTypes.string),
		stickerProps: PropTypes.string,
		title: PropTypes.string,
	}).isRequired,
};

export default Cards;
