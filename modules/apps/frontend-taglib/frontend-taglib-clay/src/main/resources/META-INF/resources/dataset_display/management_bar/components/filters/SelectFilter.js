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
import {ClaySelect} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import getAppContext from '../Context';

function SelectFilter({id, items, panelType, value: valueProp}) {
	const {actions} = getAppContext();
	const [value, setValue] = useState(valueProp);

	return (
		<>
			<ClaySelect
				aria-label="Select Label"
				id="mySelectId"
				onChange={(event) => setValue(event.target.value)}
				value={value || ''}
			>
				<ClaySelect.Option label={''} value={''} />
				{items.map((item) => (
					<ClaySelect.Option
						key={item.value}
						label={item.label}
						value={item.value}
					/>
				))}
			</ClaySelect>
			<div className="mt-3">
				<ClayButton
					className="btn-sm"
					disabled={value === valueProp}
					onClick={() => actions.updateFilterValue(id, value)}
				>
					{panelType === 'edit'
						? Liferay.Language.get('edit-filter')
						: Liferay.Language.get('add-filter')}
				</ClayButton>
			</div>
		</>
	);
}

SelectFilter.propTypes = {
	id: PropTypes.string.isRequired,
	invisible: PropTypes.bool,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
		})
	),
	label: PropTypes.string.isRequired,
	operator: PropTypes.oneOf([
		'eq',
		'ne',
		'gt',
		'ge',
		'lt',
		'le',
		'and',
		'or',
		'not',
	]).isRequired,
	type: PropTypes.oneOf(['select']).isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

export default SelectFilter;
