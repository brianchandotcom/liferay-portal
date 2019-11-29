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

import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';
import ReactDOM from 'react-dom';

let counter = 0;

/**
 * Wrapper for ReactDOM render that automatically:
 *
 * - Provides commonly-needed context (for example, the Clay spritemap).
 * - Unmounts when portlets are destroyed based on the received
 *   `portletId` value inside renderData. If none is passed, the
 *   component will be automatically unmounted before the next navigation.
 *
 * The React docs advise not to rely on the render return value, so we
 * don't propagate it.
 *
 * @see https://reactjs.org/docs/react-dom.html#render
 */
export default function render(renderFunction, renderData, container) {
	if (!Liferay.SPA || Liferay.SPA.app) {
		const {portletId} = renderData;
		const spritemap =
			Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg';

		let {componentId} = renderData;

		const destroyOnNavigate = !portletId;

		if (!componentId) {
			componentId = `__UNNAMED_COMPONENT__${portletId}__${counter++}`;
		}

		Liferay.component(
			componentId,
			{
				destroy: () => {
					ReactDOM.unmountComponentAtNode(container);
				}
			},
			{
				destroyOnNavigate,
				portletId
			}
		);

		// eslint-disable-next-line liferay-portal/no-react-dom-render
		ReactDOM.render(
			<ClayIconSpriteContext.Provider value={spritemap}>
				{renderFunction(renderData)}
			</ClayIconSpriteContext.Provider>,
			container
		);
	}
	else {
		Liferay.once('SPAReady', () => {
			render(renderFunction, renderData, container);
		});
	}
}
