/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useReducer} from 'react';

import Toaster from './Toaster.es';

const ToasterContext = createContext();

const ACTION_TYPES = {
	ADD: 'ADD_TOASTER',
	CLEAR_ALL: 'CLEAR_ALL_TOASTERS',
	REMOVE: 'REMOVE_TOASTER',
};

const toasterReducer = ({toasts = []}, {type, value}) => {
	if (type === ACTION_TYPES.ADD) {
		toasts.push(value);
	}

	if (type === ACTION_TYPES.CLEAR_ALL) {
		toasts = [];
	}

	if (type === ACTION_TYPES.REMOVE) {
		toasts = toasts.filter((_, index) => index !== value);
	}

	return {toasts};
};

const ToasterProvider = ({children}) => {
	const [{toasts}, dispatch] = useReducer(toasterReducer, {toasts: []});

	const addToast = (newToast) => {
		dispatch({type: ACTION_TYPES.ADD, value: newToast});
	};

	const clearAll = () => {
		dispatch({type: ACTION_TYPES.CLEAR_ALL});
	};

	const removeToast = (removeIndex) => {
		dispatch({type: ACTION_TYPES.REMOVE, value: removeIndex});
	};

	return (
		<ToasterContext.Provider
			value={{
				addToast,
				clearAll,
				dispatch,
				removeToast,
				toasts,
			}}
		>
			<Toaster removeToast={removeToast} toasts={toasts} />

			{children}
		</ToasterContext.Provider>
	);
};

export {ToasterContext};
export default ToasterProvider;
