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

import {Tabs} from '../Tabs.es';
import * as DefaultVariant from './DefaultVariant.es';

export const Column = ({children, column, index}) => {
	if (column.fields.length === 0) {
		return null;
	}

	return (
		<div className={`col-md-${column.size}`} key={index}>
			{column.fields.map((field, index) => children({field, index}))}
		</div>
	);
};

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

export const Page = ({children}) => children;

export const Rows = ({children, rows}) =>
	rows.map((row, index) => children({index, row}));
