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

import classNames from 'classnames';
import React, {useReducer} from 'react';

const initialState = {active: null, hover: null};

const TopperContext = React.createContext([initialState, () => {}]);

const TOPPER_ACTIVE = 'ACTIVE';
const TOPPER_HOVER = 'HOVER';

const reducer = (state, action) => {
	switch (action.type) {
		case TOPPER_HOVER:
			return {
				...state,
				hover: action.payload
			};
		case TOPPER_ACTIVE:
			return {
				...state,
				active: action.payload
			};
		default:
			break;
	}
};

const TopperProvider = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	return (
		<TopperContext.Provider value={[state, dispatch]}>
			{children}
		</TopperContext.Provider>
	);
};

const TopperListItem = React.forwardRef(
	({children, className, expand, isDragHandler, isTitle, ...props}, ref) => (
		<li
			{...props}
			className={classNames(
				'fragments-editor__topper__item tbar-item',
				className,
				{
					'fragments-editor__drag-handler': isDragHandler,
					'fragments-editor__topper__title': isTitle,
					'tbar-item-expand': expand
				}
			)}
			ref={ref}
		>
			{children}
		</li>
	)
);

const Topper = ({children}) => (
	<div className="fragments-editor__topper tbar">
		<ul className="tbar-nav">{children}</ul>
	</div>
);

Topper.Item = TopperListItem;

export {TopperProvider, TopperContext, TOPPER_ACTIVE, TOPPER_HOVER};
export default Topper;
