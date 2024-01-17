/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State, useLiferayState} from '@liferay/frontend-js-state-web';
import {useEffect} from 'react';

import {useSelector} from '../contexts/StoreContext';
import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import InfoItemService from '../services/InfoItemService';

const STATUS = {
	idle: 'idle',
	loading: 'loading',
	saved: 'saved',
};

const INITIAL_STATE = {
	data: [],
	status: STATUS.idle,
};

export const pageContentsAtom = State.atom('page-contents', INITIAL_STATE);

export default function usePageContents() {
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const [contents, setContents] = useLiferayState(pageContentsAtom);

	useEffect(() => {
		Liferay.once('endNavigate', clearPageContents);
	}, []);

	useEffect(() => {
		const {status} = State.readAtom(pageContentsAtom);

		if (status === STATUS.idle) {
			setContents({status: STATUS.loading});

			InfoItemService.getPageContents({
				segmentsExperienceId,
			}).then((pageContents) => {
				setContents({data: pageContents, status: STATUS.saved});
			});
		}
	}, [contents, segmentsExperienceId, setContents]);

	return contents.data || [];
}

export function clearPageContents() {
	State.writeAtom(pageContentsAtom, INITIAL_STATE);
}
