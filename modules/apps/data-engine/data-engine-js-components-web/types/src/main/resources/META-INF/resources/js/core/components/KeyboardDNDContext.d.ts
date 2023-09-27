/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
declare type ITargetPosition = 'bottom' | 'middle' | 'top';
declare type IItemType = 'root' | 'page' | 'row' | 'column' | 'field';
interface IState {
	currentTarget: {
		itemPath: number[];
		itemType: IItemType;
		position: ITargetPosition;
	};
	sourceItem:
		| {
				dragType: 'add';
				fieldType: Record<string, any>;
		  }
		| {
				dragType: 'move';
				fieldName: string;
				itemPath: number[];
				pageIndex: number;
				parentField: Record<string, any>;
		  };
}
export declare function KeyboardDNDContextProvider({
	children,
}: {
	children: ReactNode;
}): JSX.Element;
export declare function useSetSourceItem(): (
	nextSourceItem: IState['sourceItem'] | null
) => void;
export declare function useText(): string | null;
export declare function useIsOverTarget(
	itemPath: number[],
	position: ITargetPosition
): boolean;
export {};
