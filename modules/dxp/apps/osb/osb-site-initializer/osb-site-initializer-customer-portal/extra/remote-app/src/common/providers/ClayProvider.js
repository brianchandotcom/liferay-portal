/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';

export function getIconSpriteMap() {
	try {
		// eslint-disable-next-line no-undef
		if (!Liferay.ThemeDisplay) {
			new Error('themeDisplay is not defined');
		}

		// eslint-disable-next-line no-undef
		return `${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg`;
	}
	catch (error) {
		console.warn(error.message);

		// eslint-disable-next-line no-undef
		return require('@clayui/css/lib/images/icons/icons.svg').default;
	}
}

const ClayProvider = ({children}) => {
	return (
		<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
			{children}
		</ClayIconSpriteContext.Provider>
	);
};

export default ClayProvider;
