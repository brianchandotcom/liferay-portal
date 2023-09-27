/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import ThemeContext from '../../ThemeContext.es';

const HiddenInputs = ({valueMap = {}}) => {
	const {namespace} = useContext(ThemeContext);

	return Object.keys(valueMap).map((key) => (
		<input
			id={`${namespace}${key}`}
			key={key}
			name={`${namespace}${key}`}
			readOnly
			type="hidden"
			value={valueMap[key]}
		/>
	));
};

HiddenInputs.propTypes = {
	valueMap: PropTypes.object.isRequired,
};

export default HiddenInputs;
