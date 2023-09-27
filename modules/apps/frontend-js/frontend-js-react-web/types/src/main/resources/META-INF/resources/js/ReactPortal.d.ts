/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
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
declare const ReactPortal: React.ForwardRefExoticComponent<
	IProps & React.RefAttributes<HTMLElement>
>;
export default ReactPortal;
