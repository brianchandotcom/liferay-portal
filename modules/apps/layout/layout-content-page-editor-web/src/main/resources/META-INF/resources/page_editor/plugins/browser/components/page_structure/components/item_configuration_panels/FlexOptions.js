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

import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';

const ALIGN_OPTIONS = [
	{
		label: Liferay.Language.get('start'),
		value: 'align-items-start',
	},
	{
		label: Liferay.Language.get('center[verb]'),
		value: 'align-items-center',
	},
	{
		label: Liferay.Language.get('end'),
		value: 'align-items-end',
	},
	{
		label: Liferay.Language.get('stretch'),
		value: '',
	},
	{
		label: Liferay.Language.get('baseline'),
		value: 'align-items-baseline',
	},
];

const FLEX_WRAP_OPTIONS = [
	{
		label: Liferay.Language.get('nowrap'),
		value: '',
	},
	{
		label: Liferay.Language.get('wrap'),
		value: 'flex-wrap',
	},
	{
		label: Liferay.Language.get('wrap-reverse'),
		value: 'flex-wrap-reverse',
	},
];

const JUSTIFY_OPTIONS = [
	{
		label: Liferay.Language.get('start'),
		value: '',
	},
	{
		label: Liferay.Language.get('center[verb]'),
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

export function FlexOptions({itemConfig, onConfigChange}) {
	return (
		<>
			<SelectField
				field={{
					label: Liferay.Language.get('flex-wrap'),
					name: 'flexWrap',
					typeOptions: {
						validValues: FLEX_WRAP_OPTIONS,
					},
				}}
				onValueSelect={onConfigChange}
				value={itemConfig.flexWrap || ''}
			/>

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
					onValueSelect={onConfigChange}
					value={itemConfig.align || ''}
				/>

				<SelectField
					className="page-editor__sidebar__fieldset__field-small"
					field={{
						label: Liferay.Language.get('justify-content'),
						name: 'justify',
						typeOptions: {
							validValues: JUSTIFY_OPTIONS,
						},
					}}
					onValueSelect={onConfigChange}
					value={itemConfig.justify || ''}
				/>
			</div>
		</>
	);
}

FlexOptions.propTypes = {
	itemConfig: PropTypes.object.isRequired,
	onConfigChange: PropTypes.func.isRequired,
};
