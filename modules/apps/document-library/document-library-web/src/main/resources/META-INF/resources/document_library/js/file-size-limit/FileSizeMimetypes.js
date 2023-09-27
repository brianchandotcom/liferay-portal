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

import '../../css/file_size_mimetypes.scss';

const NumberErrorMessage = `${Liferay.Language.get(
	'error-colon'
)} ${Liferay.Language.get('please-enter-a-valid-number')}`;

const FileSizeField = ({
	handleAddClick,
	handleRemoveClick,
	index,
	mimeType = '',
	portletNamespace,
	size = '',
}) => {
	const [sizeErrorMessage, setSizeErrorMessage] = useState('');

	return (
		<ClayLayout.Row className="mt-4 size-limit-row">
			<ClayLayout.Col md="6">
				<label htmlFor="mimeType">
					{Liferay.Language.get('mime-type-field-label')}

					<span
						className="inline-item-after"
						title={Liferay.Language.get('mime-type-help-message')}
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
							target.validity.valid && Number(target.value) >= 0
								? ''
								: NumberErrorMessage
						);
					}}
					type="number"
				/>

				{index > 0 && (
					<ClayButton
						aria-label={Liferay.Language.get('remove')}
						className="dm-field-repeatable-delete-button"
						onClick={() => handleRemoveClick(index)}
						small
						title={Liferay.Language.get('remove')}
						type="button"
					>
						<ClayIcon symbol="hr" />
					</ClayButton>
				)}

				<ClayButton
					className="dm-field-repeatable-add-button"
					onClick={() => handleAddClick(index)}
					small
					title={Liferay.Language.get('add')}
					type="button"
				>
					<ClayIcon symbol="plus" />
				</ClayButton>

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
	);
};

const FileSizePerMimeType = ({
	description = Liferay.Language.get('file-size-mime-type-description'),
	portletNamespace,
	sizeList: initialSizeList,
}) => {
	const emptyRow = () => ({id: uuidv4(), mimeType: '', size: ''});

	const addRow = (index) => {
		const tempList = [...sizesList];
		tempList.splice(index + 1, 0, emptyRow());
		setSizesList(tempList);
	};

	const removeRow = (index) => {
		const tempList = [...sizesList];
		tempList.splice(index, 1);
		setSizesList(tempList);
	};

	const [sizesList, setSizesList] = useState(
		initialSizeList && !!initialSizeList.length
			? initialSizeList.map((item) => ({...item, id: uuidv4()}))
			: [emptyRow()]
	);

	return (
		<>
			<p className="mb-4 text-3 text-secondary">
				{Liferay.Language.get(description)}
			</p>

			{sizesList.map((item, index) => (
				<FileSizeField
					handleAddClick={addRow}
					handleRemoveClick={removeRow}
					index={index}
					key={item.id}
					mimeType={item.mimeType}
					portletNamespace={portletNamespace}
					size={item.size}
				/>
			))}
		</>
	);
};

FileSizePerMimeType.propTypes = {
	description: PropTypes.string,
	portletNamespace: PropTypes.string.isRequired,
	sizeList: PropTypes.arrayOf(
		PropTypes.shape({
			mimeType: PropTypes.string,
			size: PropTypes.number,
		})
	),
};

export default FileSizePerMimeType;
