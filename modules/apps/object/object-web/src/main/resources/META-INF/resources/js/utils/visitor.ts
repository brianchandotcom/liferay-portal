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
	TObjectLayoutRow,
	TObjectLayoutTab,
} from '../components/layout/types';

class TabsVisitor {
	private _layout: any;

	constructor(layout: TObjectLayout) {
		this.setLayout(layout);
	}

	dispose() {
		this._layout = null;
	}

	setLayout(layout: TObjectLayout) {
		this._layout = {...layout};
	}

	mapFields(mapper: (field: TObjectLayoutColumn) => void) {
		return this._layout.objectLayoutTabs.map(
			({objectLayoutBoxes}: TObjectLayoutTab) => {
				return objectLayoutBoxes.map(({objectLayoutRows}) => {
					return objectLayoutRows.map(({objectLayoutColumns}) => {
						return objectLayoutColumns.map((field) => {
							return field && mapper(field);
						});
					});
				});
			}
		);
	}
}

class BoxesVisitor {
	private _tab: any;

	constructor(tab: TObjectLayoutTab) {
		this.setTab(tab);
	}

	dispose() {
		this._tab = null;
	}

	setTab(tab: TObjectLayoutTab) {
		this._tab = {...tab};
	}

	mapFields(mapper: (field: TObjectLayoutColumn) => void) {
		return this._tab.objectLayoutBoxes.map(
			({objectLayoutRows}: TObjectLayoutBox) => {
				return objectLayoutRows.map(({objectLayoutColumns}) => {
					return objectLayoutColumns.map((field) => {
						return field && mapper(field);
					});
				});
			}
		);
	}
}

class RowsVisitor {
	private _box: any;

	constructor(box: TObjectLayoutBox) {
		this.setBox(box);
	}

	dispose() {
		this._box = null;
	}

	setBox(box: TObjectLayoutBox) {
		this._box = {...box};
	}

	mapFields(mapper: (field: TObjectLayoutColumn) => void) {
		return this._box.objectLayoutRows.map(
			({objectLayoutColumns}: TObjectLayoutRow) => {
				return objectLayoutColumns.map((field) => {
					return field && mapper(field);
				});
			}
		);
	}
}

export {BoxesVisitor, RowsVisitor, TabsVisitor};
