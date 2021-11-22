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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

const DropDownItem = ({
	cssClasses,
	deleteAction = false,
	direction,
	disabled,
	icon,
	index,
	onClick,
	text,
}) => {
	const handleClick = () => {
		onClick({deleteAction, direction, index});
	};

	return (
		<ClayDropDown.Item
			className={`${cssClasses} sortable-list-dropdown-item`}
		>
			<ClayButton
				block
				className="align-items-center d-flex font-weight-normal text-secondary"
				disabled={disabled}
				displayType={null}
				onClick={handleClick}
				small
			>
				<ClayIcon className="mr-3 mt-0" symbol={icon} />

				{text}
			</ClayButton>
		</ClayDropDown.Item>
	);
};

DropDownItem.defaultProps = {
	direction: null,
};

DropDownItem.propTypes = {
	cssClasses: PropTypes.string,
	direction: PropTypes.number,
	disabled: PropTypes.bool.isRequired,
	icon: PropTypes.string.isRequired,
	index: PropTypes.number.isRequired,
	onClick: PropTypes.func.isRequired,
	text: PropTypes.string.isRequired,
};

export default DropDownItem;
