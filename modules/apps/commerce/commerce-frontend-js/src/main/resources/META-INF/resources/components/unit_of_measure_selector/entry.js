/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import launcher from '../../utilities/launcher';
import UnitOfMeasureSelector from './UnitOfMeasureSelector';

export default function entry(...data) {
	return launcher(UnitOfMeasureSelector, ...data);
}
