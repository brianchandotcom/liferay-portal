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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ManagementToolbar} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import ViewsContext from '../../views/ViewsContext';
import ActiveViewSelector from './ActiveViewSelector';
import CreationMenu from './CreationMenu';
import CustomViewsControls from './CustomViewsControls';
import FiltersDropdown from './FiltersDropdown';
import MainSearch from './MainSearch';

function NavBar({creationMenu, showSearch}) {
	const [{customViewsEnabled, filters, views}] = useContext(ViewsContext);

	const [showMobile, setShowMobile] = useState(false);

	return (
		<ManagementToolbar.Container className="justify-content-space-between">
			<ManagementToolbar.ItemList>
				{!!filters.length && (
					<ManagementToolbar.Item>
						<FiltersDropdown />
					</ManagementToolbar.Item>
				)}
			</ManagementToolbar.ItemList>

			{showSearch && (
				<>
					<ManagementToolbar.Search
						onSubmit={(event) => {
							event.preventDefault();
						}}
						showMobile={showMobile}
					>
						<MainSearch setShowMobile={setShowMobile} />
					</ManagementToolbar.Search>
				</>
			)}

			<ManagementToolbar.ItemList>
				{showSearch && (
					<ManagementToolbar.Item className="navbar-breakpoint-d-none">
						<ClayButton
							className="nav-link nav-link-monospaced"
							displayType="unstyled"
							onClick={() => setShowMobile(true)}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</ManagementToolbar.Item>
				)}

				{customViewsEnabled && <CustomViewsControls />}

				{views?.length > 1 && (
					<ManagementToolbar.Item>
						<ActiveViewSelector views={views} />
					</ManagementToolbar.Item>
				)}

				{creationMenu && (
					<ManagementToolbar.Item>
						<CreationMenu {...creationMenu} />
					</ManagementToolbar.Item>
				)}
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
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
