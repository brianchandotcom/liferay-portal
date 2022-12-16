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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import PropTypes from 'prop-types';
import React from 'react';

export default function ActionsDropdown({actions}) {
	return actions?.length ? (
		<ClayDropDownWithItems
			items={actions}
			renderMenuOnClick
			trigger={
				<ClayButtonWithIcon
					displayType="unstyled"
					small
					symbol="ellipsis-v"
				/>
			}
		/>
	) : null;
}

ActionsDropdown.propTypes = {
	actions: PropTypes.arrayOf(
		PropTypes.shape({
			data: PropTypes.shape({
				action: PropTypes.string,
				deleteURL: PropTypes.string,
				permissionsURL: PropTypes.string,
			}),
			href: PropTypes.string,
			icon: PropTypes.string,
			label: PropTypes.string.isRequired,
		})
	),
};
