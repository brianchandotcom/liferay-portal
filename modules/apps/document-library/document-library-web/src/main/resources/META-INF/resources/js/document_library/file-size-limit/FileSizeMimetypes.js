/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React, {useState} from 'react';
import {v4 as uuidv4} from 'uuid';

import '../../../document_library/css/file_size_mimetypes.scss';

const NumberErrorMessage = `${Liferay.Language.get(
	'error-colon'
)} ${Liferay.Language.get('please-enter-a-valid-number')}`;

const FileSizeField = ({
	handleRemoveClick,
	index,
	mimeType = '',
	portletNamespace,
	size = '',
}) => {
	const [sizeErrorMessage, setSizeErrorMessage] = useState('');

	return (
		<div className="file-size-mimetypes-item">
			<div className="file-size-mimetypes-item-content">
				<ClayLayout.Row>
					<ClayLayout.Col md="6">
						<label htmlFor="mimeType">
							{Liferay.Language.get('mime-type-field-label')}

							<span
								className="inline-item-after"
								title={Liferay.Language.get(
									'mime-type-help-message'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</label>

						<ClayInput
							defaultValue={mimeType}
							id="mimeType"
							name={`${portletNamespace}mimeType_${index}`}
							type="text"
						/>
					</ClayLayout.Col>

					<ClayLayout.Col
						className={sizeErrorMessage ? 'has-error' : ''}
						md="6"
					>
						<label htmlFor="size">
							{Liferay.Language.get('maximum-file-size')}

							<span
								className="inline-item-after"
								title={Liferay.Language.get(
									'maximum-file-size-help-message'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</label>

						<ClayInput
							defaultValue={size}
							id="size"
							name={`${portletNamespace}size_${index}`}
							onChange={({target}) => {
								setSizeErrorMessage(
									target.validity.valid &&
										Number(target.value) >= 0
										? ''
										: NumberErrorMessage
								);
							}}
							type="number"
						/>

						{sizeErrorMessage && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="exclamation-full" />

									{sizeErrorMessage}
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</ClayLayout.Col>
				</ClayLayout.Row>
			</div>

			{index > 0 && (
				<ClayButton
					aria-label={Liferay.Language.get('remove')}
					borderless
					className="file-size-mimetypes-remove"
					displayType="secondary"
					monospaced
					onClick={() => handleRemoveClick(index)}
					type="button"
				>
					<ClayIcon symbol="trash" />
				</ClayButton>
			)}
		</div>
	);
};

const FileSizeMimetypes = ({
	description = Liferay.Language.get('file-size-mime-type-description'),
	portletNamespace,
	sizeList: initialSizeList,
}) => {
	const emptyRow = () => ({id: uuidv4(), mimeType: '', size: ''});

	const addRow = () => {
		setSizesList((prevSizesList) => [...prevSizesList, emptyRow()]);
	};

	const removeRow = (index) => {
		setSizesList((prevSizesList) => prevSizesList.toSpliced(index, 1));
	};

	const [sizesList, setSizesList] = useState(
		initialSizeList && !!initialSizeList.length
			? initialSizeList.map((item) => ({...item, id: uuidv4()}))
			: [emptyRow()]
	);

	return (
		<>
			<p className="mb-4 text-3 text-secondary">{description}</p>

			{sizesList.map((item, index) => (
				<FileSizeField
					handleRemoveClick={removeRow}
					index={index}
					key={item.id}
					mimeType={item.mimeType}
					portletNamespace={portletNamespace}
					size={item.size}
				/>
			))}

			<ClayButton
				aria-label={Liferay.Language.get('add-option')}
				borderless
				className="file-size-mimetypes-add"
				displayType="secondary"
				onClick={addRow}
				type="button"
			>
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="plus" />
				</span>

				{Liferay.Language.get('add-option')}
			</ClayButton>
		</>
	);
};

FileSizeMimetypes.propTypes = {
	description: PropTypes.string,
	portletNamespace: PropTypes.string.isRequired,
	sizeList: PropTypes.arrayOf(
		PropTypes.shape({
			mimeType: PropTypes.string,
			size: PropTypes.number,
		})
	),
};

export default FileSizeMimetypes;
