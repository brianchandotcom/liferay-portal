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

import {createContext, useContext} from 'react';

const defaultProperties = {
	createSupportRequest: '',
	licenseKeyDownloadURL: '',
	liferaywebdavurl: '',
	oktaSessionURL: '',
	page: '',
	route: '',
	supportLink: '',
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

const useApplicationProvider = () => useContext(ApplicationPropertiesContext);

export {useApplicationProvider};
