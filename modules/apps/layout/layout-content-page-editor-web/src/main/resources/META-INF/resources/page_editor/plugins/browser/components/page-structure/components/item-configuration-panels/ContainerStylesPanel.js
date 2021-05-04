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

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import {SelectField} from '../../../../../../app/components/fragment-configuration-fields/SelectField';
import {CONTAINER_DISPLAY_OPTIONS} from '../../../../../../app/config/constants/containerDisplayOptions';
import {CONTAINER_WIDTH_TYPES} from '../../../../../../app/config/constants/containerWidthTypes';
import {VIEWPORT_SIZES} from '../../../../../../app/config/constants/viewportSizes';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {getResponsiveConfig} from '../../../../../../app/utils/getResponsiveConfig';
import {getLayoutDataItemPropTypes} from '../../../../../../prop-types/index';
import {CommonStyles} from './CommonStyles';

const ALIGN_ITEMS_STRETCH = 'align-items-stretch';
const JUSTIFY_CONTENT_START = 'justify-content-start';

const CONTENT_DISPLAY_OPTIONS = [
	{
		label: Liferay.Language.get('block'),
		value: CONTAINER_DISPLAY_OPTIONS.block,
	},
	{
		label: Liferay.Language.get('flex-row'),
		value: CONTAINER_DISPLAY_OPTIONS.flexRow,
	},
	{
		label: Liferay.Language.get('flex-column'),
		value: CONTAINER_DISPLAY_OPTIONS.flexColumn,
	},
];

const ALIGN_OPTIONS = [
	{
		label: Liferay.Language.get('start'),
		value: 'align-items-start',
	},
	{
		label: Liferay.Language.get('center'),
		value: 'align-items-center',
	},
	{
		label: Liferay.Language.get('end'),
		value: 'align-items-end',
	},
	{
		label: Liferay.Language.get('stretch'),
		value: ALIGN_ITEMS_STRETCH,
	},
	{
		label: Liferay.Language.get('baseline'),
		value: 'align-items-baseline',
	},
];

const JUSTIFY_OPTIONS = [
	{
		label: Liferay.Language.get('start'),
		value: JUSTIFY_CONTENT_START,
	},
	{
		label: Liferay.Language.get('center'),
		value: 'justify-content-center',
	},
	{
		label: Liferay.Language.get('end'),
		value: 'justify-content-end',
	},
	{
		label: Liferay.Language.get('between'),
		value: 'justify-content-between',
	},
	{
		label: Liferay.Language.get('around'),
		value: 'justify-content-around',
	},
];

const WIDTH_TYPE_OPTIONS = [
	{
		label: Liferay.Language.get('fluid'),
		value: CONTAINER_WIDTH_TYPES.fluid,
	},
	{
		label: Liferay.Language.get('fixed-width'),
		value: CONTAINER_WIDTH_TYPES.fixed,
	},
];

export const ContainerStylesPanel = ({item}) => {
	const dispatch = useDispatch();
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const {availableViewportSizes} = config;

	const containerConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	const viewportSize = availableViewportSizes[selectedViewportSize];

	const flexOptionsVisible =
		item.config.contentDisplay === CONTAINER_DISPLAY_OPTIONS.flexColumn ||
		item.config.contentDisplay === CONTAINER_DISPLAY_OPTIONS.flexRow;

	return (
		<>
			<p className="page-editor__row-styles-panel__viewport-label">
				<ClayIcon className="mr-2" symbol={viewportSize.icon} />
				{viewportSize.label}
			</p>

			{selectedViewportSize === VIEWPORT_SIZES.desktop && (
				<div className="page-editor__page-structure__section__custom-styles">
					<h1 className="sr-only">
						{Liferay.Language.get('custom-styles')}
					</h1>

					<SelectField
						field={{
							label: Liferay.Language.get('content-display'),
							name: 'contentDisplay',
							typeOptions: {
								validValues: CONTENT_DISPLAY_OPTIONS,
							},
						}}
						onValueSelect={(name, value) => {
							const itemConfig =
								value === CONTAINER_DISPLAY_OPTIONS.block
									? {
											align: '',
											justify: '',
											[name]: '',
									  }
									: {[name]: value};

							dispatch(
								updateItemConfig({
									itemConfig,
									itemId: item.itemId,
									segmentsExperienceId,
								})
							);
						}}
						value={item.config.contentDisplay}
					/>

					{flexOptionsVisible && (
						<div className="d-flex justify-content-between">
							<SelectField
								className="page-editor__sidebar__fieldset__field-small"
								field={{
									label: Liferay.Language.get('align-items'),
									name: 'align',
									typeOptions: {
										validValues: ALIGN_OPTIONS,
									},
								}}
								onValueSelect={(name, value) => {
									dispatch(
										updateItemConfig({
											itemConfig: {
												[name]:
													value ===
													ALIGN_ITEMS_STRETCH
														? ''
														: value,
											},
											itemId: item.itemId,
											segmentsExperienceId,
										})
									);
								}}
								value={item.config.align || ALIGN_ITEMS_STRETCH}
							/>

							<SelectField
								className="page-editor__sidebar__fieldset__field-small"
								field={{
									label: Liferay.Language.get(
										'justify-content'
									),
									name: 'justify',
									typeOptions: {
										validValues: JUSTIFY_OPTIONS,
									},
								}}
								onValueSelect={(name, value) => {
									dispatch(
										updateItemConfig({
											itemConfig: {
												[name]:
													value ===
													JUSTIFY_CONTENT_START
														? ''
														: value,
											},
											itemId: item.itemId,
											segmentsExperienceId,
										})
									);
								}}
								value={
									item.config.justify || JUSTIFY_CONTENT_START
								}
							/>
						</div>
					)}

					<SelectField
						field={{
							label: Liferay.Language.get('container-width'),
							name: 'widthType',
							typeOptions: {
								validValues: WIDTH_TYPE_OPTIONS,
							},
						}}
						onValueSelect={(name, value) => {
							dispatch(
								updateItemConfig({
									itemConfig: {[name]: value},
									itemId: item.itemId,
									segmentsExperienceId,
								})
							);
						}}
						value={item.config.widthType}
					/>
				</div>
			)}

			<CommonStyles
				commonStylesValues={containerConfig.styles}
				item={item}
			/>
		</>
	);
};

ContainerStylesPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			styles: PropTypes.object,
		}),
	}),
};
