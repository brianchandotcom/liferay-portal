/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useState} from 'react';

import fieldTypes from '../../utils/fieldTypes';
import {SidebarContext} from '../sidebar/SidebarContext';

const MAX_FIELD_LABEL_LENGTH = 91;

export default function CardShortcut({fields}) {
	const [itemSelectedIndex, setItemSelectedIndex] = useState();

	const {portletNamespace} = useContext(SidebarContext);

	const scrollToCard = (portletNamespace, index) => {
		const card = document.getElementById(
			`${portletNamespace}card_${index}`
		);

		if (card !== null) {
			card.scrollIntoView({behavior: 'smooth'});
		}
	};

	const showFieldLabel = (label) => {
		if (label.length > MAX_FIELD_LABEL_LENGTH) {
			return label.substr(0, MAX_FIELD_LABEL_LENGTH) + '...';
		}

		return label;
	};

	const shortcuts = fields.map((field, index) => {
		if (Object.keys(fieldTypes).includes(field.type)) {
			return (
				<li key={`card-item-${index}`}>
					<a
						className={`${
							itemSelectedIndex === index ? 'selected' : ''
						}`}
						data-senna-off
						onClick={() => {
							setItemSelectedIndex(index);
							scrollToCard(portletNamespace, index);
						}}
					>
						<div className="field-label">
							{showFieldLabel(field.label)}
						</div>
					</a>
				</li>
			);
		}
	});

	return <ul>{shortcuts}</ul>;
}
