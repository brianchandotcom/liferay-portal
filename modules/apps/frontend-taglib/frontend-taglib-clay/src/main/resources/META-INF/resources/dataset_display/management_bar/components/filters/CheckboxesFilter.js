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
import {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function getOdataString(value, key) {
	if (!value || !value.length) {
		return null;
	}

	return `${key}/any(x:${value
		.map(
			(v) =>
				`(x eq ${
					typeof v.value === 'string' ? `'${v.value}'` : v.value
				})`
		)
		.join(' or ')})`;
}

function CheckboxesFilter({actions, id, items, value: valueProp}) {
	const [value, setValue] = useState(valueProp);

	function selectCheckbox(itemValue) {
		if (!value) {
			setValue([itemValue]);
		}
		else if (!value.includes(itemValue)) {
			setValue(value.concat(itemValue));
		}
		else if (value.length === 1) {
			setValue(undefined);
		}
		else {
			setValue(value.filter((v) => v !== itemValue));
		}
	}

	return (
		<>
			{items.map((item, i) => {
				let checked = false;

				if (value) {
					checked = value.reduce(
						(acc, el) => acc || el === item.value,
						false
					);
				}

				return (
					<ClayCheckbox
						aria-label={item.label}
						checked={checked}
						key={i}
						label={item.label}
						onChange={() => selectCheckbox(item.value)}
					/>
				);
			})}
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

CheckboxesFilter.propTypes = {
	id: PropTypes.string.isRequired,
	invisible: PropTypes.bool,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
		})
	),
	label: PropTypes.string.isRequired,
	type: PropTypes.oneOf(['checkbox']).isRequired,
	value: PropTypes.arrayOf(
		PropTypes.oneOfType([PropTypes.string, PropTypes.number])
	),
};

export default CheckboxesFilter;
