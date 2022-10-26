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

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {fetch, navigate, openSelectionModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

function mapItemsOnClick(items) {
	return items.map((item) => {
		const {items: nestedItems, jsOnClickConfig, ...otherKeys} = item;

		const newVal = {...otherKeys};

		if (nestedItems) {
			newVal.items = mapItemsOnClick(nestedItems);
		}

		if (jsOnClickConfig) {
			newVal.onClick = () => {
				const {selectEventName, title, url} = jsOnClickConfig;

				openSelectionModal({
					id: selectEventName,
					onSelect(selectedItem) {
						navigate(selectedItem.url);
					},
					selectEventName,
					title,
					url,
				});
			};
		}

		return newVal;
	});
}

const defaultItems = [
	{
		'aria-label': Liferay.Language.get('loading'),
		'aria-valuemax': 100,
		'aria-valuemin': 0,
		'label': <ClayLoadingIndicator />,
		'roleItem': 'progressbar',
	},
];

function PersonalMenu({
	color,
	isImpersonated,
	itemsURL,
	label,
	size,
	userPortraitURL,
}) {
	const [items, setItems] = useState(defaultItems);
	const preloadPromiseRef = useRef();

	function preloadItems() {
		if (!preloadPromiseRef.current) {
			preloadPromiseRef.current = fetch(itemsURL)
				.then((response) => response.json())
				.then((responseItems) =>
					setItems(mapItemsOnClick(responseItems))
				);
		}
	}

	useEffect(() => {
		if (preloadPromiseRef.current) {
			const firstMenuItem = document.querySelector(
				'.dropdown-menu-personal-menu [role=menuitem]'
			);
			firstMenuItem?.focus();
		}
	}, [items]);

	return (
		<ClayDropDownWithItems
			items={items}
			menuElementAttrs={{className: 'dropdown-menu-personal-menu'}}
			trigger={
				label ? (
					<div
						dangerouslySetInnerHTML={{__html: label}}
						onFocus={preloadItems}
						onMouseOver={preloadItems}
					/>
				) : (
					<ClayButton
						aria-label={Liferay.Language.get('personal-menu')}
						className="rounded-circle"
						displayType="unstyled"
						onFocus={preloadItems}
						onMouseOver={preloadItems}
					>
						<span
							className={`sticker sticker-user-icon sticker-${size}`}
						>
							<ClaySticker
								className={`user-icon-color-${color}`}
								shape="circle"
								size={size}
							>
								{userPortraitURL ? (
									<img
										alt=""
										className="sticker-img"
										src={userPortraitURL}
									/>
								) : (
									<ClayIcon symbol="user" />
								)}
							</ClaySticker>

							{isImpersonated && (
								<ClaySticker
									className="sticker-user-icon"
									id="impersonate-user-sticker"
									outside
									position="bottom-right"
									shape="circle"
									size={size ? 'sm' : ''}
								>
									<span id="impersonate-user-icon">
										<ClayIcon symbol="user" />
									</span>
								</ClaySticker>
							)}
						</span>
					</ClayButton>
				)
			}
		/>
	);
}

PersonalMenu.propTypes = {
	itemsURL: PropTypes.string,
};

export default PersonalMenu;
