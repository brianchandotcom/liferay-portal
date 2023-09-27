/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React from 'react';

import {ButtonList} from './ButtonList';

export function CollapsableButtonList({items, label, onButtonClick}) {
	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={label}
			displayType="unstyled"
			showCollapseIcon
		>
			<ClayPanel.Body>
				<ButtonList items={items} onButtonClick={onButtonClick} />
			</ClayPanel.Body>
		</ClayPanel>
	);
}

CollapsableButtonList.propTypes = {
	items: PropTypes.arrayOf(PropTypes.object),
	label: PropTypes.string.isRequired,
	onButtonClick: PropTypes.func.isRequired,
};
