/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {PropTypes} from 'prop-types';
import React from 'react';

/**
 * The search bar at the top of the modal. Provides an input for searching
 * through all results.
 */
const AddResultSearchBar = ({
	onSearchKeyDown,
	onSearchQueryChange,
	onSearchSubmit,
	searchQuery,
}) => (
	<ClayLayout.ContainerFluid className="add-result-container">
		<div className="management-bar navbar-expand-md">
			<div className="navbar-form navbar-form-autofit">
				<div className="input-group">
					<div className="input-group-item">
						<input
							aria-label={Liferay.Language.get(
								'search-the-engine'
							)}
							autoFocus={true}
							className="form-control input-group-inset input-group-inset-after"
							onChange={onSearchQueryChange}
							onKeyDown={onSearchKeyDown}
							placeholder={Liferay.Language.get(
								'search-the-engine'
							)}
							type="text"
							value={searchQuery}
						/>

						<div className="input-group-inset-item input-group-inset-item-after">
							<ClayButton
								aria-label={Liferay.Language.get('search')}
								displayType="unstyled"
								onClick={onSearchSubmit}
							>
								<ClayIcon symbol="search" />
							</ClayButton>
						</div>
					</div>
				</div>
			</div>
		</div>
	</ClayLayout.ContainerFluid>
);

AddResultSearchBar.propTypes = {
	onSearchKeyDown: PropTypes.func.isRequired,
	onSearchQueryChange: PropTypes.func.isRequired,
	onSearchSubmit: PropTypes.func.isRequired,
	searchQuery: PropTypes.string,
};

AddResultSearchBar.defaultProps = {
	searchQuery: '',
};

export default AddResultSearchBar;
