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
import {useSelector} from '../../app/contexts/StoreContext';
import {selectPageContents} from '../../app/selectors/selectPageContents';
import {useId} from '../../app/utils/useId';
import {openImageSelector} from '../../core/openImageSelector';

export function ImageSelector({
	fileEntryId,
	imageTitle = '',
	label,
	onClearButtonPressed,
	onImageSelected,
}) {
	const imageTitleId = useId();

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const pageContents = useSelector(selectPageContents);

	const selectedImageTitle =
		pageContents.find((pageContent) => pageContent.classPK === fileEntryId)
			?.title ?? imageTitle;

	const hasImageTitle = !!imageTitle.length;

	return selectedViewportSize === VIEWPORT_SIZES.desktop ? (
		<>
			<ClayForm.Group>
				<label htmlFor={imageTitleId}>{label}</label>

				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<ClayInput
							className="page-editor__item-selector__content-input"
							id={imageTitleId}
							onClick={() =>
								openImageSelector((image) => {
									onImageSelected(image);
								})
							}
							placeholder={Liferay.Language.get('select-image')}
							readOnly
							sizing="sm"
							value={selectedImageTitle}
						/>
					</ClayInput.GroupItem>
					<ClayInput.GroupItem shrink>
						<ClayButtonWithIcon
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
					</ClayInput.GroupItem>
					{hasImageTitle && (
						<>
							<ClayInput.GroupItem shrink>
								<ClayButtonWithIcon
									displayType="secondary"
									onClick={onClearButtonPressed}
									small
									symbol="times-circle"
									title={Liferay.Language.get(
										'clear-selection'
									)}
								/>
							</ClayInput.GroupItem>
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
	fileEntryId: PropTypes.string,
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
