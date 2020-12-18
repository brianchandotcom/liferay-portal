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

import ClayLayout from '@clayui/layout';
import React from 'react';

import {Tabs} from '../Tabs.es';
import * as DefaultVariant from './DefaultVariant.es';

export const Column = ({children, column, index}) => {
	if (column.fields.length === 0) {
		return null;
	}

	return (
		<ClayLayout.Col key={index} md={column.size}>
			{column.fields.map((field, index) => children({field, index}))}
		</ClayLayout.Col>
	);
};

Column.displayName = 'TabbedVariant.Column';

export const Container = ({activePage, children, pageIndex, pages}) => (
	<div className="ddm-form-page-container tabbed">
		{pages.length > 0 && pageIndex === activePage && (
			<Tabs activePage={activePage} pageIndex={pageIndex} pages={pages} />
		)}

		<DefaultVariant.Container
			activePage={activePage}
			isBuilder={false}
			pageIndex={pageIndex}
		>
			{children}
		</DefaultVariant.Container>
	</div>
);

Container.displayName = 'TabbedVariant.Container';

export const Page = ({children}) => children;

Page.displayName = 'TabbedVariant.Page';

export const Rows = ({children, rows}) =>
	rows.map((row, index) => children({index, row}));

Rows.displayName = 'TabbedVariant.Rows';
