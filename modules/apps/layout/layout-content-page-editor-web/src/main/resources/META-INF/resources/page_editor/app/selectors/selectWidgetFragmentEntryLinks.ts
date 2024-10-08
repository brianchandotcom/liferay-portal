/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../reducers';
import selectSegmentsExperienceId from './selectSegmentsExperienceId';

export default function selectWidgetFragmentEntryLinks(state: State) {
	const nextSegmentsExperienceId = selectSegmentsExperienceId(state);

	return Object.values(state.fragmentEntryLinks).filter(
		({portletId, removed, ...fragmentEntryLink}) =>
			portletId &&
			!removed &&
			fragmentEntryLink.segmentsExperienceId === nextSegmentsExperienceId
	);
}
