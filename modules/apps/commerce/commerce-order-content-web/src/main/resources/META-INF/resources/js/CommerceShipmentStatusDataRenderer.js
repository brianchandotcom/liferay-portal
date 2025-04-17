/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import PropTypes from 'prop-types';
import React from 'react';

function getLabelDisplay(value) {
	let label = {...value};

	if ('key' in label && 'name' in label) {
		label = {
			label: value.key,
			label_i18n: value.name,
		};
	}

	label.displayType = 'secondary';

	if (label.label === 'delivered') {
		label.displayType = 'info';
	}
	else if (
		label.label === 'processing' ||
		label.label === 'ready-to-ship'
	) {
		label.displayType = 'warning';
	}
	else if (label.label === 'shipped') {
		label.displayType = 'success';
	}

	return label;
}

const CommerceShipmentStatusDataRenderer = ({value}) => {
	const {displayType, label_i18n} = getLabelDisplay(value);

	return (
		<ClayLabel displayType={displayType}>
			{Liferay.Language.get(label_i18n)}
		</ClayLabel>
	);
};

export default CommerceShipmentStatusDataRenderer;

CommerceShipmentStatusDataRenderer.propTypes = {
	value: PropTypes.string,
};
