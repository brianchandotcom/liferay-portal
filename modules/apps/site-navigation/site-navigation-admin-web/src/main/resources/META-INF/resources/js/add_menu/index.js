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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useCallback, useState} from 'react';

function AddMenu({dropdownItems, portletId}) {
	const [active, setActive] = useState(false);

	const handleItemClick = useCallback(event => {
		event.currentTarget = event.target;

		Liferay.Util.openInDialog(event.nativeEvent || event, {
			dialog: {
				destroyOnHide: true
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer'
			},
			id: `_${portletId}_addMenuItem`,
			title: event.target.title || event.target.innerText,
			uri: event.data.item.href || event.target.dataset.href
		});
	}, []);

	if (!Liferay.component(adaptiveMediaProgressComponentId)) {
		class AddMenuDefaultEventHandler {
			handleItemClicked(event) {
				handleItemClick(event);
			}
		}

		Liferay.component(
			`_${portletId}_AddMenuDefaultEventHandler`,
			new AddMenuDefaultEventHandler(),
			{
				destroyOnNavigate: true
			}
		);
	}

	return (
		<ClayDropDown
			active={active}
			onActiveChange={newVal => setActive(newVal)}
			trigger={
				<ClayButton
					className={
						'btn btn-primary dropdown-toggle nav-btn nav-btn-monospaced'
					}
				>
					<ClayIcon symbol="plus" />
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{dropdownItems.map(({href, label}) => (
					<ClayDropDown.Item
						data-href={href}
						key={href}
						onClick={handleItemClick}
					>
						{label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

export default function(props) {
	return <AddMenu {...props} />;
}
