/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {getLayoutDataItemPropTypes} from '../../../../../../prop_types/index';
import CSSFieldSet from './CSSFieldSet';

export default function FormStepContainerAdvancedPanel({item}) {
	return <CSSFieldSet item={item} />;
}

FormStepContainerAdvancedPanel.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};
