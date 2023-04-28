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
import {ReactNode} from 'react';

import {Liferay} from '../../../services/liferay';

const getIconSpriteMap = () => {
	const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();
	const spritemap = `${pathThemeImages}/clay/icons.svg`;

	return spritemap;
};

interface IProps {
	children: ReactNode;
}

const ClayIconProvider = ({children}: IProps) => {
	return (
		<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
			{children}
		</ClayIconSpriteContext.Provider>
	);
};

export default ClayIconProvider;
