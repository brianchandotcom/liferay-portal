/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import classNames from 'classnames';
import React from 'react';

import Cell from './Cell';
import ContextProvider from './ContextProvider';
import Row from './Row';
import Table from './Table';

function Body({children, className}) {
	const Component = Liferay.FeatureFlags['LPS-193005']
		? ClayTable.Body
		: 'div';

	return (
		<Component
			className={classNames(
				{
					'dnd-tbody': !Liferay.FeatureFlags['LPS-193005'],
				},
				className
			)}
		>
			{children}
		</Component>
	);
}

function Head({children, className}) {
	const Component = Liferay.FeatureFlags['LPS-193005']
		? ClayTable.Head
		: 'div';

	return (
		<Component
			className={classNames(
				{
					'dnd-thead': !Liferay.FeatureFlags['LPS-193005'],
				},
				className
			)}
		>
			{children}
		</Component>
	);
}

export default Object.assign(Table, {
	Body,
	Cell,
	ContextProvider,
	Head,
	Row,
	Table,
});
