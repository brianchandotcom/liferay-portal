/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

const EditTagsContext = React.createContext({
	namespace: '',
});

EditTagsContext.Provider.propTypes = {
	value: PropTypes.shape({
		namespace: PropTypes.string,
	}),
};

export default EditTagsContext;
