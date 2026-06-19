/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import PropTypes from 'prop-types';
import React from 'react';

import SidebarPanel from '../../SidebarPanel';

const Authentication = () => {
	return (
		<SidebarPanel panelTitle={Liferay.Language.get('authentication')}>
			<ClayAlert displayType="info">
				{Liferay.Language.get(
					'requests-are-made-on-behalf-of-the-current-user-the-authorization-header-is-set-automatically'
				)}
			</ClayAlert>

			<p className="text-secondary">
				{Liferay.Language.get(
					'future-support-for-external-apis-with-custom-authentication-methods'
				)}
			</p>
		</SidebarPanel>
	);
};

Authentication.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default Authentication;
