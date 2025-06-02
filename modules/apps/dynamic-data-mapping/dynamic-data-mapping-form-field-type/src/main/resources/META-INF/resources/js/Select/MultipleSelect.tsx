/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import MultipleSelectLocalizedObjectField, {
	MultipleSelectLocalizedObjectFieldProps,
} from '../localizedObjectFields/MultipleSelectLocalizedObjectField';
import {MultipleSelectBase} from './MultipleSelectBase';
import {MultipleSelectBaseProps} from './select.d';

export type MultipleSelectionProps = MultipleSelectBaseProps<string[] | string>;

const Main = (
	props: MultipleSelectionProps | MultipleSelectLocalizedObjectFieldProps
) => {
	const isLocalizedObjectField: boolean =
		Liferay.FeatureFlags['LPD-32050'] && !!props.localizedObjectField;

	return !isLocalizedObjectField ? (
		<MultipleSelectBase {...(props as MultipleSelectionProps)} />
	) : (
		<MultipleSelectLocalizedObjectField
			{...(props as MultipleSelectLocalizedObjectFieldProps)}
		/>
	);
};

export default Main;
