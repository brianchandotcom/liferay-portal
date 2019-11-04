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
	const icon = open ? 'angle-down' : 'angle-right';

	useEffect(() => {
		setOpen(props.open);
	}, [props.open]);

	const handleClick = () => {
		setOpen(!open);
	};

	return (
		<div className="mb-2">
			<button
				aria-expanded={open}
				className={classNames(
					'page-editor__collapse',
					'align-items-end',
					'btn',
					'btn-unstyled',
					'collapse-icon',
					'd-flex',
					'justify-content-between',
					'sheet-subtitle',
					'w-100'
				)}
				onClick={handleClick}
				type="button"
			>
				{props.label}
				<ClayIcon key={icon} symbol={icon} />
			</button>

			{open && props.children}
		</div>
	);
}

Collapse.propTypes = {
	children: PropTypes.node.isRequired,
	open: PropTypes.bool
};
