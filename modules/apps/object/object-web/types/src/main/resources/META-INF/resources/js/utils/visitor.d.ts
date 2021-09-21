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

import {
	TObjectLayout,
	TObjectLayoutBox,
	TObjectLayoutColumn,
	TObjectLayoutTab,
} from '../components/layout/types';
declare class TabsVisitor {
	private _layout;
	constructor(layout: TObjectLayout);
	dispose(): void;
	setLayout(layout: TObjectLayout): void;
	mapFields(mapper: (field: TObjectLayoutColumn) => void): any;
}
declare class BoxesVisitor {
	private _tab;
	constructor(tab: TObjectLayoutTab);
	dispose(): void;
	setTab(tab: TObjectLayoutTab): void;
	mapFields(mapper: (field: TObjectLayoutColumn) => void): any;
}
declare class RowsVisitor {
	private _box;
	constructor(box: TObjectLayoutBox);
	dispose(): void;
	setBox(box: TObjectLayoutBox): void;
	mapFields(mapper: (field: TObjectLayoutColumn) => void): any;
}
export {BoxesVisitor, RowsVisitor, TabsVisitor};
