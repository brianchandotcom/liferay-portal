/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {IFDSViewSectionProps} from '../../FDSView';
import Fields from '../fields/Fields';
import {IBaseVisualizationMode} from './VisualizationModes';

export interface ITable extends IBaseVisualizationMode<'table'> {}

export default function Table(props: IFDSViewSectionProps) {
	return <Fields {...props} />;
}
