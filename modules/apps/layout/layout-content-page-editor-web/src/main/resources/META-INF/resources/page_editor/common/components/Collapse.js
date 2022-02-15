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

import './Collapse.scss';

export default function Collapse({children, label, open}) {
	const [isOpen, setIsOpen] = useState(open);
	const collapseIcon = isOpen ? 'angle-down' : 'angle-right';

	const collapseIconClassName = isOpen ? 'open' : 'closed';

	useEffect(() => {
		setIsOpen(open);
	}, [open]);

	const handleClick = () => {
		setIsOpen(!isOpen);
	};

	return (
		<div
			className={classNames(
				'panel-group panel-group-flush page-editor__collapse'
			)}
		>
			<button
				aria-expanded={isOpen}
				className={classNames(
					'btn',
					'btn-unstyled',
					'collapse-icon',
					'sheet-subtitle',
					{
						collapsed: !isOpen,
					}
				)}
				onClick={handleClick}
				type="button"
			>
				<span className="c-inner text-truncate" tabIndex="-1">
					{label}

					<span className={`collapse-icon-${collapseIconClassName}`}>
						<ClayIcon key={collapseIcon} symbol={collapseIcon} />
					</span>
				</span>
			</button>

			<div className="page-editor__collapse__content">
				{isOpen && children}
			</div>
		</div>
	);
}

Collapse.propTypes = {
	children: PropTypes.node.isRequired,
	label: PropTypes.string,
	open: PropTypes.bool,
};
