/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export declare type TEmptyState = {
	contentRenderer?: () => JSX.Element;
	description?: string;
	noResultsTitle: string;
	title: string;
};
interface ITableStateRendererProps extends React.HTMLAttributes<HTMLElement> {
	empty: boolean;
	emptyState: TEmptyState;
	error: boolean;
	loading: boolean;
	refetch: () => void;
}
declare const TableStateRenderer: React.FC<ITableStateRendererProps>;
export default TableStateRenderer;
