/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

function InputCheckboxRenderer({updateItem, value}) {
	return (
		<ClayCheckbox
			checked={Boolean(value)}
			onChange={(event) => {
				updateItem(event.target.checked);
			}}
		/>
	);
}

InputCheckboxRenderer.propTypes = {
	updateItem: PropTypes.func,
	value: PropTypes.bool,
};

export default InputCheckboxRenderer;
