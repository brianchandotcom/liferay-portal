/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import {createContext} from 'react';

const ConnectionContext = createContext({
	validAnalyticsConnection: true,
});

ConnectionContext.Provider.propTypes = {
	value: PropTypes.shape({
		validAnalyticsConnection: PropTypes.bool.isRequired,
	}).isRequired,
};

export default ConnectionContext;
