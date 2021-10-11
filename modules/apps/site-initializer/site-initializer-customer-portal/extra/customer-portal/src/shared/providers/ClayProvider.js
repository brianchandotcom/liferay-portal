import { ClayIconSpriteContext } from '@clayui/icon';
import React from 'react';

const getIconSpriteMap = () => {
	try {
		// eslint-disable-next-line no-undef
		if (!themeDisplay) {
			new Error('themeDisplay is not defined');
		}

		// eslint-disable-next-line no-undef
		return `${themeDisplay.getPathThemeImages()}/clay/icons.svg`;
	} catch (error) {
		console.warn(error.message);

		return require('@clayui/css/lib/images/icons/icons.svg').default;
	}
};

const ClayProvider = ({ children }) => {
	return (
		<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
			{children}
		</ClayIconSpriteContext.Provider>
	);
};

export default ClayProvider;