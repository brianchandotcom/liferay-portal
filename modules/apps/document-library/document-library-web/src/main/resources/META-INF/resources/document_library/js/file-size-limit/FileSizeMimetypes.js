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
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const FileSizeField = ({
	handleAddClick,
	handleOnChange,
	handleRemoveClick,
	index,
	mimeType,
	size,
}) => {
	const [mimeTypeValue, setMimeTypeValue] = useState(mimeType);
	const [sizeValue, setSizeValue] = useState(size);

	const onInputBlur = () => {
		if (mimeTypeValue && sizeValue) {
			handleOnChange(mimeTypeValue, sizeValue);
		}
	};

	return (
		<ClayLayout.Row className="mt-3">
			<ClayLayout.Col md="6">
				<label htmlFor="mimeType">
					{Liferay.Language.get('mime-type')}

					<span
						className="inline-item-after lfr-portal-tooltip tooltip-icon"
						title={Liferay.Language.get('mime-type-help-message')}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>

				<ClayInput
					id="mimeType"
					onBlur={onInputBlur}
					onChange={(event) => setMimeTypeValue(event.target.value)}
					type="text"
					value={mimeTypeValue}
				/>
			</ClayLayout.Col>

			<ClayLayout.Col md="6">
				<label htmlFor="size">
					{Liferay.Language.get('maximum-file-size')}

					<span
						className="inline-item-after lfr-portal-tooltip tooltip-icon"
						title={Liferay.Language.get(
							'maximum-file-size-help-message'
						)}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</label>

				<ClayInput
					id="size"
					onBlur={onInputBlur}
					onChange={(event) => setSizeValue(event.target.value)}
					type="number"
					value={sizeValue}
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
					aria-label={Liferay.Language.get('add')}
					className="dm-field-repeatable-duplicate-button"
					onClick={() => handleAddClick(index)}
					small
					title={Liferay.Language.get('duplicate')}
					type="button"
				>
					<ClayIcon symbol="plus" />
				</ClayButton>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
};

const FileSizePerMimeType = ({
	currentSizes,
	description = 'file-size-mimetype-description',
}) => {
	const emptyObj = {mimeType: '', size: ''};

	const saveMimeTypeSizeLimit = (mimeType, size) => {
		console.log('TODO save ' + mimeType + ' ' + size);
	};

	const addRow = (index) => {
		const tempList = [...sizesList];
		tempList.splice(index + 1, 0, emptyObj);
		setSizesList(tempList);
	};

	const removeRow = (index) => {
		const tempList = [...sizesList];
		tempList.splice(index, 1);
		setSizesList(tempList);
	};

	const [sizesList, setSizesList] = useState(currentSizes || [emptyObj]);

	return (
		<>
			<p className="text-muted">{Liferay.Language.get(description)}</p>

			{sizesList.map((item, index) => (
				<FileSizeField
					handleAddClick={addRow}
					handleOnChange={saveMimeTypeSizeLimit}
					handleRemoveClick={removeRow}
					index={index}
					key={index}
					mimeType={item.mimeType}
					size={item.size}
				/>
			))}
		</>
	);
};

FileSizePerMimeType.propTypes = {
	currentSizes: PropTypes.arrayOf(
		PropTypes.shape({
			mimeType: PropTypes.string,
			size: PropTypes.number,
		})
	),
	description: PropTypes.string,
};

export default FileSizePerMimeType;
