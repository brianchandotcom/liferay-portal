/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';

import {Liferay} from '../services/liferay/liferay';

const getIconSpriteMap = () => {
	const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();

	const spritemap = pathThemeImages
		? `${pathThemeImages}/clay/icons.svg`
		: // eslint-disable-next-line no-undef
		  require('@clayui/css/lib/images/icons/icons.svg').default;

	return spritemap;
};

const ClayIconProvider: React.FC = ({children}) => (
	<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
		{children}
	</ClayIconSpriteContext.Provider>
);

export default ClayIconProvider;
