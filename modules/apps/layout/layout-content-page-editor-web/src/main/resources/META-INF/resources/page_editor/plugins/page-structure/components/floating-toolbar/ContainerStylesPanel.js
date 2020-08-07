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

import ClayForm from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import {ColorPaletteField} from '../../../../app/components/fragment-configuration-fields/ColorPaletteField';
import {ImageSelectorField} from '../../../../app/components/fragment-configuration-fields/ImageSelectorField';
import {SelectField} from '../../../../app/components/fragment-configuration-fields/SelectField';
import {TextField} from '../../../../app/components/fragment-configuration-fields/TextField';
import {config} from '../../../../app/config/index';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateItemConfig from '../../../../app/thunks/updateItemConfig';
import FormRow from '../../../../common/components/FormRow';
import {
	BackgroundImagePropTypes,
	getLayoutDataItemPropTypes,
} from '../../../../prop-types/index';

const MARGIN_AUTO_OPTION = {
	label: Liferay.Language.get('auto'),
	value: 'auto',
};

export const ContainerStylesPanel = ({item}) => {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const isHorizontalMarginDisabled = item.config.widthType === 'fixed';

	const horizontalMarginOptions = isHorizontalMarginDisabled
		? [MARGIN_AUTO_OPTION]
		: config.marginOptions;

	const handleValueSelect = (name, value) => {
		const config = {[name]: value};

		if (name === 'contentDisplay' && value === 'block') {
			config.justify = '';
			config.align = '';
		}

		if (name === 'widthType' && value === 'fixed') {
			config.marginLeft = '';
			config.marginRight = '';
		}

		dispatch(
			updateItemConfig({
				itemConfig: {...item.config, ...config},
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);
	};

	const Section = ({children, label}) => (
		<>
			<div className="align-items-center d-flex justify-content-between page-editor__container-styles-panel__label pt-3">
				<p className="mb-3 text-uppercase">{label}</p>
			</div>
			{children}
		</>
	);

	return (
		<ClayForm>
			<ClayForm.Group small>
				<h1 className="sr-only">
					{Liferay.Language.get('container-styles')}
				</h1>

				{config.containerItemFlexEnabled && (
					<SelectField
						field={{
							label: Liferay.Language.get('content-display'),
							name: 'contentDisplay',
							typeOptions: {
								validValues: [
									{
										label: Liferay.Language.get('block'),
										value: 'block',
									},
									{
										label: Liferay.Language.get('flex'),
										value: 'flex',
									},
								],
							},
						}}
						onValueSelect={handleValueSelect}
						value={item.config.contentDisplay}
					/>
				)}

				{item.config.contentDisplay === 'flex' && (
					<FormRow>
						<FormRow.Column>
							<SelectField
								field={{
									label: Liferay.Language.get(
										'horizontal-align'
									),
									name: 'justify',
									typeOptions: {
										validValues: [
											{
												label: Liferay.Language.get(
													'none'
												),
												value: '',
											},
											{
												label: Liferay.Language.get(
													'start'
												),
												value: 'justify-content-start',
											},
											{
												label: Liferay.Language.get(
													'center'
												),
												value: 'justify-content-center',
											},
											{
												label: Liferay.Language.get(
													'end'
												),
												value: 'justify-content-end',
											},
											{
												label: Liferay.Language.get(
													'space-between'
												),
												value:
													'justify-content-between',
											},
											{
												label: Liferay.Language.get(
													'space-around'
												),
												value: 'justify-content-around',
											},
										],
									},
								}}
								onValueSelect={handleValueSelect}
								value={item.config.justify}
							/>
						</FormRow.Column>

						<FormRow.Column>
							<SelectField
								field={{
									label: Liferay.Language.get(
										'vertical-align'
									),
									name: 'align',
									typeOptions: {
										validValues: [
											{
												label: Liferay.Language.get(
													'none'
												),
												value: '',
											},
											{
												label: Liferay.Language.get(
													'start'
												),
												value: 'align-items-start',
											},
											{
												label: Liferay.Language.get(
													'center'
												),
												value: 'align-items-center',
											},
											{
												label: Liferay.Language.get(
													'end'
												),
												value: 'align-items-end',
											},
											{
												label: Liferay.Language.get(
													'stretch'
												),
												value: 'align-items-stretch',
											},
										],
									},
								}}
								onValueSelect={handleValueSelect}
								value={item.config.align}
							/>
						</FormRow.Column>
					</FormRow>
				)}

				<SelectField
					field={{
						label: Liferay.Language.get('container-width'),
						name: 'widthType',
						typeOptions: {
							validValues: [
								{
									label: Liferay.Language.get('fluid'),
									value: 'fluid',
								},
								{
									label: Liferay.Language.get('fixed-width'),
									value: 'fixed',
								},
							],
						},
					}}
					onValueSelect={handleValueSelect}
					value={item.config.widthType}
				/>

				<Section label={Liferay.Language.get('margin')}>
					<FormRow>
						<FormRow.Column>
							<SelectField
								disabled={isHorizontalMarginDisabled}
								field={{
									label: Liferay.Language.get('left'),
									name: 'marginLeft',
									typeOptions: {
										validValues: horizontalMarginOptions,
									},
								}}
								onValueSelect={handleValueSelect}
								value={
									isHorizontalMarginDisabled
										? MARGIN_AUTO_OPTION.value
										: item.config.marginLeft
								}
							/>
						</FormRow.Column>

						<FormRow.Column>
							<SelectField
								disabled={isHorizontalMarginDisabled}
								field={{
									label: Liferay.Language.get('right'),
									name: 'marginRight',
									typeOptions: {
										validValues: horizontalMarginOptions,
									},
								}}
								onValueSelect={handleValueSelect}
								value={
									isHorizontalMarginDisabled
										? MARGIN_AUTO_OPTION.value
										: item.config.marginRight
								}
							/>
						</FormRow.Column>

						<FormRow.Column>
							<SelectField
								field={{
									label: Liferay.Language.get('top'),
									name: 'marginTop',
									typeOptions: {
										validValues: config.marginOptions,
									},
								}}
								onValueSelect={handleValueSelect}
								value={item.config.marginTop}
							/>
						</FormRow.Column>

						<FormRow.Column>
							<SelectField
								field={{
									label: Liferay.Language.get('bottom'),
									name: 'marginBottom',
									typeOptions: {
										validValues: config.marginOptions,
									},
								}}
								onValueSelect={handleValueSelect}
								value={item.config.marginBottom}
							/>
						</FormRow.Column>
					</FormRow>
				</Section>

				<Section label={Liferay.Language.get('padding')}>
					<FormRow>
						<FormRow.Column>
							<SelectField
								field={{
									label: Liferay.Language.get('left'),
									name: 'paddingLeft',
									typeOptions: {
										validValues: config.paddingOptions,
									},
								}}
								onValueSelect={handleValueSelect}
								value={item.config.paddingLeft}
							/>
						</FormRow.Column>

						<FormRow.Column>
							<SelectField
								field={{
									label: Liferay.Language.get('right'),
									name: 'paddingRight',
									typeOptions: {
										validValues: config.paddingOptions,
									},
								}}
								onValueSelect={handleValueSelect}
								value={item.config.paddingRight}
							/>
						</FormRow.Column>

						<FormRow.Column>
							<SelectField
								field={{
									label: Liferay.Language.get('top'),
									name: 'paddingTop',
									typeOptions: {
										validValues: config.paddingOptions,
									},
								}}
								onValueSelect={handleValueSelect}
								value={item.config.paddingTop}
							/>
						</FormRow.Column>

						<FormRow.Column>
							<SelectField
								field={{
									label: Liferay.Language.get('bottom'),
									name: 'paddingBottom',
									typeOptions: {
										validValues: config.paddingOptions,
									},
								}}
								onValueSelect={handleValueSelect}
								value={item.config.paddingBottom}
							/>
						</FormRow.Column>
					</FormRow>
				</Section>

				<Section label={Liferay.Language.get('background')}>
					<ColorPaletteField
						field={{
							label: Liferay.Language.get('color'),
							name: 'backgroundColorCssClass',
						}}
						onValueSelect={(name, {cssClass}) =>
							handleValueSelect(name, cssClass)
						}
						value={{cssClass: item.config.backgroundColorCssClass}}
					/>

					<ImageSelectorField
						field={{
							label: Liferay.Language.get('image'),
							name: 'backgroundImage',
						}}
						onValueSelect={handleValueSelect}
						value={item.config.backgroundImage}
					/>
				</Section>

				<Section label={Liferay.Language.get('borders')}>
					<TextField
						field={{
							defaultValue: 0,
							label: Liferay.Language.get('border-width'),
							name: 'borderWidth',
							typeOptions: {
								validation: {
									max: 100,
									min: 0,
									type: 'number',
								},
							},
						}}
						onValueSelect={handleValueSelect}
						value={
							typeof item.config.borderWidth !== 'undefined'
								? Number(item.config.borderWidth)
								: undefined
						}
					/>
					<SelectField
						field={{
							label: Liferay.Language.get('border-radius'),
							name: 'borderRadius',
							typeOptions: {
								validValues: [
									{
										label: Liferay.Language.get('none'),
										value: '',
									},
									{
										label: Liferay.Language.get('regular'),
										value: 'rounded',
									},
									{
										label: Liferay.Language.get('large'),
										value: 'rounded-lg',
									},
									{
										label: Liferay.Language.get('pill'),
										value: 'rounded-pill',
									},
									{
										label: Liferay.Language.get('circle'),
										value: 'rounded-circle',
									},
								],
							},
						}}
						onValueSelect={handleValueSelect}
						value={item.config.borderRadius}
					/>

					<ColorPaletteField
						field={{
							label: Liferay.Language.get('border-color'),
							name: 'borderColor',
						}}
						onValueSelect={(name, {cssClass}) =>
							handleValueSelect(name, cssClass)
						}
						value={{cssClass: item.config.borderColor}}
					/>
				</Section>

				<Section label={Liferay.Language.get('effects')}>
					<TextField
						field={{
							defaultValue: 100,
							label: Liferay.Language.get('opacity'),
							name: 'opacity',
							typeOptions: {
								validation: {
									max: 100,
									min: 0,
									type: 'number',
								},
							},
						}}
						onValueSelect={handleValueSelect}
						value={
							typeof item.config.opacity !== 'undefined'
								? Number(item.config.opacity)
								: undefined
						}
					/>

					<SelectField
						field={{
							label: Liferay.Language.get('drop-shadow'),
							name: 'shadow',
							typeOptions: {
								validValues: [
									{
										label: Liferay.Language.get('default'),
										value: '',
									},
									{
										label: Liferay.Language.get('none'),
										value: 'shadow-none',
									},
									{
										label: Liferay.Language.get('small'),
										value: 'shadow-sm',
									},
									{
										label: Liferay.Language.get('regular'),
										value: 'shadow',
									},
									{
										label: Liferay.Language.get('large'),
										value: 'shadow-lg',
									},
								],
							},
						}}
						onValueSelect={handleValueSelect}
						value={item.config.shadow}
					/>
				</Section>
			</ClayForm.Group>
		</ClayForm>
	);
};

ContainerStylesPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			backgroundImage: BackgroundImagePropTypes,
			paddingBottom: PropTypes.number,
			paddingHorizontal: PropTypes.number,
			paddingTop: PropTypes.number,
		}),
	}),
};
