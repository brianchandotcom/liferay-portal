/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
