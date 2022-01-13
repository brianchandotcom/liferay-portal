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
import ClayLabel from '@clayui/label';
import ClayPopover from '@clayui/popover';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import useOnClickOutside from '../hooks/useOnClickOutside';

const CategoriesPopover = ({categories, vocabulary}) => {
	const [openPopover, setOpenPopover] = useState(false);

	useOnClickOutside(['.categories-popover', '.category-label-see-more'], () =>
		setOpenPopover(false)
	);

	return (
		<ClayPopover
			alignPosition="top"
			className="categories-popover"
			disableScroll={true}
			header={`${categories.length} ${vocabulary} ${Liferay.Language.get(
				'categories'
			)}`}
			onShowChange={setOpenPopover}
			show={openPopover}
			trigger={
				<ClayButton
					className="category-label category-label-see-more label label-lg label-secondary"
					displayType="unstyled"
				>
					{`+ ${categories.length}`}
				</ClayButton>
			}
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
