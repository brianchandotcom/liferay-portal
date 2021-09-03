import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';
import {FormProvider, useForm} from 'react-hook-form';

import {Template} from './components/Template';
import {AppProvider} from './context/AppContext';

const getIconSpriteMap = () => {
	try {
		// eslint-disable-next-line no-undef
		if (!themeDisplay) {
			new Error('themeDisplay is not defined');
		}

		// eslint-disable-next-line no-undef
		return `${themeDisplay.getPathThemeImages()}/clay/icons.svg`;
	}
	catch (erorr) {
		console.warn(erorr.message);

		return require('@clayui/css/lib/images/icons/icons.svg').default;
	}
};

export const Providers = ({children}) => {
	const form = useForm({mode: 'onChange'});

	return (
		<AppProvider>
			<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
				<FormProvider {...form}>
					<Template>{children}</Template>
				</FormProvider>
			</ClayIconSpriteContext.Provider>
		</AppProvider>
	);
};
