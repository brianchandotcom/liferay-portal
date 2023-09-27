/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const PDFPreviewLimit = ({maxLimitSize, namespace, scopeLabel, value}) => {
	const [error, setError] = useState(false);
	const [inputValue, setInputValue] = useState(value);

	const onChange = (event) => {
		const value = parseInt(event.target.value, 10);

		setInputValue(value);

		setError(maxLimitSize > 0 && (value > maxLimitSize || value === 0));
	};

	return (
		<ClayForm.Group className={error ? 'has-error' : ''}>
			<label htmlFor={`${namespace}maxNumberOfPages`}>
				{Liferay.Language.get('maximum-number-of-pages')}
			</label>

			<ClayInput
				aria-label={Liferay.Language.get('maximum-number-of-pages')}
				autoFocus
				className="form-control"
				min={0}
				name={`${namespace}maxNumberOfPages`}
				onChange={onChange}
				type="number"
				value={inputValue}
			/>

			{error && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="info-circle" />

						<span>
							{sub(
								Liferay.Language.get(
									'this-limit-is-higher-than-x-limit-enter-maximum-number-of-pages-x'
								),
								scopeLabel,
								maxLimitSize
							)}
						</span>
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
};

PDFPreviewLimit.propTypes = {
	maxLimitSize: PropTypes.number,
	namespace: PropTypes.string.isRequired,
	scopeLabel: PropTypes.string,
	value: PropTypes.number,
};

export default PDFPreviewLimit;
