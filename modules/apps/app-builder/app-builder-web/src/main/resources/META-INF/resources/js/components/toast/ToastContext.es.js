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

import ClayAlert from '@clayui/alert';
import React, {createContext, useState} from 'react';

const ToastContext = createContext();

const ToastContextProvider = ({autoClose = 5000, children}) => {
	const [toasts, setToasts] = useState([]);

	const addToast = toast => {
		toast = {
			...toast,
			id: Math.floor(Math.random() * 1000)
		};

		setToasts(prevToasts => prevToasts.concat(toast));
	};

	return (
		<ToastContext.Provider value={{addToast}}>
			{children}

			<ClayAlert.ToastContainer>
				{toasts.map(({id, message, ...restProps}) => (
					<ClayAlert
						autoClose={autoClose}
						key={id}
						onClose={() => {
							setToasts(prevToasts =>
								prevToasts.filter(toast => toast.id !== id)
							);
						}}
						{...restProps}
					>
						{message}
					</ClayAlert>
				))}
			</ClayAlert.ToastContainer>
		</ToastContext.Provider>
	);
};

export {ToastContext, ToastContextProvider};
