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
import ClayButton from '@clayui/button';
import {getUid} from 'data-engine-js-components-web';
import React, {useCallback, useContext, useState} from 'react';

const ToastContext = React.createContext();

export const ToastProvider = ({children}) => {
	const [toastItems, setToastItems] = useState([]);

	const addToast = useCallback(
		(item) => {
			setToastItems((prevItems) => [
				...prevItems,
				{id: getUid(), ...item},
			]);
		},
		[setToastItems]
	);

	return (
		<ToastContext.Provider value={addToast}>
			<ClayAlert.ToastContainer>
				{toastItems.map(
					({action, id, message, title, ...otherProps}) => (
						<ClayAlert
							{...otherProps}
							autoClose={5000}
							key={id}
							onClose={() =>
								setToastItems((prevItems) =>
									prevItems.filter((item) => item.id !== id)
								)
							}
							title={title}
						>
							{message}

							{action && (
								<ClayAlert.Footer>
									<ClayButton.Group>
										{action}
									</ClayButton.Group>
								</ClayAlert.Footer>
							)}
						</ClayAlert>
					)
				)}
			</ClayAlert.ToastContainer>

			{children}
		</ToastContext.Provider>
	);
};

export const useToast = () => {
	return useContext(ToastContext);
};
