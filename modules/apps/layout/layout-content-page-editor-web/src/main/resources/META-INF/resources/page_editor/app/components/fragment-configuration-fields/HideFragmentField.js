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

import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {config} from '../../config';
import {CheckboxField} from './CheckboxField';

export function HideFragmentField({
	disabled,
	field,
	onValueSelect,
	title,
	value,
}) {
	return (
		<>
			<CheckboxField
				disabled={disabled}
				field={field}
				onValueSelect={onValueSelect}
				title={title}
				value={value}
			/>
			{value === 'none' && config.fragmentAdvancedOptionsEnabled && (
				<p className="small text-secondary">
					{Liferay.Language.get(
						'this-fragment-is-still-visible-on-search-.you-can-hide-it-from-search-in-advanced-tab'
					)}
				</p>
			)}
		</>
	);
}

HideFragmentField.propTypes = {
	disabled: PropTypes.bool,
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
};
