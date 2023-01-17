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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

const PAGINATION_TYPE_OPTIONS = [
	{label: Liferay.Language.get('none'), value: 'none'},
	{label: Liferay.Language.get('numeric'), value: 'numeric'},
	{label: Liferay.Language.get('simple'), value: 'simple'},
];

export function PaginationSelector({
	collectionPaginationTypeId,
	handleConfigurationChanged,
	value,
}) {
	return (
		<ClayForm.Group small>
			<label htmlFor={collectionPaginationTypeId}>
				{Liferay.Language.get('pagination')}
			</label>

			<ClaySelectWithOption
				aria-label={Liferay.Language.get('pagination')}
				id={collectionPaginationTypeId}
				onChange={(event) =>
					handleConfigurationChanged({
						paginationType: event.target.value,
					})
				}
				options={PAGINATION_TYPE_OPTIONS}
				value={value}
			/>
		</ClayForm.Group>
	);
}

PaginationSelector.propTypes = {
	collectionPaginationTypeId: PropTypes.string.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	value: PropTypes.string.isRequired,
};
