/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

const SearchForm = ({onChange, tabIndex, value}) => {
	const id = `sidebarAddPanelSearchFormInput${tabIndex}`;

	return (
		<ClayForm.Group className="mb-3" role="search">
			<label className="sr-only" htmlFor={id}>
				{Liferay.Language.get('search-form')}
			</label>

			<ClayInput
				id={id}
				onChange={(event) => {
					onChange(event.target.value);
				}}
				placeholder={`${Liferay.Language.get('search')}...`}
				sizing="sm"
				type="search"
				value={value}
			/>
		</ClayForm.Group>
	);
};

SearchForm.propTypes = {
	onChange: PropTypes.func.isRequired,
	tabIndex: PropTypes.number.isRequired,
	value: PropTypes.string.isRequired,
};

export default SearchForm;
