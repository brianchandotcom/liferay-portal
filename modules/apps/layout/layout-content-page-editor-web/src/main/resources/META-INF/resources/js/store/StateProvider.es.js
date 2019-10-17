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

import React, {useContext, useEffect, useState} from 'react';

import StateContext from './StateContext.es';
import StoreContext from './StoreContext.es';

const StateProvider = props => {
	const store = useContext(StoreContext);
	const [storeState, setStoreState] = useState(store ? store.getState() : {});

	useEffect(() => {
		if (store) {
			const subscriber = store.on('change', () =>
				setStoreState(store.getState())
			);

			return () => subscriber.removeListener();
		}
	}, [store]);

	return (
		<StateContext.Provider value={storeState}>
			{props.children}
		</StateContext.Provider>
	);
};

export {StateProvider};
export default StateProvider;
