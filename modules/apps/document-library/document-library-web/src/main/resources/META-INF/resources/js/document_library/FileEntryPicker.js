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
	const inputId = `${namespace}file`;
	const [inputValue, setInputValue] = useState('');
	const [fileName, setFileName] = useState('');
	const [maxFileSizeErrorMessage, setMaxFileSizeErrorMessage] = useState('');
	const inputFileRef = useRef();

	useEffect(() => {
		setFileName(inputValue ? inputValue.replace(/^.*[\\]/, '') : '');
	}, [inputValue]);

	const onInputChange = ({target}) => {
		const fileType = target.files[0]?.type;
		const maxFileTypeSize = Number(maxMimeTypeSize[fileType]);
		let isMaxFileTypeSize = false;
		let maxSize = Number(initialMaxFileSize);

		if (maxFileTypeSize && maxSize > maxFileTypeSize) {
			maxSize = maxFileTypeSize;
			isMaxFileTypeSize = true;
		}

		if (target.files[0]?.size > maxSize) {
			const formattedMaxSize = formatStorage(maxSize, {
				addSpaceBeforeSuffix: true,
			});

			setMaxFileSizeErrorMessage(
				isMaxFileTypeSize
					? sub(
							Liferay.Language.get(
								'please-enter-a-file-with-a-valid-file-size-no-larger-than-x-for-type-x'
							),
							[formattedMaxSize, fileType]
						)
					: sub(
							Liferay.Language.get(
								'please-enter-a-file-with-a-valid-file-size-no-larger-than-x'
							),
							formattedMaxSize
						)
			);

			setInputValue('');
		}
		else {
			setMaxFileSizeErrorMessage('');
			setInputValue(target.value);

			window[`${namespace}updateFileNameAndTitle`]();
		}
	};

	return (
		<ClayForm.Group
			className={classNames({
				'has-error': !!maxFileSizeErrorMessage,
			})}
		>
			<ClayButton
				data-qa-id="selectFileButton"
				displayType="secondary"
				onClick={() => inputFileRef.current?.click()}
				title={Liferay.Language.get('select-file')}
			>
				{Liferay.Language.get('select-file')}
			</ClayButton>

			{fileName && (
				<>
					<small
						className="inline-item inline-item-after"
						data-qa-id="uploadedFileName"
					>
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

			{maxFileSizeErrorMessage && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayIcon className="mr-1" symbol="exclamation-full" />

						{maxFileSizeErrorMessage}
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
