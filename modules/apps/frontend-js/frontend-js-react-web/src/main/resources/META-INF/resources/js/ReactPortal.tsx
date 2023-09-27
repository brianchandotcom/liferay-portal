/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
