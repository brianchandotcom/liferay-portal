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

import {useEffect, useRef} from 'react';

export default function useRegistry({componentId, states}) {
	const currentState = useRef({...states});
	const eventsRef = useRef([]);
	const previousState = useRef({...states});

	const detach = (stateName, callback) => {
		if (eventsRef.current) {
			const refIndex = eventsRef.current.findIndex(
				event =>
					stateName === event.stateName && callback === event.callback
			);

			if (refIndex !== -1) {
				eventsRef.current.splice(refIndex);
			}
		}
	};

	const get = stateName => {
		const stateValue = currentState.current[stateName];

		if (stateValue) {
			return stateValue;
		}
	};

	const on = (stateName, callback) => {
		eventsRef.current.push({callback, stateName});

		return {
			detach: () => detach(stateName, callback)
		};
	};

	if (!Liferay.component(componentId)) {
		Liferay.component(
			componentId,
			{
				detach,
				get,
				on
			},
			{
				destroyOnNavigate: true
			}
		);
	}

	useEffect(() => {
		currentState.current = {...states};
	}, [states]);

	useEffect(() => {
		const stateChanged = [];

		Object.entries(states).forEach(([key, value]) => {
			if (value !== previousState.current[key]) {
				stateChanged.push(key);
			}
		});

		eventsRef.current.forEach(({callback, stateName}) => {
			if (stateChanged.includes(stateName)) {
				callback({
					newValue: states[stateName],
					previousValue: previousState.current[stateName]
				});
			}
		});

		previousState.current = {...states};
	}, [states]);
}
