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

function NavBar({
	activeView,
	creationMenuItems,
	setActiveView,
	showSearch,
	views,
}) {
	const {state} = useAppState();

	return (
		<nav className="management-bar management-bar-light navbar navbar-expand-md">
			<div className="container-fluid container-fluid-max-xl">
				{state.filters.length > 1 ? (
					<div className="mr-2 navbar-nav">
						<FiltersDropdown />
					</div>
				) : null}
				{showSearch ? (
					<div className="navbar-form navbar-overlay-sm-down pl-0">
						<MainSearch />
					</div>
				) : null}
				<div className="navbar-form navbar-form-autofit navbar-overlay navbar-overlay-sm-down pl-0">
					{views?.length > 1 ? (
						<ActiveViewSelector
							activeView={activeView}
							setActiveView={setActiveView}
							views={views}
						/>
					) : null}
				</div>
				{creationMenuItems?.length ? (
					<CreationMenu items={creationMenuItems} />
				) : null}
			</div>
		</nav>
	);
}

NavBar.propTypes = {
	activeView: PropTypes.number,
	creationMenuItems: PropTypes.array,
	setActiveView: PropTypes.func,
	showSearch: PropTypes.bool,
	views: PropTypes.arrayOf(
		PropTypes.shape({
			icon: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	),
};

NavBar.defaultProps = {
	creationMenuItems: [],
	showSearch: true,
};

export default NavBar;
