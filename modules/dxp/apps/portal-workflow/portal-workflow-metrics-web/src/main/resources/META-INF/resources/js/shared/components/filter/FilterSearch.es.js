/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import React from 'react';

const FilterSearch = ({
	children,
	filteredItems,
	onChange,
	searchTerm,
	totalCount,
}) => {
	const emptyResults = !filteredItems.length;
	const searchEnabled = totalCount > 12;

	return (
		<>
			{searchEnabled && (
				<div className="dropdown-section">
					<div className="input-group input-group-sm">
						<div className="input-group-item">
							<input
								className="form-control input-group-inset input-group-inset-after"
								onChange={onChange}
								placeholder={Liferay.Language.get('search-for')}
								type="text"
								value={searchTerm}
							/>

							<span className="input-group-inset-item input-group-inset-item-after">
								<span className="ml-2 mr-2">
									<ClayIcon symbol="search" />
								</span>
							</span>
						</div>
					</div>
				</div>
			)}

			{emptyResults && (
				<ClayList className="list-unstyled">
					<ClayList.Item>
						<span className="disabled dropdown-item">
							{Liferay.Language.get('no-results-were-found')}
						</span>
					</ClayList.Item>
				</ClayList>
			)}

			{children}
		</>
	);
};

export {FilterSearch};
