/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				size="sm"
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
