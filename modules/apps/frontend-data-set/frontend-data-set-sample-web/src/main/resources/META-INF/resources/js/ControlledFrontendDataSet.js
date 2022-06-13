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

import ClayButton from '@clayui/button';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React, {useState} from 'react';

const ControlledFrontendDataSet = ({items, ...otherProps}) => {
	const [users, setUsers] = useState(items);

	return (
		<>
			<ClayButton
				onClick={() => {
					setUsers((users) => {
						const nextIndex = users.length;

						const user = users[nextIndex - 1];

						return [
							...users,
							{
								emailAddress: user.emailAddress + nextIndex,
								firstName: user.firstName + nextIndex,
								id: user.id + nextIndex,
								lastName: user.lastName + nextIndex,
							},
						];
					});
				}}
			>
				{Liferay.Language.get('add-user')}
			</ClayButton>
			<FrontendDataSet items={users} {...otherProps} />
		</>
	);
};

export default ControlledFrontendDataSet;
