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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {useId} from '../../core/hooks/useId';
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

	const helpTextId = useId();
	const imageDescriptionInputId = useId();
	const tooltipId = useId();

	useEffect(() => {
		if (imageDescriptionInputElement) {
			imageDescriptionInputElement.value = imageDescription;
		}
	}, [imageDescription, imageDescriptionInputElement]);

	return (
		<ClayForm.Group>
			<label
				aria-describedby={tooltipId}
				htmlFor={imageDescriptionInputId}
			>
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
						aria-describedby={helpTextId}
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
