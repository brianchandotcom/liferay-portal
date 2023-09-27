/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {formatStorage, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

const FileNamePicker = ({
	maxFileSize: initialMaxFileSize,
	maxMimeTypeSize,
	namespace,
	validExtensions,
}) => {
	const [maxFileSize, setMaxFileSize] = useState(Number(initialMaxFileSize));
	const inputId = `${namespace}file`;
	const [inputValue, setInputValue] = useState('');
	const [fileName, setFileName] = useState('');
	const [maxFileSizeError, setMaxFileSizeError] = useState(false);
	const inputFileRef = useRef();

	useEffect(() => {
		setFileName(inputValue ? inputValue.replace(/^.*[\\]/, '') : '');
	}, [inputValue]);

	const onInputChange = ({target}) => {
		const fileType = target.files[0]?.type;
		const maxFileTypeSize = Number(maxMimeTypeSize[fileType]);
		let maxSize = Number(initialMaxFileSize);

		if (maxFileTypeSize && maxSize > maxFileTypeSize) {
			maxSize = maxFileTypeSize;
		}

		setMaxFileSize(maxSize);

		if (target.files[0]?.size > maxSize) {
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
			<ClayButton
				displayType="secondary"
				onClick={() => inputFileRef.current?.click()}
				title={Liferay.Language.get('select-file')}
			>
				{Liferay.Language.get('select-file')}
			</ClayButton>

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
				ref={inputFileRef}
				type="file"
				value={inputValue}
			/>

			{maxFileSizeError && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayIcon className="mr-1" symbol="exclamation-full" />

						{sub(
							Liferay.Language.get(
								'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
							),
							formatStorage(maxFileSize, {
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
	maxMimeTypeSize: PropTypes.objectOf(
		PropTypes.oneOfType([PropTypes.number, PropTypes.string])
	).isRequired,
	namespace: PropTypes.string.isRequired,
	validExtensions: PropTypes.string.isRequired,
};

export default FileNamePicker;
