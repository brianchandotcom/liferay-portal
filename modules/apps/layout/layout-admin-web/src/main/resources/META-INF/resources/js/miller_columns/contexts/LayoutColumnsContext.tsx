/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Dispatch, ReactNode, SetStateAction, useState} from 'react';

import {MillerColumnItem} from '../types/MillerColumnItem';

export type LayoutColumns = Array<MillerColumnItem[]>;

const LayoutColumnsContext = React.createContext<{
	layoutColumns: LayoutColumns;
	setLayoutColumns: Dispatch<SetStateAction<LayoutColumns>>;
}>({
	layoutColumns: [],
	setLayoutColumns: () => {},
});

function LayoutColumnsProvider({
	children,
	initialColumns,
}: {
	children: ReactNode;
	initialColumns: LayoutColumns;
}) {
	const [layoutColumns, setLayoutColumns] =
		useState<LayoutColumns>(initialColumns);

	return (
		<LayoutColumnsContext.Provider
			value={{
				layoutColumns,
				setLayoutColumns,
			}}
		>
			{children}
		</LayoutColumnsContext.Provider>
	);
}

export {LayoutColumnsContext, LayoutColumnsProvider};
