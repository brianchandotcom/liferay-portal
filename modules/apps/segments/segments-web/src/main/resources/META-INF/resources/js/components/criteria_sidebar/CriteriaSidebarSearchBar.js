/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

export default function CriteriaSidebarSearchBar({onChange, searchValue}) {
	const handleClear = (event) => {
		event.preventDefault();
		onChange('');
	};

	return (
		<div className="input-group">
			<div className="input-group-item">
				<input
					aria-label={Liferay.Language.get('search-properties')}
					className="form-control input-group-inset input-group-inset-after"
					data-testid="search-input"
					onChange={(event) => onChange(event.target.value)}
					placeholder={Liferay.Language.get('search-properties')}
					type="text"
					value={searchValue}
				/>

				<div className="input-group-inset-item input-group-inset-item-after">
					<ClayButton
						aria-label={
							searchValue
								? Liferay.Language.get('clear-search')
								: Liferay.Language.get('search-properties')
						}
						data-testid="search-button"
						displayType="unstyled"
						onClick={searchValue ? handleClear : undefined}
						title={
							searchValue
								? Liferay.Language.get('clear-search')
								: Liferay.Language.get('search-properties')
						}
					>
						<ClayIcon symbol={searchValue ? 'times' : 'search'} />
					</ClayButton>
				</div>
			</div>
		</div>
	);
}

CriteriaSidebarSearchBar.propTypes = {
	onChange: PropTypes.func.isRequired,
	searchValue: PropTypes.string,
};
