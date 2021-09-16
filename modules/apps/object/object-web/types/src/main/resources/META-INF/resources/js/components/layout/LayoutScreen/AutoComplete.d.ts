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

import React from 'react';
interface IAutoCompleteProps extends React.HTMLAttributes<HTMLElement> {
	children: (item: any) => React.ReactNode;
	contentRight?: React.ReactNode;
	items: any[];
	onChangeQuery: (value: string) => void;
	onSelectItem: (item: any) => void;
	query: string;
	value?: string;
	emptyStateMessage: string;
	required?: boolean;
}
declare const AutoComplete: React.FC<IAutoCompleteProps>;
export default AutoComplete;
