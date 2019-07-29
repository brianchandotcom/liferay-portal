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

import ClayLabel from '@clayui/label';
import PropTypes from 'prop-types';
import React from 'react';

const MappedContent = props => {
	const {label, style} = props.status;

	return (
		<li className="fragments-editor__mapped-content">
			<div className="d-flex py-3 pl-2 flex-column">
				<strong className="list-group-title truncate-text">
					{props.title}
				</strong>

				<span className="text-secondary small">{props.name}</span>

				<span className="text-secondary small">
					{props.usagesCount}
				</span>

				<ClayLabel
					className="align-self-start mt-2"
					displayType={style}
				>
					{label}
				</ClayLabel>
			</div>
		</li>
	);
};

MappedContent.propTypes = {
	name: PropTypes.string.isRequired,
	status: PropTypes.shape({
		label: PropTypes.string,
		style: PropTypes.string
	}),
	title: PropTypes.string.isRequired,
	usagesCount: PropTypes.number.isRequired
};

export {MappedContent};
export default MappedContent;
