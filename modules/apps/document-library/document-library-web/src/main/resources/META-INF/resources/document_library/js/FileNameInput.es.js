/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const RequiredMark = () => (
	<>
		<span className="inline-item-after reference-mark text-warning">
			<ClayIcon symbol="asterisk" />
		</span>
		<span className="hide-accessible sr-only">
			{Liferay.Language.get('required')}
		</span>
	</>
);

const Feedback = ({message, warning}) => (
	<ClayForm.FeedbackGroup>
		<ClayForm.FeedbackItem>
			{warning && <ClayIcon className="mr-1" symbol="warning-full" />}

			{message}
		</ClayForm.FeedbackItem>
	</ClayForm.FeedbackGroup>
);

const FileNameInput = ({initialValue, portletNamespace, required}) => {
	const inputId = portletNamespace + 'fileName';
	const [inputValue, setInputValue] = useState(initialValue);
	const valueChanged = initialValue !== inputValue;

	const showWarning = required ? valueChanged && inputValue : valueChanged;
	const showError = required && !inputValue;

	return (
		<ClayForm.Group
			className={classNames({
				'has-error': showError,
				'has-warning': showWarning,
			})}
		>
			<label htmlFor={inputId}>
				{Liferay.Language.get('file-name')}

				{required && <RequiredMark />}
			</label>

			<ClayInput
				className="form-control"
				id={inputId}
				name={inputId}
				onChange={({target: {value}}) => setInputValue(value)}
				required={required}
				type="text"
				value={inputValue}
			/>

			{showError && (
				<Feedback
					message={Liferay.Language.get('this-field-is-required')}
				/>
			)}

			{showWarning && (
				<Feedback
					message={Liferay.Language.get(
						'warning-changing-file-name-will-affect-existing-links-to-this-document'
					)}
					warning
				/>
			)}
		</ClayForm.Group>
	);
};

FileNameInput.propTypes = {
	initialValue: PropTypes.string,
	portletNamespace: PropTypes.string,
	required: PropTypes.bool,
};

export default FileNameInput;
