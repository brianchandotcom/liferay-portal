/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {openModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

const Component = ({iconClass, iconName, items, title}) => {
	const tranformedItems = items.map((item) => {
		if (item.action && item.action === 'publishToLive') {
			return {
				...item,
				onClick() {
					openModal({
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
