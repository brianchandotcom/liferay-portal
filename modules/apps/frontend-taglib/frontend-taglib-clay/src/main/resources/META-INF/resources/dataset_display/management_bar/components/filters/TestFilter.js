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

function getOdataString() {
	return `test ne 4`;
}

function TestFilter({actions, id, inputText, label, value: valueProp}) {
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
						aria-label={label}
						className="form-control"
						onChange={(e) => setValue(e.target.value)}
						type="text"
						value={value || ''}
					/>
				</div>
				<div className="input-group-append input-group-item input-group-item-shrink">
					<span className="input-group-text">
						{Liferay.Language.get('test')}
					</span>
				</div>
			</div>
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
		</div>
	);
}

TestFilter.propTypes = {
	id: PropTypes.string.isRequired,
	invisible: PropTypes.bool,
	label: PropTypes.string.isRequired,
	value: PropTypes.string,
};

export default TestFilter;
