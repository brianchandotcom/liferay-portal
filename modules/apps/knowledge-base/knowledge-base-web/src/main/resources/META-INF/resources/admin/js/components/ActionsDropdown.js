/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					aria-label={Liferay.Language.get('actions')}
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
