/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import SidebarPanel from '../../SidebarPanel';
import InputOutputVariables from '../shared-components/InputOutputVariables';

const Variables = () => {
	return (
		<SidebarPanel panelTitle={Liferay.Language.get('variables')}>
			<ClayForm.Group>
				<InputOutputVariables />
			</ClayForm.Group>
		</SidebarPanel>
	);
};

Variables.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default Variables;
