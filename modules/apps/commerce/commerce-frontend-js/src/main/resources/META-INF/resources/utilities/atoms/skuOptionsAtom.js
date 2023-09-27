/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '@liferay/frontend-js-state-web';

const skuOptionsAtom = State.atom('skuOptionsAtom', {
	errors: [],
	namespace: '',
	skuOptions: [],
	updating: false,
});

export default skuOptionsAtom;
