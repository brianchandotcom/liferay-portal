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

const INDEX_REGEX = /.*(\d+)$/;

export default ({fields}) => {
	const [itemSelectedIndex, setItemSelectedIndex] = useState(() => {
		if (window.location.hash) {
			return window.location.hash.match(INDEX_REGEX)[1];
		}
		else {
			return null;
		}
	});

	const {portletNamespace} = useContext(SidebarContext);

	const shortcuts = fields.map((field, index) => {
		if (Object.keys(fieldTypes).includes(field.type)) {
			return (
				<li
					key={`card-item-${index}`}
					onClick={() => setItemSelectedIndex(index)}
				>
					<a
						className={`${
							itemSelectedIndex == index ? 'selected' : ''
						}`}
						data-senna-off
						href={`#${portletNamespace}card_${index}`}
					>
						<div className="indicator"></div>
						<div className="field-label">{field.label}</div>
					</a>
				</li>
			);
		}
	});

	return <ul>{shortcuts}</ul>;
};
