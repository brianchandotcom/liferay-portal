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

import PropTypes from 'prop-types';
import React from 'react';

import {SelectField} from '../../../../../../app/components/fragment-configuration-fields/SelectField';
import {CONTAINER_WIDTH_TYPES} from '../../../../../../app/config/constants/containerWidthTypes';
import {CONTENT_DISPLAY_OPTIONS} from '../../../../../../app/config/constants/contentDisplayOptions';
import {useDispatch} from '../../../../../../app/contexts/StoreContext';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import {getLayoutDataItemPropTypes} from '../../../../../../prop-types/index';
import {FlexOptions} from './FlexOptions';

const DISPLAY_OPTIONS = [
	{
		label: Liferay.Language.get('block'),
		value: CONTENT_DISPLAY_OPTIONS.block,
	},
	{
		label: Liferay.Language.get('flex-row'),
		value: CONTENT_DISPLAY_OPTIONS.flexRow,
	},
	{
		label: Liferay.Language.get('flex-column'),
		value: CONTENT_DISPLAY_OPTIONS.flexColumn,
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

export default function ContainerDisplayOptions({item}) {
	const dispatch = useDispatch();

	const flexOptionsVisible =
		item.config.contentDisplay === CONTENT_DISPLAY_OPTIONS.flexColumn ||
		item.config.contentDisplay === CONTENT_DISPLAY_OPTIONS.flexRow;

	return (
		<>
			<SelectField
				field={{
					label: Liferay.Language.get('content-display'),
					name: 'contentDisplay',
					typeOptions: {
						validValues: DISPLAY_OPTIONS,
					},
				}}
				onValueSelect={(name, value) => {
					const itemConfig =
						value === CONTENT_DISPLAY_OPTIONS.block
							? {
									align: '',
									flexWrap: '',
									justify: '',
									[name]: '',
							  }
							: {[name]: value};

					dispatch(
						updateItemConfig({
							itemConfig,
							itemId: item.itemId,
						})
					);
				}}
				value={item.config.contentDisplay}
			/>

			{flexOptionsVisible && (
				<FlexOptions
					itemConfig={item.config}
					onConfigChange={(name, value) => {
						dispatch(
							updateItemConfig({
								itemConfig: {
									[name]: value,
								},
								itemId: item.itemId,
							})
						);
					}}
				/>
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
						})
					);
				}}
				value={item.config.widthType}
			/>
		</>
	);
}

ContainerDisplayOptions.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			link: PropTypes.oneOfType([
				PropTypes.shape({
					href: PropTypes.string,
					target: PropTypes.string,
				}),
				PropTypes.shape({
					classNameId: PropTypes.string,
					classPK: PropTypes.string,
					fieldId: PropTypes.string,
					target: PropTypes.string,
				}),
				PropTypes.shape({
					mappedField: PropTypes.string,
					target: PropTypes.string,
				}),
			]),
		}),
	}),
};
