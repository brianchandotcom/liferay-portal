import {ClayIconSpriteContext} from '@clayui/icon';
import {Liferay} from '../utils/liferay';

const getIconSpriteMap = () => {
	const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();

	const spritemap = pathThemeImages
		? `${pathThemeImages}/clay/icons.svg`
		: // eslint-disable-next-line no-undef
		  require('@clayui/css/lib/images/icons/icons.svg').default;

	return spritemap;
};

const ClayIconProvider = ({children}) => (
	<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
		{children}
	</ClayIconSpriteContext.Provider>
);

export default ClayIconProvider;
