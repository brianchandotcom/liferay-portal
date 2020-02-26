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

	const addToast = newToast => {
		dispatch({type: ACTION_TYPES.ADD, value: newToast});
	};

	const clearAll = () => {
		dispatch({type: ACTION_TYPES.CLEAR_ALL});
	};

	const removeToast = removeIndex => {
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
