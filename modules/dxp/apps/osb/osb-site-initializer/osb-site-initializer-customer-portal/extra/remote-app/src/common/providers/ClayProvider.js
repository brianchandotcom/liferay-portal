import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';

const getIconSpriteMap = () => {
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
};

const ClayProvider = ({children}) => {
	return (
		<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
			{children}
		</ClayIconSpriteContext.Provider>
	);
};

export default ClayProvider;
