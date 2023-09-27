/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import {Tabs} from '../Tabs.es';
import * as DefaultVariant from './DefaultVariant.es';

export function Column({children, column, index}) {
	if (!column.fields.length) {
		return null;
	}

	return (
		<ClayLayout.Col key={index} md={column.size}>
			{column.fields.map((field, index) => children({field, index}))}
		</ClayLayout.Col>
	);
}

Column.displayName = 'TabbedVariant.Column';

export function Container({activePage, children, pageIndex, pages}) {
	return (
		<div className="ddm-form-page-container tabbed">
			{!!pages.length && pageIndex === activePage && (
				<Tabs
					activePage={activePage}
					pageIndex={pageIndex}
					pages={pages}
				/>
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
}

Container.displayName = 'TabbedVariant.Container';

export function Page({children}) {
	return children;
}

Page.displayName = 'TabbedVariant.Page';

export function Rows({children, rows}) {
	return rows.map((row, index) => children({index, row}));
}

Rows.displayName = 'TabbedVariant.Rows';
