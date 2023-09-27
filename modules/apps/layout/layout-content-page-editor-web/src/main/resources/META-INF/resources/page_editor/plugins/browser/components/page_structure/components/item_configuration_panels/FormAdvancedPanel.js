/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {HideFromSearchField} from '../../../../../../app/components/fragment_configuration_fields/HideFromSearchField';
import {getLayoutDataItemPropTypes} from '../../../../../../prop_types/index';
import CSSFieldSet from './CSSFieldSet';

export default function FormAdvancedPanel({item}) {
	return (
		<>
			<HideFromSearchField item={item} />
			<CSSFieldSet item={item} />
		</>
	);
}

FormAdvancedPanel.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};
