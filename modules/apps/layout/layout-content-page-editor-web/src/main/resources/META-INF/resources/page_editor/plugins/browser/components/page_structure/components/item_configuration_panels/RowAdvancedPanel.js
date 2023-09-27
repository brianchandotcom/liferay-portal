/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {HideFromSearchField} from '../../../../../../app/components/fragment_configuration_fields/HideFromSearchField';
import CSSFieldSet from './CSSFieldSet';

export function RowAdvancedPanel({item}) {
	return (
		<>
			<HideFromSearchField item={item} />
			<CSSFieldSet item={item} />
		</>
	);
}

RowAdvancedPanel.propTypes = {
	item: PropTypes.object,
};
