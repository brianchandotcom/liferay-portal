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

import React, {useContext, useState} from 'react';

import fieldTypes from '../../utils/fieldTypes.es';
import {SidebarContext} from '../sidebar/SidebarContext.es';

const MAX_FIELD_LABEL_LENGTH = 91;

export default ({fields}) => {
	const [itemSelectedIndex, setItemSelectedIndex] = useState();

	const {portletNamespace} = useContext(SidebarContext);

	const scrollToCard = (portletNamespace, index) => {
		const card = document.getElementById(
			`${portletNamespace}card_${index}`
		);

		if (card !== null) {
			card.scrollIntoView();
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
							itemSelectedIndex == index ? 'selected' : ''
						}`}
						data-senna-off
						onClick={() => {
							setItemSelectedIndex(index);
							scrollToCard(portletNamespace, index);
						}}
					>
						<div className="indicator"></div>
						<div className="field-label">
							{showFieldLabel(field.label)}
						</div>
					</a>
				</li>
			);
		}
	});

	return <ul>{shortcuts}</ul>;
};
