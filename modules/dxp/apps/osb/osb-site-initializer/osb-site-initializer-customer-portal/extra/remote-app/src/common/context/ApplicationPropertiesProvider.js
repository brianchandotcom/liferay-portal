import {createContext} from 'react';

const defaultProperties = {
	liferaywebdavurl: '',
	page: '',
	route: '',
	supportLink:
		'https://help.liferay.com/hc/en-us/articles/360018414031-Account-Support',
};

export const ApplicationPropertiesContext = createContext(defaultProperties);

/**
 * @description The Context contains all properties settled on Web Component
 * @param {Object} Props -> Contains object with Properties of Application
 * Inserted from index.html properties for Web Component
 */

const ApplicationContextProvider = ({children, properties}) => (
	<ApplicationPropertiesContext.Provider value={properties}>
		{children}
	</ApplicationPropertiesContext.Provider>
);

export default ApplicationContextProvider;
