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

import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const PdfPreviewLimit = ({maxLimitSize, namespace, value}) => {
	const [warning, setWarning] = useState(false);
	const [inputValue, setInputValue] = useState(value);

	const onChange = (event) => {
		const value = event.target.value;

		setInputValue(value);

		setWarning(maxLimitSize > 0 && value > maxLimitSize);
	};

	return (
		<>
			<ClayForm.Group className={warning ? 'has-warning' : ''}>
				<label htmlFor={`${namespace}maxNumberOfPages`}>
					{Liferay.Language.get('maximum-number-of-pages')}
				</label>

				<ClayInput
					autoFocus
					className="form-control"
					name={`${namespace}maxNumberOfPages`}
					onChange={onChange}
					type="number"
					value={inputValue}
				/>

				{warning && (
					<ClayForm.FeedbackGroup>
						<ClayForm.FeedbackItem>
							<ClayForm.FeedbackIndicator symbol="info-circle" />

							<span>
								{Liferay.Language.get(
									'this-limit-is-higher-than-system-settings-limit'
								)}
							</span>
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</ClayForm.Group>

			<p className="text-muted">
				{Liferay.Language.get('maximum-number-of-pages-help')}
			</p>
		</>
	);
};

PdfPreviewLimit.propTypes = {
	maxLimitSize: PropTypes.number,
	namespace: PropTypes.string.isRequired,
	value: PropTypes.number,
};

export default PdfPreviewLimit;
