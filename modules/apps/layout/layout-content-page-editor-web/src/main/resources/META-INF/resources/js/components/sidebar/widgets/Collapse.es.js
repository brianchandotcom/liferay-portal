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

	useEffect(() => {
		setOpen(props.open);
	}, [props.open]);

	const handleClick = () => {
		setOpen(!open);
	};

	const collapseClassNames = classNames(
		'align-items-end',
		'btn',
		'btn-unstyled',
		'collapse-icon',
		'd-flex',
		'justify-content-between',
		'mb-2',
		'px-2',
		'rounded-0',
		'sheet-subtitle',
		'w-100'
	);

	return (
		<div className="mb-2">
			<button
				aria-expanded={open}
				className={collapseClassNames}
				onClick={handleClick}
				type="button"
			>
				{props.label}

				<ClayIcon symbol={open ? 'angle-down' : 'angle-right'} />
			</button>

			{open && props.children}
		</div>
	);
};

Collapse.PropTypes = {
	children: PropTypes.node.isRequired,
	open: PropTypes.bool
};

export default Collapse;
