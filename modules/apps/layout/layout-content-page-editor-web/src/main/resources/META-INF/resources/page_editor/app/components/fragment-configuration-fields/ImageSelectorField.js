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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {ImageSelector} from '../../../common/components/ImageSelector';
import {ImageSelectorSize} from '../../../common/components/ImageSelectorSize';
import MappingSelector from '../../../common/components/MappingSelector';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {useSelector} from '../../contexts/StoreContext';
import isMapped from '../../utils/editable-value/isMapped';
import isMappedToCollection from '../../utils/editable-value/isMappedToCollection';
import isMappedToInfoItem from '../../utils/editable-value/isMappedToInfoItem';
import {useId} from '../../utils/useId';

const IMAGE_SOURCES = {
	direct: {
		label: Liferay.Language.get('direct'),
		value: 'direct',
	},

	mapping: {
		label: Liferay.Language.get('mapping'),
		value: 'mapping',
	},
};

export const ImageSelectorField = ({field, onValueSelect, value = {}}) => {
	const imageSourceInputId = useId();

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const [imageSource, setImageSource] = useState(() =>
		isMapped(value)
			? IMAGE_SOURCES.mapping.value
			: IMAGE_SOURCES.direct.value
	);

	const handleImageChanged = (image) => {
		onValueSelect(field.name, image);
	};

	const handleSourceChanged = (event) => {
		setImageSource(event.target.value);

		if (Object.keys(value).length) {
			handleImageChanged({});
		}
	};

	return (
		<>
			{selectedViewportSize === VIEWPORT_SIZES.desktop && (
				<ClayForm.Group small>
					<label htmlFor={imageSourceInputId}>
						{Liferay.Language.get('image-source')}
					</label>

					<ClaySelectWithOption
						id={imageSourceInputId}
						onChange={handleSourceChanged}
						options={Object.values(IMAGE_SOURCES)}
						value={imageSource}
					/>
				</ClayForm.Group>
			)}

			{imageSource === IMAGE_SOURCES.direct.value ? (
				<>
					<ImageSelector
						imageTitle={value.title}
						label={field.label}
						onClearButtonPressed={() => handleImageChanged({})}
						onImageSelected={handleImageChanged}
					/>

					{value?.fileEntryId && (
						<ImageSelectorSize
							fieldValue={{fileEntryId: value.fileEntryId}}
							imageSizeId="auto"
						/>
					)}
				</>
			) : (
				<>
					{selectedViewportSize === VIEWPORT_SIZES.desktop ? (
						<MappingSelector
							fieldType={EDITABLE_TYPES.backgroundImage}
							mappedItem={value}
							onMappingSelect={handleImageChanged}
						/>
					) : null}

					{(value?.fileEntryId ||
						isMappedToInfoItem(value) ||
						isMappedToCollection(value)) && (
						<ImageSelectorSize
							fieldValue={value}
							imageSizeId="auto"
						/>
					)}
				</>
			)}
		</>
	);
};

ImageSelectorField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes),
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
};
