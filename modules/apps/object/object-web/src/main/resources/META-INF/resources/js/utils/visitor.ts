/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	TObjectLayout,
	TObjectLayoutBox,
	TObjectLayoutColumn,
	TObjectLayoutRow,
	TObjectLayoutTab,
} from '../components/Layout/types';

class TabsVisitor {
	private _layout: TObjectLayout | null = null;

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
		return this._layout?.objectLayoutTabs.map(
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
	private _tab: TObjectLayoutTab | null = null;

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
		return this._tab?.objectLayoutBoxes.map(
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
	private _box: TObjectLayoutBox | null = null;

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
		return this._box?.objectLayoutRows.map(
			({objectLayoutColumns}: TObjectLayoutRow) => {
				return objectLayoutColumns.map((field) => {
					return field && mapper(field);
				});
			}
		);
	}
}

export {BoxesVisitor, RowsVisitor, TabsVisitor};
