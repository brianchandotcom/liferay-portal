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

import classNames from 'classnames';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const Collapse = props => {
	const [open, setOpen] = useState(props.open);
	const icon = open ? 'angle-down' : 'angle-right';

	useEffect(() => {
		setOpen(props.open);
	}, [props.open]);

	const handleClick = () => {
		setOpen(!open);
	};

	// "key" here in ClayIcon works around a bug caused by svg4everybody
	// wherein the icon doesn't update when "symbol" changes.
	const iconKey = icon;

	return (
		<div className="mb-2">
			<button
				aria-expanded={open}
				className={classNames(
					'align-items-end',
					'btn',
					'btn-unstyled',
					'collapse-icon',
					'd-flex',
					'fragments-editor-sidebar-section__collapse',
					'justify-content-between',
					'sheet-subtitle',
					'w-100'
				)}
				onClick={handleClick}
				type="button"
			>
				{props.label}
				<ClayIcon key={iconKey} symbol={icon} />
			</button>

			{open && props.children}
		</div>
	);
};

Collapse.propTypes = {
	children: PropTypes.node.isRequired,
	open: PropTypes.bool
};

export default Collapse;
