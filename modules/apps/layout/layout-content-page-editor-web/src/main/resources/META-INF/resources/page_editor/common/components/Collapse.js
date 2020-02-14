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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

export default function Collapse(props) {
	const [open, setOpen] = useState(props.open);
	const collapseIcon = open ? 'angle-down' : 'angle-right';
	const collapseIconClassName = open ? 'open' : 'closed';

	useEffect(() => {
		setOpen(props.open);
	}, [props.open]);

	const handleClick = () => {
		setOpen(!open);
	};

	return (
		<div className="page-editor__collapse panel-group panel-group-flush">
			<button
				aria-expanded={open}
				className={classNames('collapse-icon', 'sheet-subtitle', {
					collapsed: !open
				})}
				onClick={handleClick}
			>
				{props.label}
				<span className={`collapse-icon-${collapseIconClassName}`}>
					<ClayIcon key={collapseIcon} symbol={collapseIcon} />
				</span>
			</button>
			{open && props.children}
		</div>
	);
}

Collapse.propTypes = {
	children: PropTypes.node.isRequired,
	open: PropTypes.bool
};
