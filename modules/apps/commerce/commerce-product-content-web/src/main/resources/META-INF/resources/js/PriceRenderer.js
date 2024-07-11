/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PriceComponent as Price} from 'commerce-frontend-js';
import PropTypes from 'prop-types';
import React from 'react';

function PriceRenderer(data) {
	if (!data.value) {
		return '';
	}

	return <Price compact={true} price={data.value} />;
}

PriceRenderer.propTypes = {
	data: PropTypes.object,
};

export default PriceRenderer;
