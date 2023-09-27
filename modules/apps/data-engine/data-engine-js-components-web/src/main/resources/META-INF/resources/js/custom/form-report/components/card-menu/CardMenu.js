/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
