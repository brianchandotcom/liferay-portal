/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {ImageSelector} from '../../../common/components/ImageSelector';
import {ImageSelectorSize} from '../../../common/components/ImageSelectorSize';
import MappingSelector from '../../../common/components/MappingSelector';
import {ConfigurationFieldPropTypes} from '../../../prop_types/index';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import {STANDARD_IMAGE_SIZE_LIMIT} from '../../config/constants/standardImageSizeLimit';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {useSelector} from '../../contexts/StoreContext';
import isMapped from '../../utils/editable_value/isMapped';
import isMappedToCollection from '../../utils/editable_value/isMappedToCollection';
import isMappedToInfoItem from '../../utils/editable_value/isMappedToInfoItem';

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

export function ImageSelectorField({field, onValueSelect, value = {}}) {
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
						fileEntryId={value.fileEntryId}
						imageTitle={value.title || value.url}
						label={field.label}
						onClearButtonPressed={() => handleImageChanged({})}
						onImageSelected={handleImageChanged}
					/>

					{value?.fileEntryId && (
						<ImageSelectorSize
							fieldValue={{fileEntryId: value.fileEntryId}}
							imageSizeId="auto"
							imageSizeLimit={STANDARD_IMAGE_SIZE_LIMIT}
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
							imageSizeLimit={STANDARD_IMAGE_SIZE_LIMIT}
						/>
					)}
				</>
			)}
		</>
	);
}

ImageSelectorField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes),
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
};
