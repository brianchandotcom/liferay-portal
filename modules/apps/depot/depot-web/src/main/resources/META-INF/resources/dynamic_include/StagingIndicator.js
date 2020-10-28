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

import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

const Component = ({iconClass, iconName, items, title}) => {
	const tranformedItems = items.map((item) => {
		if (item.action && item.action === 'publishToLive') {
			return {
				...item,
				onClick() {
					Liferay.Util.openModal({
						title: item.label,
						url: item.publishURL,
					});
				},
			};
		}
		else {
			return item;
		}
	});

	return (
		<ClayDropDownWithItems
			alignmentPosition={Align.BottomCenter}
			items={tranformedItems}
			trigger={
				<button className="staging-indicator-button">
					<ClayIcon className={iconClass} symbol={iconName} />

					<span className="staging-indicator-title">{title}</span>

					<ClayIcon symbol="caret-bottom" />
				</button>
			}
		/>
	);
};

Component.propTypes = {
	iconClass: PropTypes.string,
	iconName: PropTypes.string,
	title: PropTypes.string,
};

export default function (props) {
	return <Component {...props} />;
}
