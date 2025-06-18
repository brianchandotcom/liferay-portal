/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../contexts/StateContext';
import getFieldsArray from '../utils/getFieldsArray';

export default function selectStructureFields(state: State) {
	return getFieldsArray(state.structure);
}
