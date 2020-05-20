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

import ColorPalette from '../../../common/components/ColorPalette';
import FormRow from '../../../common/components/FormRow';
import {ImageSelector} from '../../../common/components/ImageSelector';
import MappingSelector from '../../../common/components/MappingSelector';
import {
	BackgroundImagePropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../config/constants/layoutDataItemDefaultConfigurations';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {config} from '../../config/index';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateItemConfig from '../../thunks/updateItemConfig';
import {useId} from '../../utils/useId';

const CONTAINER_PADDING_LABELS = {
	paddingBottom: Liferay.Language.get('padding-bottom'),
	paddingHorizontal: Liferay.Language.get('padding-horizontal'),
	paddingLeft: Liferay.Language.get('padding-left'),
	paddingRight: Liferay.Language.get('padding-right'),
	paddingTop: Liferay.Language.get('padding-top'),
};

const CONTAINER_TYPE_LABELS = {
	fixed: Liferay.Language.get('fixed-width'),
	fluid: Liferay.Language.get('fluid'),
};

export const ContainerConfigurationPanel = ({item}) => {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const containerPaddingId = useId();
	const containerTypeId = useId();

	const containerConfig = {
		...LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[
			LAYOUT_DATA_ITEM_TYPES.container
		],
		...item.config,
	};

	const handleConfigurationValueChanged = (itemConfig) => {
		dispatch(
			updateItemConfig({
				itemConfig,
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);
	};

	const paddingIdentifiers =
		containerConfig.type === 'fluid'
			? ['paddingTop', 'paddingBottom', 'paddingHorizontal']
			: ['paddingTop', 'paddingBottom'];

	return (
		<ClayForm.Group small>
			<ClayForm.Group>
				<p className="mb-3 sheet-subtitle">
					{Liferay.Language.get('layout')}
				</p>

				<ClayForm.Group>
					<label htmlFor={containerTypeId}>
						{Liferay.Language.get('container')}
					</label>

					<ClaySelectWithOption
						id={containerTypeId}
						onChange={({target: {value}}) => {
							handleConfigurationValueChanged({
								paddingHorizontal:
									value === 'fixed'
										? containerConfig.paddingHorizontal
										: LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS
												.container.paddingHorizontal,
								type: value,
							});
						}}
						options={Object.entries(
							CONTAINER_TYPE_LABELS
						).map(([value, label]) => ({label, value}))}
						value={containerConfig.type}
					/>
				</ClayForm.Group>

				<FormRow>
					{Object.values(paddingIdentifiers).map(
						(configurationKey) => {
							const inputId = `${containerPaddingId}${configurationKey}`;
							const label =
								CONTAINER_PADDING_LABELS[configurationKey];
							const value = String(
								containerConfig[configurationKey]
							);

							const handleChange = ({target: {value}}) =>
								handleConfigurationValueChanged({
									[configurationKey]: Number(value),
								});

							return (
								<FormRow.Column key={configurationKey}>
									<label htmlFor={inputId}>{label}</label>
									<ClaySelectWithOption
										id={inputId}
										onChange={handleChange}
										options={config.paddingOptions}
										value={value}
									/>
								</FormRow.Column>
							);
						}
					)}
				</FormRow>
			</ClayForm.Group>

			<ClayForm.Group>
				<p className="mb-3 sheet-subtitle">
					{Liferay.Language.get('background-color')}
				</p>
				<ColorPalette
					onClear={() =>
						handleConfigurationValueChanged({
							backgroundColorCssClass: '',
						})
					}
					onColorSelect={(value) =>
						handleConfigurationValueChanged({
							backgroundColorCssClass: value,
						})
					}
					selectedColor={containerConfig.backgroundColorCssClass}
				></ColorPalette>
			</ClayForm.Group>

			<ClayForm.Group>
				<p className="mb-3 sheet-subtitle">
					{Liferay.Language.get('background-image')}
				</p>
				<ContainerBackgroundImageConfiguration
					backgroundImage={containerConfig.backgroundImage}
					onValueChange={handleConfigurationValueChanged}
				/>
			</ClayForm.Group>
		</ClayForm.Group>
	);
};

ContainerConfigurationPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			backgroundImage: BackgroundImagePropTypes,
			paddingBottom: PropTypes.number,
			paddingHorizontal: PropTypes.number,
			paddingTop: PropTypes.number,
		}),
	}),
};

function ContainerBackgroundImageConfiguration({
	backgroundImage,
	onValueChange,
}) {
	const containerBackgroundImageId = useId();

	const imageSourceOptions = {
		mapping: {
			label: Liferay.Language.get('content-mapping'),
			value: 'content_mapping',
		},

		selection: {
			label: Liferay.Language.get('manual-selection'),
			value: 'manual_selection',
		},
	};

	const [imageSource, setImageSource] = useState(() =>
		backgroundImage.fieldId || backgroundImage.mappedField
			? imageSourceOptions.mapping.value
			: imageSourceOptions.selection.value
	);

	return (
		<>
			<ClayForm.Group>
				<label htmlFor={containerBackgroundImageId}>
					{Liferay.Language.get('image-source')}
				</label>
				<ClaySelectWithOption
					id={containerBackgroundImageId}
					onChange={({target: {value}}) => {
						setImageSource(value);

						onValueChange({
							backgroundImage: {},
						});
					}}
					options={Object.values(imageSourceOptions)}
					value={imageSource}
				/>
			</ClayForm.Group>

			{imageSource === imageSourceOptions.selection.value ? (
				<ImageSelector
					imageTitle={backgroundImage.title}
					label={Liferay.Language.get('background-image')}
					onClearButtonPressed={() =>
						onValueChange({
							backgroundImage: {
								title: '',
								url: '',
							},
						})
					}
					onImageSelected={(image) =>
						onValueChange({
							backgroundImage: {
								title: image.title,
								url: image.url,
							},
						})
					}
				/>
			) : (
				<MappingSelector
					fieldType={EDITABLE_TYPES.image}
					mappedItem={backgroundImage}
					onMappingSelect={(mappedItem) => {
						onValueChange({
							backgroundImage: mappedItem,
						});
					}}
				/>
			)}
		</>
	);
}

ContainerBackgroundImageConfiguration.propTypes = {
	backgroundImage: BackgroundImagePropTypes,
	onValueChange: PropTypes.func.isRequired,
};
