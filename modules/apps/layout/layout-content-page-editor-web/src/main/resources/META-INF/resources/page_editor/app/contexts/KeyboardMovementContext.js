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

import React, {useCallback, useContext, useState} from 'react';

const INITIAL_STATE = {
	setSource: () => {},
	setTarget: () => {},
	source: null,
	target: {
		itemId: null,
		position: null,
	},
};

const KeyboardMovementContext = React.createContext(INITIAL_STATE);

function KeyboardMovementContextProvider({children}) {
	const [source, setSource] = useState(null);
	const [target, setTarget] = useState({
		itemId: null,
		position: null,
	});

	return (
		<KeyboardMovementContext.Provider
			value={{
				setSource,
				setTarget,
				source,
				target,
			}}
		>
			{children}
		</KeyboardMovementContext.Provider>
	);
}

function useDisableKeyboardMovement() {
	const {setSource, setTarget} = useContext(KeyboardMovementContext);

	return useCallback(() => {
		setSource(null);
		setTarget({
			itemId: null,
			position: null,
		});
	}, [setSource, setTarget]);
}

function useSetMovementSource() {
	return useContext(KeyboardMovementContext).setSource;
}

function useMovementSource() {
	return useContext(KeyboardMovementContext).source;
}

function useMovementTarget() {
	return useContext(KeyboardMovementContext).target;
}

function useSetMovementTarget() {
	return useContext(KeyboardMovementContext).setTarget;
}

export {
	KeyboardMovementContextProvider,
	useDisableKeyboardMovement,
	useMovementSource,
	useMovementTarget,
	useSetMovementSource,
	useSetMovementTarget,
};
