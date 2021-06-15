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
import React from 'react';
import {createPortal} from 'react-dom';

interface IProps extends React.HTMLAttributes<HTMLDivElement> {

	/**
	 * Element to render portal into.
	 */
	container?: Element;

	/**
	 * Name of element to wrap content in. Default is
	 * a 'div' element.
	 */
	wrapper?:
		| string
		| React.ComponentType<{
				className: string;
				id?: string;
				ref?: React.Ref<HTMLElement>;
		  }>
		| false;
}

const ReactPortal = React.forwardRef<HTMLElement, IProps>(
	(
		{
			children,
			className,
			container,
			id,
			wrapper: Wrapper = 'div',
			...otherProps
		},
		ref
	) => {
		const cssClass = classNames('lfr-tooltip-scope', className);

		let content;

		if (Wrapper) {
			content = (
				<Wrapper className={cssClass} id={id} ref={ref} {...otherProps}>
					{children}
				</Wrapper>
			);
		}
		else if (
			React.isValidElement(children) &&
			React.Children.only(children)
		) {
			content = React.cloneElement(
				children as React.DetailedReactHTMLElement<any, HTMLElement>,
				{className: classNames(cssClass, children.props.className), id}
			);
		}

		// eslint-disable-next-line @liferay/portal/no-react-dom-create-portal
		return createPortal(content, container || document.body);
	}
);

export default ReactPortal;
