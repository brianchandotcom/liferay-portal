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
import React, {useState} from 'react';

import {propertyGroupShape} from '../../utils/types.es';
import CriteriaSidebarCollapse from './CriteriaSidebarCollapse';
import CriteriaSidebarSearchBar from './CriteriaSidebarSearchBar';

export default function CriteriaSidebar({
	onTitleClicked,
	propertyGroups,
	propertyKey,
}) {
	const [searchValue, setSearchValue] = useState('');

	return (
		<div
			aria-label={Liferay.Language.get('segments-contributors-panel')}
			className="criteria-sidebar-root d-flex flex-column position-absolute"
			role="tabpanel"
			tabIndex={-1}
		>
			<div className="sidebar-header">
				{Liferay.Language.get('properties')}
			</div>

			<div className="sidebar-search">
				<CriteriaSidebarSearchBar
					onChange={(value) => setSearchValue(value)}
					searchValue={searchValue}
				/>
			</div>

			<div className="overflow-y-auto position-relative sidebar-collapse">
				<CriteriaSidebarCollapse
					onCollapseClick={onTitleClicked}
					propertyGroups={propertyGroups}
					propertyKey={propertyKey}
					searchValue={searchValue}
				/>
			</div>
		</div>
	);
}

CriteriaSidebar.propTypes = {
	onTitleClicked: PropTypes.func,
	propertyGroups: PropTypes.arrayOf(propertyGroupShape),
	propertyKey: PropTypes.string,
};
