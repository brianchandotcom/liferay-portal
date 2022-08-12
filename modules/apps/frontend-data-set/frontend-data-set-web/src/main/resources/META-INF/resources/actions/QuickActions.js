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
