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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const FileNamePicker = ({
	maxFileSize: initialMaxFileSize,
	namespace,
	validExtensions,
}) => {
	const maxFileSize = Number(initialMaxFileSize);
	const inputId = `${namespace}file`;
	const [inputValue, setInputValue] = useState('');
	const [fileName, setFileName] = useState('');
	const [maxFileSizeError, setMaxFileSizeError] = useState(false);

	useEffect(() => {
		setFileName(inputValue ? inputValue.replace(/^.*[\\]/, '') : '');
	}, [inputValue]);

	const onInputChange = ({target}) => {
		if (target.files[0].size > maxFileSize) {
			setMaxFileSizeError(true);
			setInputValue('');
		}
		else {
			setMaxFileSizeError(false);

			setInputValue(target.value);

			window[`${namespace}updateFileNameAndTitle`]();
		}
	};

	return (
		<ClayForm.Group
			className={classNames({
				'has-error': maxFileSizeError,
			})}
		>
			<label className="btn btn-secondary" htmlFor={inputId}>
				{Liferay.Language.get('select-file')}
			</label>

			{fileName && (
				<>
					<small className="inline-item inline-item-after">
						<strong>{fileName}</strong>
					</small>

					<ClayButtonWithIcon
						borderless
						displayType="secondary"
						monospaced
						onClick={() => setInputValue('')}
						symbol="times-circle-full"
					/>
				</>
			)}

			<ClayInput
				accept={validExtensions}
				className="d-none"
				id={inputId}
				name={inputId}
				onChange={onInputChange}
				type="file"
				value={inputValue}
			/>

			{maxFileSizeError && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayIcon className="mr-1" symbol="exclamation-full" />
						{Liferay.Util.sub(
							Liferay.Language.get(
								'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
							),
							Liferay.Util.formatStorage(maxFileSize, {
								addSpaceBeforeSuffix: true,
							})
						)}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
};

FileNamePicker.propTypes = {
	maxFileSize: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
		.isRequired,
	namespace: PropTypes.string.isRequired,
	validExtensions: PropTypes.string.isRequired,
};

export default FileNamePicker;
