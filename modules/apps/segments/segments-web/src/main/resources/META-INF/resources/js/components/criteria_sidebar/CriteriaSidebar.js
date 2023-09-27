/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			<div className="c-p-4 overflow-y-auto position-relative sidebar-collapse">
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
