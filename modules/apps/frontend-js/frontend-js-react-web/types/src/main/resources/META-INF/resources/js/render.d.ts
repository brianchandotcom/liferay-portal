/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

/**
 * Wrapper for ReactDOM render that automatically:
 *
 * - Provides commonly-needed context (for example, the Clay spritemap).
 * - Unmounts when portlets are destroyed based on the received
 *   `portletId` value inside `renderData`. If none is passed, the
 *   component will be automatically unmounted before the next navigation.
 *
 * @param {Function|React.Element} renderable Component, or function that returns an Element, to be rendered.
 * @param {object} renderData Data to be passed to the component as props.
 * @param {HTMLElement} container DOM node where the component is to be mounted.
 *
 * The React docs advise not to rely on the render return value, so we
 * don't propagate it.
 *
 * @see https://reactjs.org/docs/react-dom.html#render
 */
export default function render(
	renderable:
		| NonNullable<React.ReactNode>
		| NonNullable<React.ForwardRefExoticComponent<any>>
		| (() => NonNullable<React.ReactNode>),
	renderData: {
		componentId?: string;
		portletId?: string;
		[key: string]: unknown;
	},
	container: Element
): void;
