/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React from 'react';

const ErrorAlert = ({
	handleClick,
	handleClose,
	itemSelectorEventName,
	itemSelectorURL,
	message,
}) => {
	return (
		<div className="error-wrapper">
			<ClayAlert displayType="danger" onClose={handleClose}>
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
