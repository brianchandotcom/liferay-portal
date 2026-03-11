/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFDSState} from '@liferay/frontend-data-set-web';
import {State} from '@liferay/frontend-js-state-web';

const advancedFDSAtom = State.atom<IFDSState>(
	'com_liferay_frontend_data_set_sample_web_internal_portlet_FDSSamplePortlet-advanced_fdsState',
	{
		filters: [],
		search: {
			query: '',
		},
	}
);

export {advancedFDSAtom};
