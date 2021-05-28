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
import type {ImpactValue, Result} from 'axe-core';
export declare const TYPES: {
	readonly CATEGORY_ADD: 'CATEGORY_ADD';
	readonly CATEGORY_REMOVE: 'CATEGORY_REMOVE';
	readonly IMPACT_ADD: 'IMPACT_ADD';
	readonly IMPACT_REMOVE: 'IMPACT_REMOVE';
};
declare type TAction = {
	payload: {
		value: string;
	};
	type: 'CATEGORY_ADD' | 'CATEGORY_REMOVE' | 'IMPACT_ADD' | 'IMPACT_REMOVE';
};
declare type TState = {
	filteredViolations: Array<Result>;
	selectedCategories: Array<String> | [];
	selectedImpact: Array<ImpactValue> | [];
	violations: Array<Result>;
};
export declare const useFilteredViolationsDispatch: () => React.Dispatch<
	TAction
>;
declare type FilteredViolationsContextProviderProps = {
	children: (props: TState) => React.ReactNode;
	value: {
		filteredViolations: Array<Result>;
		violations: Array<Result>;
	};
};
export declare function FilteredViolationsContextProvider({
	children,
	value,
}: FilteredViolationsContextProviderProps): JSX.Element;
export {};
