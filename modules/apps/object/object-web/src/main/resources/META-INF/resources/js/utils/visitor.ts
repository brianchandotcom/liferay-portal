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
	TObjectLayoutBox,
	TObjectLayoutColumn,
	TObjectLayoutTab,
} from '../components/layout/types';

class TabsVisitor {
	private _tabs: any;

	constructor(tabs: TObjectLayoutTab[]) {
		this.setTabs(tabs);
	}

	dispose() {
		this._tabs = null;
	}

	setTabs(tabs: TObjectLayoutTab[]) {
		this._tabs = [...tabs];
	}

	mapTabs(mapper: (column: TObjectLayoutColumn) => void) {
		return this._tabs.map((tab: TObjectLayoutTab) => {
			return tab.objectLayoutBoxes.map((box) => {
				return box.objectLayoutRows.map((row) => {
					return row.objectLayoutColumns.map((column) => {
						return mapper(column);
					});
				});
			});
		});
	}
}

class BoxesVisitor {
	private _boxes: any;

	constructor(boxes: TObjectLayoutBox[]) {
		this.setBoxes(boxes);
	}

	dispose() {
		this._boxes = null;
	}

	setBoxes(boxes: TObjectLayoutBox[]) {
		this._boxes = [...boxes];
	}

	mapBoxes(mapper: (column: TObjectLayoutColumn) => void) {
		return this._boxes.map((box: TObjectLayoutBox) => {
			return box.objectLayoutRows.map((row) => {
				return row.objectLayoutColumns.map((column) => {
					return mapper(column);
				});
			});
		});
	}
}

export {BoxesVisitor, TabsVisitor};
