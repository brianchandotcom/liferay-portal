/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import PropTypes from 'prop-types';
import React from 'react';

import {getLabelDisplay} from './getLabelDisplay';

const CommerceStatusDataRenderer = ({value}) => {
	const {displayType, label_i18n} = getLabelDisplay(value);

	return (
		<ClayLabel displayType={displayType}>
			{Liferay.Language.get(label_i18n)}
		</ClayLabel>
	);
};

export default CommerceStatusDataRenderer;

CommerceStatusDataRenderer.propTypes = {
	value: PropTypes.string,
};
