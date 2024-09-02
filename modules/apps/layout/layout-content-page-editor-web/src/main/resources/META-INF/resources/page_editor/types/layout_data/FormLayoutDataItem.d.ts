/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	BaseLayoutDataItem,
	CommonStyles,
	ResponsiveConfig,
} from './BaseLayoutDataItem';
import {ContainerGeneralConfig} from './ContainerLayoutDataItem';

type MappedFormConfig =
	| Record<string, never>
	| {classNameId: string; classTypeId: string};

export type FormLayoutDataItem = BaseLayoutDataItem<
	'form',
	CommonStyles &
		ContainerGeneralConfig &
		MappedFormConfig & {
			formConfig: number;
			formType: 'multistep' | 'simple';
			numberOfSteps: number;
		} & ResponsiveConfig<{}>
>;
