/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import Cell from './Cell';
import Row from './Row';
import Table from './Table';
import TableContextProvider from './TableContextProvider';

function Body({children, className}) {
	return <div className={classNames('dnd-tbody', className)}>{children}</div>;
}

function Head({children, className}) {
	return <div className={classNames('dnd-thead', className)}>{children}</div>;
}

export default Object.assign(Table, {
	Body,
	Cell,
	Head,
	Row,
	Table,
	TableContextProvider,
});
