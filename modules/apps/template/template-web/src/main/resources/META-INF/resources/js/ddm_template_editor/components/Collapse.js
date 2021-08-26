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
import React, {useState} from 'react';

const Collapse = ({children, label}) => {
	const [isOpen, setIsOpen] = useState(true);
	const collapseIcon = isOpen ? 'angle-down-small' : 'angle-right-small';
	const collapseIconClassName = isOpen ? 'open' : 'closed';

	const handleClick = () => {
		setIsOpen((isOpen) => !isOpen);
	};

	return (
		<div
			className={classNames(
				'ddm_template_editor__App-sidebar-collapse',
				'panel-group panel-group-flush'
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
				<span className="c-inner" tabIndex="-1">
					{label}
					<span className={`collapse-icon-${collapseIconClassName}`}>
						<ClayIcon key={collapseIcon} symbol={collapseIcon} />
					</span>
				</span>
			</button>
			{isOpen && children}
		</div>
	);
};

Collapse.propTypes = {
	children: PropTypes.node.isRequired,
	label: PropTypes.string.isRequired,
};

export default Collapse;
