/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	TObjectLayout,
	TObjectLayoutBox,
	TObjectLayoutColumn,
	TObjectLayoutTab,
} from '../components/Layout/types';
declare class TabsVisitor {
	private _layout;
	constructor(layout: TObjectLayout);
	dispose(): void;
	setLayout(layout: TObjectLayout): void;
	mapFields(
		mapper: (field: TObjectLayoutColumn) => void
	): void[][][][] | undefined;
}
declare class BoxesVisitor {
	private _tab;
	constructor(tab: TObjectLayoutTab);
	dispose(): void;
	setTab(tab: TObjectLayoutTab): void;
	mapFields(
		mapper: (field: TObjectLayoutColumn) => void
	): void[][][] | undefined;
}
declare class RowsVisitor {
	private _box;
	constructor(box: TObjectLayoutBox);
	dispose(): void;
	setBox(box: TObjectLayoutBox): void;
	mapFields(
		mapper: (field: TObjectLayoutColumn) => void
	): void[][] | undefined;
}
export {BoxesVisitor, RowsVisitor, TabsVisitor};
