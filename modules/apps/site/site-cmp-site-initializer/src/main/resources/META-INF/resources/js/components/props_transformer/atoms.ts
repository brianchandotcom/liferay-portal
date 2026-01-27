/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFDSState} from '@liferay/frontend-data-set-web';
import {State} from '@liferay/frontend-js-state-web';

export const CMP_TASKS_FDS_ATOM_ID = 'cmpTasksFDSAtom';

const cmpTasksFDSAtom = State.atom<IFDSState>(CMP_TASKS_FDS_ATOM_ID, {
	filters: [],
	search: {
		query: '',
	},
});

export {cmpTasksFDSAtom};
