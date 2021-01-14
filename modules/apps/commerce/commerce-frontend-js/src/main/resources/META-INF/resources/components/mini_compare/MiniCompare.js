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
import {ClayIconSpriteContext} from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import CookieUtils from '../../utilities/cookies';
import {
	ADD_ITEM_TO_COMPARE,
	COMPARE_IS_AVAILABLE,
	COMPARE_IS_UNAVAILABLE,
	ITEM_REMOVED_FROM_COMPARE,
	REMOVE_ITEM_FROM_COMPARE,
} from '../../utilities/eventsDefinitions';

const cookieUtils = new CookieUtils('COMMERCE_COMPARE_cpDefinitionIds_');

function toggleStatus(commerceChannelGroupId, id, toggle) {
	const value = cookieUtils.getValue(commerceChannelGroupId);

	const cpDefinitionIds = value ? value.split(':') : [];

	if (toggle) {
		if (!cpDefinitionIds.includes(id)) {
			cpDefinitionIds.push(id);
		}
	}
	else {
		const index = cpDefinitionIds.indexOf(id);

		if (index !== -1) {
			cpDefinitionIds.splice(index, 1);
		}
	}

	cookieUtils.setValue(commerceChannelGroupId, cpDefinitionIds.join(':'));
}

function Item(props) {
	return (
		<div className={classnames('mini-compare-item', props.id && 'active')}>
			<ClaySticker className="mini-compare-thumbnail-container" size="lg">
				<div
					className="mini-compare-thumbnail"
					style={{backgroundImage: `url('${props.thumbnail}')`}}
				/>
			</ClaySticker>
			<ClayButtonWithIcon
				className="mini-compare-delete"
				displayType="unstyled"
				onClick={props.onDelete}
				small
				symbol="times"
			/>
		</div>
	);
}

function MiniCompare(props) {
	const [items, updateItems] = useState(props.items);

	cookieUtils.setValue(
		props.commerceChannelGroupId,
		items.map((item) => item.id).join(':')
	);

	useEffect(() => {
		function toggleItem({id, thumbnail}) {
			const newItem = {
				id,
				thumbnail,
			};

			return updateItems((items) => {
				const included = items.find((el) => el.id === id);

				toggleStatus(props.commerceChannelGroupId, id, !included);

				return included
					? items.filter((i) => i.id !== id)
					: items.concat(newItem);
			});
		}

		Liferay.on(ADD_ITEM_TO_COMPARE, toggleItem);
		Liferay.on(REMOVE_ITEM_FROM_COMPARE, toggleItem);

		return () => {
			Liferay.detach(ADD_ITEM_TO_COMPARE, toggleItem);
			Liferay.detach(REMOVE_ITEM_FROM_COMPARE, toggleItem);
		};
	}, [
		props.commerceChannelGroupId,
		props.itemsLimit,
		props.portletNamespace,
	]);

	useEffect(() => {
		if (items.length < props.itemsLimit) {
			Liferay.fire(COMPARE_IS_AVAILABLE);
		}
		else {
			Liferay.fire(COMPARE_IS_UNAVAILABLE);
		}
	}, [items, props.itemsLimit]);

	return (
		<ClayIconSpriteContext.Provider value={props.spritemap}>
			<div
				className={classnames('mini-compare', items.length && 'active')}
			>
				{Array(props.itemsLimit)
					.fill(null)
					.map((_el, i) => {
						const currentItem = items[i] || {};

						return (
							<Item
								{...currentItem}
								key={i}
								onDelete={(e) => {
									e.preventDefault();
									updateItems(
										items.filter(
											(v) => v.id !== currentItem.id
										)
									);
									toggleStatus(
										props.commerceChannelGroupId,
										currentItem.id,
										false
									);
									Liferay.fire(
										ITEM_REMOVED_FROM_COMPARE,
										currentItem
									);
								}}
							/>
						);
					})}
				<a className="btn btn-primary" href={props.compareProductsURL}>
					{Liferay.Language.get('compare')}
				</a>
			</div>
		</ClayIconSpriteContext.Provider>
	);
}

MiniCompare.propTypes = {
	commerceChannelGroupId: PropTypes.number,
	compareProductsURL: PropTypes.string.isRequired,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			id: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
				.isRequired,
			thumbnail: PropTypes.string,
		})
	),
	itemsLimit: PropTypes.number,
	portletNamespace: PropTypes.string.isRequired,
	spritemap: PropTypes.string,
};

MiniCompare.defaultProps = {
	items: [],
	itemsLimit: 5,
};

export default MiniCompare;
