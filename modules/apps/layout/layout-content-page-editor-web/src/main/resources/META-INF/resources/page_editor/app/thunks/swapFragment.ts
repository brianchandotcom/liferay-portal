/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../types/State';
import {FragmentEntryLink} from '../actions/addFragmentEntryLinks';
import swapFragmentAction from '../actions/swapFragment';
import {FragmentEntry} from '../actions/updateFragments';
import updateNetwork from '../actions/updateNetwork';
import FragmentService from '../services/FragmentService';

type Props = {
	editableValues: string;
	fragmentEntryKey: FragmentEntry['fragmentEntryKey'];
	fragmentEntryLinkId: FragmentEntryLink['fragmentEntryLinkId'];
	groupId?: string;
};

export default function swapFragment({
	editableValues,
	fragmentEntryKey,
	fragmentEntryLinkId,
	groupId,
}: Props) {
	return (
		dispatch: (
			action: ReturnType<typeof updateNetwork | typeof swapFragmentAction>
		) => void,
		getState: () => State
	) => {
		const {fragmentEntryLinks, segmentsExperienceId} = getState();

		const previousFragmentEntryKey =
			fragmentEntryLinks[fragmentEntryLinkId].fragmentEntryKey;

		return FragmentService.swapFragment({
			editableValues,
			fragmentEntryKey,
			fragmentEntryLinkId,
			groupId,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		}).then(({fragmentEntryLink, layoutData}) => {
			dispatch(
				swapFragmentAction({
					fragmentEntryLink,
					fragmentEntryLinkId,
					layoutData,
					previousFragmentEntryKey,
				})
			);
		});
	};
}
