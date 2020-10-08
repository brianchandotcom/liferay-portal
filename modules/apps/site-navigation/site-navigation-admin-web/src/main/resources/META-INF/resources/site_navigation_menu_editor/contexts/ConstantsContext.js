/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import PropTypes from 'prop-types';
import React, {useContext} from 'react';

const ConstantsContext = React.createContext({});

export const useConstants = () => useContext(ConstantsContext);

export const ConstantsProvider = ({children, constants}) => (
	<ConstantsContext.Provider value={constants}>
		{children}
	</ConstantsContext.Provider>
);

ConstantsProvider.propTypes = {
	constants: PropTypes.shape({
		editSiteNavigationMenuItemURL: PropTypes.string,
		editSiteNavigationMenuSettingsURL: PropTypes.string,
		portletId: PropTypes.string,
		redirect: PropTypes.string,
		siteNavigationMenuId: PropTypes.string,
		siteNavigationMenuItems: PropTypes.arrayOf(
			PropTypes.shape({
				children: PropTypes.array.isRequired,
				siteNavigationMenuItemId: PropTypes.string.isRequired,
			}).isRequired
		),
		siteNavigationMenuName: PropTypes.string,
	}),
};
