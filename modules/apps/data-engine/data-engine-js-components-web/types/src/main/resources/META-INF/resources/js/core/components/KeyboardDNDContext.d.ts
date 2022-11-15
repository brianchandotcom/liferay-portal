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
