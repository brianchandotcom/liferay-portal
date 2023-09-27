/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo} from 'react';

import {useSelector} from '../../contexts/StoreContext';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';

export default function useBackURL() {
	const [backLinkElement, backLinkURL] = useMemo(() => {
		const backLinkElement = document.querySelector('.lfr-back-link');

		try {
			return [backLinkElement, new URL(backLinkElement?.href)];
		}
		catch (error) {
			return [];
		}
	}, []);

	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	useEffect(() => {
		if (backLinkElement && backLinkURL && segmentsExperienceId) {
			backLinkURL.searchParams.set(
				'segmentsExperienceId',
				segmentsExperienceId
			);
			backLinkElement.href = backLinkURL.toString();

			const currentURL = new URL(window.location.href);

			if (currentURL.searchParams.has('p_l_back_url')) {
				currentURL.searchParams.set(
					'p_l_back_url',
					backLinkURL.toString()
				);

				window.history.replaceState(
					null,
					document.title,
					currentURL.toString()
				);
			}
		}
	}, [backLinkElement, backLinkURL, segmentsExperienceId]);
}
