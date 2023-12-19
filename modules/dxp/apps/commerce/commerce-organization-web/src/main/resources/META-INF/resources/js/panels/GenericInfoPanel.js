/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {INFO_PANEL_MODE_MAP} from '../utils/constants';
import EditAccountInfoPanel from './EditAccountInfoPanel';
import EditOrganizationInfoPanel from './EditOrganizationInfoPanel';
import EditUserInfoPanel from './EditUserInfoPanel';
import ViewAccountInfoPanel from './ViewAccountInfoPanel';
import ViewOrganizationInfoPanel from './ViewOrganizationInfoPanel';
import ViewUserInfoPanel from './ViewUserInfoPanel';

function GenericInfoPanel(props) {
	const Views = {
		account: {
			edit: EditAccountInfoPanel,
			view: ViewAccountInfoPanel,
		},
		organization: {
			edit: EditOrganizationInfoPanel,
			view: ViewOrganizationInfoPanel,
		},
		user: {
			edit: EditUserInfoPanel,
			view: ViewUserInfoPanel,
		},
	};

	const PanelView = Views[props.type][props.mode];

	return <PanelView {...props} />;
}

GenericInfoPanel.defaultProps = {
	mode: INFO_PANEL_MODE_MAP.view,
};

GenericInfoPanel.propTypes = {
	closePanelViewHandler: PropTypes.func.isRequired,
	data: PropTypes.object.isRequired,
	mode: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	pathImage: PropTypes.string.isRequired,
	selectLogoURL: PropTypes.string.isRequired,
	spritemap: PropTypes.string.isRequired,
	type: PropTypes.string.isRequired,
	updatePanelViewHandler: PropTypes.func.isRequired,
};

export default GenericInfoPanel;
