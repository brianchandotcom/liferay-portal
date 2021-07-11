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
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {useId} from '../../app/utils/useId';
import CurrentLanguageFlag from './CurrentLanguageFlag';

export const ImageSelectorDescription = ({
	imageDescription,
	onImageDescriptionChanged,
}) => {
	const [
		imageDescriptionInputElement,
		setImageDescriptionInputElement,
	] = useState();

	const imageDescriptionInputId = useId();

	useEffect(() => {
		if (imageDescriptionInputElement) {
			imageDescriptionInputElement.value = imageDescription;
		}
	}, [imageDescription, imageDescriptionInputElement]);

	return (
		<ClayForm.Group>
			<label htmlFor={imageDescriptionInputId}>
				{Liferay.Language.get('image-description')}
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
};

ImageSelectorDescription.propTypes = {
	imageDescription: PropTypes.string.isRequired,
	onImageDescriptionChanged: PropTypes.func.isRequired,
};
