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
import React from 'react';

import ActiveViewSelector from './ActiveViewSelector';
import {useAppState} from './Context';
import CreationMenu from './CreationMenu';
import FiltersDropdown from './FiltersDropdown';
import MainSearch from './MainSearch';

function NavBar({creationMenu, showSearch, views}) {
	const {state} = useAppState();

	return (
		<nav className="management-bar management-bar-light navbar navbar-expand-md">
			<div className="container-fluid container-fluid-max-xl">
				{state.filters.length && (
					<div className="mr-2 navbar-nav">
						<FiltersDropdown />
					</div>
				)}
				{showSearch && (
					<div className="navbar-form navbar-overlay-sm-down pl-0">
						<MainSearch />
					</div>
				)}
				<div className="navbar-form navbar-form-autofit navbar-overlay navbar-overlay-sm-down pl-0">
					{views?.length > 1 && <ActiveViewSelector views={views} />}
				</div>
				{creationMenu && <CreationMenu {...creationMenu} />}
			</div>
		</nav>
	);
}

NavBar.propTypes = {
	creationMenu: PropTypes.shape({
		primaryItems: PropTypes.array,
		secondaryItems: PropTypes.array,
	}),
	setActiveView: PropTypes.func,
	showSearch: PropTypes.bool,
	views: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string.isRequired,
			thumbnail: PropTypes.string.isRequired,
		})
	),
};

NavBar.defaultProps = {
	creationMenu: {
		primaryItems: [],
	},
	showSearch: true,
};

export default NavBar;
