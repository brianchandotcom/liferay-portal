/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Fragment} from '../Fragment';
import {Issue} from './Issue';

interface SelectedFragment extends Fragment {
	title: string;
	type: 'fragment';
}

interface SelectedIssue extends Issue {
	type: 'issue';
}

export type SelectedItem = SelectedFragment | SelectedIssue;
