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
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function getOdataString(value, key) {
	return `${key} eq ${value}`;
}

function NumberFilter({actions, id, inputText, max, min, value: valueProp}) {
	const [value, setValue] = useState(valueProp);

	return (
		<div className="form-group">
			<div className="input-group">
				<div
					className={classNames('input-group-item', {
						'input-group-prepend': inputText,
					})}
				>
					<input
						className="form-control"
						max={max}
						min={min}
						onChange={(event) => setValue(event.target.value)}
						type="number"
						value={value || ''}
					/>
				</div>
				{inputText && (
					<div className="input-group-append input-group-item input-group-item-shrink">
						<span className="input-group-text">{inputText}</span>
					</div>
				)}
			</div>
			<div className="mt-3">
				<ClayButton
					className="btn-sm"
					disabled={Number(value) === valueProp}
					onClick={() =>
						actions.updateFilterState(
							id,
							Number(value),
							getOdataString(Number(value, id))
						)
					}
				>
					{valueProp
						? Liferay.Language.get('edit-filter')
						: Liferay.Language.get('add-filter')}
				</ClayButton>
			</div>
		</div>
	);
}

NumberFilter.propTypes = {
	id: PropTypes.string.isRequired,
	inputText: PropTypes.string,
	invisible: PropTypes.bool,
	label: PropTypes.string.isRequired,
	max: PropTypes.number,
	min: PropTypes.number,
	type: PropTypes.oneOf(['number']).isRequired,
	value: PropTypes.number,
};

export default NumberFilter;
