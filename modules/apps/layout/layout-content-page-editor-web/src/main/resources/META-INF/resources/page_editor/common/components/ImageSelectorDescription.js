/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import CurrentLanguageFlag from './CurrentLanguageFlag';
import {PopoverTooltip} from './PopoverTooltip';

export function ImageSelectorDescription({
	imageDescription,
	onImageDescriptionChanged,
}) {
	const [
		imageDescriptionInputElement,
		setImageDescriptionInputElement,
	] = useState();

	const imageDescriptionInputId = useId();
	const tooltipId = useId();

	useEffect(() => {
		if (imageDescriptionInputElement) {
			imageDescriptionInputElement.value = imageDescription;
		}
	}, [imageDescription, imageDescriptionInputElement]);

	return (
		<ClayForm.Group>
			<label htmlFor={imageDescriptionInputId}>
				<span>{Liferay.Language.get('image-description')}</span>

				<PopoverTooltip
					content={Liferay.Language.get(
						'this-value-is-used-for-alt-text'
					)}
					header={Liferay.Language.get('image-description')}
					id={tooltipId}
					trigger={
						<ClayIcon
							aria-label={Liferay.Language.get('show-more')}
							className="ml-2"
							symbol="question-circle-full"
						/>
					}
				/>
			</label>

			<ClayInput.Group small>
				<ClayInput.GroupItem>
					<ClayInput
						id={imageDescriptionInputId}
						onBlur={(event) => {
							onImageDescriptionChanged(event.target.value);
						}}
						ref={setImageDescriptionInputElement}
						sizing="sm"
						type="text"
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem shrink>
					<CurrentLanguageFlag />
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

ImageSelectorDescription.propTypes = {
	imageDescription: PropTypes.string.isRequired,
	onImageDescriptionChanged: PropTypes.func.isRequired,
};
