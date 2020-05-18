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

import React, {useEffect, useRef} from 'react';

import PageRendererRows from './PageRendererRows.es';

class NoRender extends React.Component {
	shouldComponentUpdate() {
		return false;
	}

	render() {
		const {forwardRef, ...otherProps} = this.props;

		return <div ref={forwardRef} {...otherProps} />;
	}
}

// This is a adapter to maintain compatibility with the previous FieldSet,
// being able to call page renderer rows. This should probably be removed
// by a more friendly implementation when we remove the implementation of
// calling the fields dynamically through soy.

export const PageRendererAdapter = ({
	activePage = 0,
	context,
	editable,
	onBlur,
	onChange,
	onFocus,
	pageIndex = 0,
	rows = [],
	spritemap,
}) => {
	const component = useRef(null);
	const container = useRef(null);

	useEffect(() => {
		if (!component.current && container.current) {
			component.current = new PageRendererRows(
				{
					activePage,
					editable,
					events: {
						fieldBlurred: onBlur,
						fieldEdited: onChange,
						fieldFocused: onFocus,
					},
					pageIndex,
					parentContext: context,
					rows,
					spritemap,
				},
				container.current
			);
		}

		return () => {
			if (component.current) {
				component.current.dispose();
			}
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (component.current) {
			component.current.setState({rows});
		}
	}, [rows]);

	return <NoRender forwardRef={container} />;
};
