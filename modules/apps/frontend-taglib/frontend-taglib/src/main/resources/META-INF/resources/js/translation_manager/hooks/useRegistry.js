/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useRef} from 'react';

export default function useRegistry({componentId, states}) {
	const currentStateRef = useRef({...states});
	const eventsRef = useRef([]);
	const previousStateRef = useRef({...states});

	const detach = (stateName, callback) => {
		if (eventsRef.current) {
			const refIndex = eventsRef.current.findIndex(
				(event) =>
					stateName === event.stateName && callback === event.callback
			);

			if (refIndex !== -1) {
				eventsRef.current.splice(refIndex);
			}
		}
	};

	const get = (stateName) => {
		const stateValue = currentStateRef.current[stateName];

		if (stateValue) {
			return stateValue;
		}
	};

	const on = (stateName, callback) => {
		eventsRef.current.push({callback, stateName});

		return {
			detach: () => detach(stateName, callback),
		};
	};

	if (!Liferay.component(componentId)) {
		Liferay.component(
			componentId,
			{
				detach,
				get,
				on,
			},
			{
				destroyOnNavigate: true,
			}
		);
	}

	useEffect(() => {
		currentStateRef.current = {...states};
	}, [states]);

	useEffect(() => {
		const stateChanged = [];

		Object.entries(states).forEach(([key, value]) => {
			if (value !== previousStateRef.current[key]) {
				stateChanged.push(key);
			}
		});

		eventsRef.current.forEach(({callback, stateName}) => {
			if (stateChanged.includes(stateName)) {
				callback({
					newValue: states[stateName],
					previousValue: previousStateRef.current[stateName],
				});
			}
		});

		previousStateRef.current = {...states};
	}, [states]);
}
