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
import ClayDropDown from '@clayui/drop-down';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function getOdataString() {
	return `test ne 4`;
}

function TestFilter({id, inputText, updateFilterState, value: valueProp}) {
	const [value, setValue] = useState(valueProp);

	return (
		<ClayDropDown.Caption>
			<div className="form-group">
				<div className="input-group">
					<div
						className={classNames('input-group-item', {
							'input-group-prepend': inputText,
						})}
					>
						<input
							className="form-control"
							onChange={(event) => setValue(event.target.value)}
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
						disabled={value === valueProp}
						onClick={() =>
							updateFilterState(
								id,
								value,
								value,
								getOdataString(value, id)
							)
						}
						small
					>
						{valueProp
							? Liferay.Language.get('edit-filter')
							: Liferay.Language.get('add-filter')}
					</ClayButton>
				</div>
			</div>
		</ClayDropDown.Caption>
	);
}

TestFilter.propTypes = {
	id: PropTypes.string.isRequired,
	inputText: PropTypes.string,
	updateFilterState: PropTypes.func.isRequired,
	value: PropTypes.string,
};

export default TestFilter;
