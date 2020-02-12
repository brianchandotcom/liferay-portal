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

import React from 'react';

import Breadcrumbs from './breadcrumbs/Breadcrumbs.es';
import MillerColumns from './miller_columns/MillerColumns.es';
import MillerColumnsContext from './miller_columns/MillerColumnsContext.es';

const Layout = ({
	context: {namespace},
	props: {breadcrumbEntries, layoutColumns}
}) => {
	return (
		<>
			<Breadcrumbs entries={breadcrumbEntries} />

			<MillerColumnsContext.Provider value={{namespace}}>
				<MillerColumns columns={layoutColumns} />
			</MillerColumnsContext.Provider>
		</>
	);
};

export default Layout;
