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

import {EVPComponentType} from '../../main';
import {Liferay} from '../services/liferay/liferay';

const getIconSpriteMap = () => {
	const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();

	const spritemap = pathThemeImages
		? `${pathThemeImages}/clay/icons.svg`
		: // eslint-disable-next-line no-undef
		  require('@clayui/css/lib/images/icons/icons.svg').default;

	return spritemap;
};

const ClayIconProvider: React.FC<EVPComponentType> = ({children}) => (
	<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
		{children}
	</ClayIconSpriteContext.Provider>
);

export default ClayIconProvider;
