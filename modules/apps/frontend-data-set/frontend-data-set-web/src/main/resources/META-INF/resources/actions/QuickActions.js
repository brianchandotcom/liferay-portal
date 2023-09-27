/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {LinkOrButton} from '@clayui/shared';
import PropTypes from 'prop-types';
import React from 'react';

import {formatActionURL} from '../utils/index';
import {actionsBasePropTypes} from './Actions';

function QuickActions({actions, itemData, itemId, onClick}) {
	return (
		<div className="quick-action-menu">
			{actions.map((action) => {
				return (
					<LinkOrButton
						aria-label={action.label || action.icon}
						className="component-action quick-action-item"
						displayType="unstyled"
						href={
							action.href &&
							formatActionURL(action.href, itemData)
						}
						key={action.data?.id || action.label}
						monospaced={false}
						onClick={(event) =>
							onClick({
								action,
								event,
								itemData,
								itemId,
							})
						}
						symbol={action.icon}
						title={action.label}
					>
						<ClayIcon symbol={action.icon} />
					</LinkOrButton>
				);
			})}
		</div>
	);
}

QuickActions.propTypes = {
	...actionsBasePropTypes,
	onClick: PropTypes.func.isRequired,
};

export default QuickActions;
