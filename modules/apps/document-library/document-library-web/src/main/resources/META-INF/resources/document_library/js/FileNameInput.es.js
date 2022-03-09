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
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const RequiredMark = () => (
	<>
		<span className="inline-item-after reference-mark text-warning">
			<ClayIcon symbol="asterisk" />
		</span>
		<span className="sr-only">{Liferay.Language.get('required')}</span>
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

const FileNameInput = ({initialValue, portletNamespace, required, visible}) => {
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
				type={visible ? 'text' : 'hidden'}
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
	visible: PropTypes.bool,
};

export default FileNameInput;
