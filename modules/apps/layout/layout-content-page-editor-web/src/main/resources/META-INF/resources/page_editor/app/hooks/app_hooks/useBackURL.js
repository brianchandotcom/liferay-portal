/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
