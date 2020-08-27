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
import getAppContext from './Context';
import CreationMenu from './CreationMenu';
import FiltersDropdown from './FiltersDropdown';
import MainSearch from './MainSearch';

function NavBar(props) {
	const {state} = getAppContext();

	return (
		<nav className="management-bar management-bar-light navbar navbar-expand-md">
			<div className="container-fluid container-fluid-max-xl">
				{state.filters.length ? (
					<div className="mr-2 navbar-nav">
						<FiltersDropdown />
					</div>
				) : null}
				{props.showSearch ? (
					<div className="navbar-form navbar-overlay-sm-down pl-0">
						<MainSearch />
					</div>
				) : null}
				<div className="navbar-form navbar-form-autofit navbar-overlay navbar-overlay-sm-down pl-0">
					{props.views && props.views.length > 1 ? (
						<ActiveViewSelector
							activeView={props.activeView}
							setActiveView={props.setActiveView}
							views={props.views}
						/>
					) : null}
				</div>
				{props.creationMenuItems && props.creationMenuItems.length ? (
					<CreationMenu items={props.creationMenuItems} />
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
