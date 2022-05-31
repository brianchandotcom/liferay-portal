/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useCallback, useEffect, useState} from 'react';

const INTERSECTION_OPTIONS = {
	root: null,
	threshold: 1.0,
};

export default function useIntersectionObserver() {
	const [trackedRefCurrent, setTrackedRefCurrent] = useState();
	const [isIntersecting, setIsIntersecting] = useState(false);

	const memoizedSetIntersecting = useCallback((entities) => {
		const target = entities[0];

		setIsIntersecting(target.isIntersecting);
	}, []);

	useEffect(() => {
		const observer = new IntersectionObserver(
			memoizedSetIntersecting,
			INTERSECTION_OPTIONS
		);

		if (trackedRefCurrent) {
			observer.observe(trackedRefCurrent);
		}

		return () => {
			if (trackedRefCurrent) {
				observer.unobserve(trackedRefCurrent);
			}
		};
	}, [memoizedSetIntersecting, trackedRefCurrent]);

	return [setTrackedRefCurrent, isIntersecting];
}
