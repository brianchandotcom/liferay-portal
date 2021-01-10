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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React from 'react';

const ErrorAlert = ({
	handleClick,
	itemSelectorEventName,
	itemSelectorURL,
	message
}) => {
	return (
		<div className="error-wrapper">
			<ClayAlert displayType="danger">
				{message}

				{itemSelectorEventName && itemSelectorURL && (
					<ClayAlert.Footer>
						<ClayButton.Group>
							<ClayButton
								displayType="secondary"
								onClick={handleClick}
							>
								{Liferay.Language.get('select-file')}
							</ClayButton>
						</ClayButton.Group>
					</ClayAlert.Footer>
				)}
			</ClayAlert>
		</div>
	);
};

ErrorAlert.propTypes = {
	handleClick: PropTypes.func,
	itemSelectorEventName: PropTypes.string,
	itemSelectorURL: PropTypes.string,
	message: PropTypes.string.isRequired,
};

export default ErrorAlert;
