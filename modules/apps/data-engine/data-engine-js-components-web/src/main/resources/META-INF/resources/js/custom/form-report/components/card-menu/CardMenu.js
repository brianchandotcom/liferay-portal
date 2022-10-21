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

import {ClayVerticalNav} from '@clayui/nav';
import React, {useContext, useState} from 'react';

import {SidebarContext} from '../sidebar/SidebarContext';

const MAX_FIELD_LABEL_LENGTH = 91;

export default function CardMenu({fields}) {
	const [itemSelectedLabel, setItemSelectedLabel] = useState();

	const {portletNamespace} = useContext(SidebarContext);

	const scrollToCard = (portletNamespace, index) => {
		const card = document.getElementById(
			`${portletNamespace}card_${index}`
		);

		if (card !== null) {
			card.scrollIntoView({behavior: 'smooth'});
		}
	};

	const newItems = [];

	fields.forEach((field, index) => {
		let label = field.label;

		if (label.length > MAX_FIELD_LABEL_LENGTH) {
			label = label.substr(0, MAX_FIELD_LABEL_LENGTH) + '...';
		}
		newItems.push({
			label,
			onClick: () => {
				setItemSelectedLabel(label);
				scrollToCard(portletNamespace, index);
			},
		});
	});

	const menu = (
		<ClayVerticalNav
			items={newItems}
			large={true}
			triggerLabel={itemSelectedLabel ?? newItems[0].label}
		/>
	);

	return menu;
}
