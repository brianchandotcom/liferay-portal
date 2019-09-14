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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';
import {PropTypes} from 'prop-types';

/**
 * The search bar at the top of the modal. Provides an input for searching
 * through all results.
 */
const AddResultSearchBar = ({
	onSearchKeyDown,
	onSearchQueryChange,
	onSearchSubmit,
	searchQuery
}) => (
	<div className="add-result-container container-fluid">
		<div className="management-bar navbar-expand-md">
			<div className="navbar-form navbar-form-autofit">
				<div className="input-group">
					<div className="input-group-item">
						<input
							aria-label={Liferay.Language.get(
								'search-the-engine'
							)}
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
	</div>
);

AddResultSearchBar.propTypes = {
	onSearchKeyDown: PropTypes.func.isRequired,
	onSearchQueryChange: PropTypes.func.isRequired,
	onSearchSubmit: PropTypes.func.isRequired,
	searchQuery: PropTypes.string
};

AddResultSearchBar.defaultProps = {
	searchQuery: ''
};

export default AddResultSearchBar;
