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
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import 'product-navigation-control-menu/css/TabItem.scss';

export default function TabItem({item}) {
	return (
		<li
			className={classNames('sidebar__panel__tab-item', {
				disabled: item.disabled,
			})}
		>
			<div className="sidebar__panel__tab-item-body">
				<ClayIcon className="mr-3" symbol={item.icon} />
				<div className="text-truncate title">{item.label}</div>
			</div>

			<ClayButton
				className="btn-monospaced sidebar__panel__tab-item-add"
				displayType="unstyled"
				small
			>
				<ClayIcon symbol="plus" />
				<span className="sr-only">{item.name}</span>
			</ClayButton>
		</li>
	);
}

TabItem.propTypes = {
	item: PropTypes.shape({
		data: PropTypes.object.isRequired,
		icon: PropTypes.string.isRequired,
		label: PropTypes.string.isRequired,
		preview: PropTypes.string,
		type: PropTypes.string.isRequired,
	}).isRequired,
};
