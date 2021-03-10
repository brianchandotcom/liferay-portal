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
import PropTypes from 'prop-types';
import React from 'react';

import {VIEWPORT_SIZES} from '../../app/config/constants/viewportSizes';
import {useSelector} from '../../app/store/index';
import {useId} from '../../app/utils/useId';
import {openImageSelector} from '../../core/openImageSelector';

const DEFAULT_IMAGE_TITLE = '';

export function ImageSelector({
	imageTitle = DEFAULT_IMAGE_TITLE,
	label,
	onClearButtonPressed,
	onImageSelected,
}) {
	const imageTitleId = useId();

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const hasImageTitle = imageTitle && imageTitle !== DEFAULT_IMAGE_TITLE;

	return selectedViewportSize === VIEWPORT_SIZES.desktop ? (
		<>
			<ClayForm.Group>
				<label htmlFor={imageTitleId}>{label}</label>
				<ClayInput.Group>
					<ClayInput
						className="page-editor__item-selector__content-input"
						id={imageTitleId}
						placeholder={Liferay.Util.sub(
							Liferay.Language.get('select-x'),
							Liferay.Language.get('image')
						)}
						readOnly
						sizing="sm"
						value={imageTitle}
					/>
					<ClayButtonWithIcon
						className="ml-2"
						displayType="secondary"
						onClick={() =>
							openImageSelector((image) => {
								onImageSelected(image);
							})
						}
						small
						symbol={hasImageTitle ? 'change' : 'plus'}
						title={Liferay.Util.sub(
							hasImageTitle
								? Liferay.Language.get('change-x')
								: Liferay.Language.get('select-x'),
							Liferay.Language.get('image')
						)}
					/>
					{hasImageTitle && (
						<>
							<ClayButtonWithIcon
								className="ml-2"
								displayType="secondary"
								onClick={onClearButtonPressed}
								small
								symbol="times-circle"
								title={Liferay.Language.get('clear-selection')}
							/>
						</>
					)}
				</ClayInput.Group>
			</ClayForm.Group>
		</>
	) : (
		<ReadOnlyImageInput imageTitle={imageTitle} label={label} />
	);
}

ImageSelector.propTypes = {
	imageTitle: PropTypes.string,
	label: PropTypes.string.isRequired,
	onClearButtonPressed: PropTypes.func.isRequired,
	onImageSelected: PropTypes.func.isRequired,
};

function ReadOnlyImageInput({imageTitle, label}) {
	const readOnlyInputId = useId();

	return (
		<>
			<label htmlFor={readOnlyInputId}>{label}</label>
			<ClayForm.Group small>
				<ClayInput
					className="mb-2"
					disabled
					id={readOnlyInputId}
					readOnly
					value={imageTitle}
				/>
			</ClayForm.Group>
		</>
	);
}

ReadOnlyImageInput.propTypes = {
	imageTitle: PropTypes.string,
	label: PropTypes.string,
};
