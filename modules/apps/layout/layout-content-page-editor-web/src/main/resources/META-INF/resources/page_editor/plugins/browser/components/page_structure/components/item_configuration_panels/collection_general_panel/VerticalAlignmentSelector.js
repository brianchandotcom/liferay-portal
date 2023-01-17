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

const VERTICAL_ALIGNMENT_OPTIONS = [
	{label: Liferay.Language.get('top'), value: 'start'},
	{label: Liferay.Language.get('middle'), value: 'center'},
	{label: Liferay.Language.get('bottom'), value: 'end'},
];

export function VerticalAlignmentSelector({
	collectionVerticalAlignmentId,
	handleConfigurationChanged,
	value,
}) {
	return (
		<ClayForm.Group small>
			<label htmlFor={collectionVerticalAlignmentId}>
				{Liferay.Language.get('vertical-alignment')}
			</label>

			<ClaySelectWithOption
				id={collectionVerticalAlignmentId}
				onChange={(event) => {
					const nextValue = event.target.value;

					handleConfigurationChanged({
						verticalAlignment: nextValue,
					});
				}}
				options={VERTICAL_ALIGNMENT_OPTIONS}
				value={value || ''}
			/>
		</ClayForm.Group>
	);
}

VerticalAlignmentSelector.propTypes = {
	collectionVerticalAlignmentId: PropTypes.string.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	value: PropTypes.string.isRequired,
};
