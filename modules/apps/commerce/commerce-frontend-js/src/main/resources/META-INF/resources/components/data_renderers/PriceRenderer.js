/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import Price from '../price/Price';

function PriceRenderer(data) {
	if (!data.value) {
		return null;
	}

	return <Price compact={true} price={data.value} />;
}

PriceRenderer.propTypes = {
	value: PropTypes.object,
};

export default PriceRenderer;
