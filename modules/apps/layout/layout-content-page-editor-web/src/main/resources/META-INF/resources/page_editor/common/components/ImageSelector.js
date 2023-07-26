/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

import {VIEWPORT_SIZES} from '../../app/config/constants/viewportSizes';
import {useSelector} from '../../app/contexts/StoreContext';
import {selectPageContents} from '../../app/selectors/selectPageContents';
import {openImageSelector} from '../openImageSelector';

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

	const selectButtonLabel = sub(
		hasImageTitle
			? Liferay.Language.get('change-x')
			: Liferay.Language.get('select-x'),
		Liferay.Language.get('image')
	);

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
							aria-label={selectButtonLabel}
							displayType="secondary"
							onClick={() =>
								openImageSelector((image) => {
									onImageSelected(image);
								})
							}
							size="sm"
							symbol={hasImageTitle ? 'change' : 'plus'}
							title={selectButtonLabel}
						/>
					</ClayInput.GroupItem>

					{hasImageTitle && (
						<>
							<ClayInput.GroupItem shrink>
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get(
										'clear-selection'
									)}
									displayType="secondary"
									onClick={onClearButtonPressed}
									size="sm"
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
