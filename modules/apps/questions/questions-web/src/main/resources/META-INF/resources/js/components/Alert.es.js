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
import React, {useEffect, useState} from 'react';

export default ({displayType = 'danger', info}) => {
	const [alert, setAlert] = useState(null);

	useEffect(() => {
		if (info && (info.title || info.message)) {
			setAlert(info);
		}
	}, [info]);

	return (
		<>
			{alert && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={5000}
						displayType={displayType}
						onClose={() => {
							setAlert(null);
						}}
						title={alert.title || alert.message}
					>
						{alert.title && alert.message}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</>
	);
};
