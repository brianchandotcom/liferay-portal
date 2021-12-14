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
import ClayPopover from '@clayui/popover';
import PropTypes from 'prop-types';
import React from 'react';

const CategoriesPopover = ({categories, vocabulary}) => {
	return (
		<ClayPopover
			alignPosition="top"
			disableScroll={true}
			header={`${categories.length} ${vocabulary} Categories`}
			trigger={<ClayLabel large={true}>+{categories.length}</ClayLabel>}
		>
			{categories.map((cat, index) => (
				<ClayLabel key={index} large={true}>
					{cat}
				</ClayLabel>
			))}
		</ClayPopover>
	);
};

CategoriesPopover.propTypes = {
	categories: PropTypes.array.isRequired,
	vocabulary: PropTypes.string.isRequired,
};

export default CategoriesPopover;
