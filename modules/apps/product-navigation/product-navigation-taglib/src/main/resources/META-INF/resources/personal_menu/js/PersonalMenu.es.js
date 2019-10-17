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
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {createRef, useState} from 'react';

const preloadPromise = createRef();

function PersonalMenu({itemsURL}) {
	const [active, setActive] = useState(false);
	const [items, setItems] = useState([]);

	function preloadItems() {
		if (!preloadPromise.current) {
			preloadPromise.current = fetch(itemsURL)
				.then(response => response.json())
				.then(items => setItems(items));
		}

		return preloadPromise.current;
	}

	function toggleActive(newVal) {
		preloadItems().then(setActive(newVal));
	}

	return (
		<ClayDropDownWithItems
			active={active}
			alignmentPosition={Align.BottomRight}
			items={items}
			onActiveChange={toggleActive}
			trigger={
				<ClayButton
					displayType="unstyled"
					onFocus={preloadItems}
					onMouseOver={preloadItems}
					symbol="user"
				>
					<ClaySticker
						className="user-icon-color-4"
						shape="circle"
						size="lg"
					>
						<ClayIcon symbol="user" />
					</ClaySticker>
				</ClayButton>
			}
		/>
	);
}

PersonalMenu.propTypes = {
	itemsURL: PropTypes.string
};

export default function(props) {
	return <PersonalMenu {...props} />;
}
