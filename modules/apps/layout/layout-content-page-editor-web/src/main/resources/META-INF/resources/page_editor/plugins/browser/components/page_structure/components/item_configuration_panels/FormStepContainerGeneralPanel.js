/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {useSelector} from '../../../../../../app/contexts/StoreContext';
import {getResponsiveConfig} from '../../../../../../app/utils/getResponsiveConfig';
import {getLayoutDataItemPropTypes} from '../../../../../../prop_types/index';
import {CommonStyles} from './CommonStyles';

export function FormStepContainerGeneralPanel({item}) {
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);

	return (
		<CommonStyles
			commonStylesValues={itemConfig.styles}
			item={item}
			role={COMMON_STYLES_ROLES.general}
		/>
	);
}

FormStepContainerGeneralPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired,
		}).isRequired,
	}),
};
