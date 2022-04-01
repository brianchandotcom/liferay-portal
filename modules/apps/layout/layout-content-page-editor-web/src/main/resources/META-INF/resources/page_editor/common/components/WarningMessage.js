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
import PropTypes from 'prop-types';
import React from 'react';

export function WarningMessage({message}) {
	return (
		<div className="autofit-row mt-2 small text-warning">
			<div className="autofit-col">
				<div className="autofit-section mr-2">
					<ClayIcon symbol="warning-full" />
				</div>
			</div>

			<div className="autofit-col autofit-col-expand">
				<div className="autofit-section">{message}</div>
			</div>
		</div>
	);
}

WarningMessage.propTypes = {
	message: PropTypes.string.isRequired,
};
