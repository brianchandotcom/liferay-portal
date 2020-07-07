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

function getOdataString(value, key) {
	return `${key} eq '${value}'`;
}

function TextFilter({actions, id, inputText, label, value: valueProp}) {
	const [value, setValue] = useState(valueProp);

	let actionType = 'edit';

	if (valueProp && !value) {
		actionType = 'delete';
	}
	else if (!valueProp && value) {
		actionType = 'add';
	}

	return (
		<>
			<ClayDropDown.Caption>
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
						{inputText && (
							<div className="input-group-append input-group-item input-group-item-shrink">
								<span className="input-group-text">
									{inputText}
								</span>
							</div>
						)}
					</div>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={(!value && value) || value !== value}
					onClick={() =>
						actions.updateFilterState(
							id,
							value,
							value,
							getOdataString(value, id)
						)
					}
					small
				>
					{actionType === 'add' && Liferay.Language.get('add-filter')}
					{actionType === 'edit' &&
						Liferay.Language.get('edit-filter')}
					{actionType === 'delete' &&
						Liferay.Language.get('delete-filter')}
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
}

TextFilter.propTypes = {
	id: PropTypes.string.isRequired,
	inputText: PropTypes.string,
	invisible: PropTypes.bool,
	label: PropTypes.string.isRequired,
	type: PropTypes.oneOf(['text']).isRequired,
	value: PropTypes.string,
};

export default TextFilter;
