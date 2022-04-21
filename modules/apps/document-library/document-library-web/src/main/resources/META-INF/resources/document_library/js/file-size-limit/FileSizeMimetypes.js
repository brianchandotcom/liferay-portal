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
import React, {useState} from 'react';

const FileSizeField = ({handleOnChange, mimeType, size}) => {
	const [mimeTypeValue, setMimeTypeValue] = useState(mimeType);
	const [sizeValue, setSizeValue] = useState(size);

	const onInputBlur = () => {
		if (mimeTypeValue && sizeValue) {
			handleOnChange(mimeTypeValue, sizeValue);
		}
	};

	return (
		<ClayLayout.Row>
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

				<ClayButton
					aria-label={Liferay.Language.get('remove')}
					className="dm-field-repeatable-delete-button"
					small
					title={Liferay.Language.get('remove')}
					type="button"
				>
					<ClayIcon symbol="hr" />
				</ClayButton>

				<ClayButton
					aria-label={Liferay.Language.get('add')}
					className="dm-field-repeatable-duplicate-button"
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
	description = 'file-size-mimetype-description',
}) => {
	const addMimeTypeSizeLimit = (mimeType, size) => {
		console.log('TODO save ' + mimeType + ' ' + size);
	};

	return (
		<>
			<p className="text-muted">{Liferay.Language.get(description)}</p>

			<FileSizeField handleOnChange={addMimeTypeSizeLimit} />
		</>
	);
};

export default FileSizePerMimeType;
