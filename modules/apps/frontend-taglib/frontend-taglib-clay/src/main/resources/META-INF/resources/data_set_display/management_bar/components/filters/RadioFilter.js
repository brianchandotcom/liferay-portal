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
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function getOdataString(value, key) {
	return `${key} eq ${typeof value === 'string' ? `'${value}'` : value}`;
}

function RadioFilter({actions, id, items, value: valueProp}) {
	const [value, setValue] = useState(valueProp);

	return (
		<>
			<ClayRadioGroup
				onSelectedValueChange={setValue}
				selectedValue={value || ''}
			>
				{items.map((item) => (
					<ClayRadio
						key={item.value}
						label={item.label}
						value={item.value}
					/>
				))}
			</ClayRadioGroup>
			<div className="mt-3">
				<ClayButton
					className="btn-sm"
					disabled={value === valueProp}
					onClick={() =>
						actions.updateFilterState(
							id,
							value,
							getOdataString(value, id)
						)
					}
				>
					{valueProp
						? Liferay.Language.get('edit-filter')
						: Liferay.Language.get('add-filter')}
				</ClayButton>
			</div>
		</>
	);
}

RadioFilter.propTypes = {
	id: PropTypes.string.isRequired,
	invisible: PropTypes.bool,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
		})
	),
	label: PropTypes.string.isRequired,
	type: PropTypes.oneOf(['radio']).isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

export default RadioFilter;
