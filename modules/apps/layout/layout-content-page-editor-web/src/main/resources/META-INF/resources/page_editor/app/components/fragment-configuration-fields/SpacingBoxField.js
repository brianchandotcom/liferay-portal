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
import React, {useMemo} from 'react';

import InvisibleFieldset from '../../../common/components/InvisibleFieldset';
import SpacingBox from '../../../common/components/SpacingBox';
import useControlledState from '../../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {useSelector} from '../../contexts/StoreContext';
import selectCanDetachTokenValues from '../../selectors/selectCanDetachTokenValues';
import getPreviousResponsiveStyle from '../../utils/getPreviousResponsiveStyle';

export function SpacingBoxField({disabled, field, item, onValueSelect, value}) {
	const [nextValue, setNextValue] = useControlledState(value);
	const canSetCustomValue = useSelector(selectCanDetachTokenValues);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const handleChange = (key, value, {isReset = false} = {}) => {
		if (isReset) {
			const previousResponsiveValue = getPreviousResponsiveStyle(
				key,
				item.config,
				selectedViewportSize
			);

			setNextValue((previousvalue) => ({
				...previousvalue,
				[key]: previousResponsiveValue,
			}));
		}
		else {
			setNextValue((previousValue) => ({...previousValue, [key]: value}));
		}

		onValueSelect(key, value);
	};

	const fields = useMemo(() => {
		const fields = {};

		field.typeOptions.spacingFieldSets.forEach((fieldSet) => {
			fieldSet.styles.forEach((field) => {
				fields[field.name] = field;
			});
		});

		return fields;
	}, [field.typeOptions.spacingFieldSets]);

	return (
		<>
			<InvisibleFieldset disabled={disabled}>
				{field.label ? (
					<div className="sr-only">{field.label}</div>
				) : null}

				<SpacingBox
					canSetCustomValue={canSetCustomValue}
					fields={fields}
					onChange={handleChange}
					value={nextValue}
				/>
			</InvisibleFieldset>
		</>
	);
}

SpacingBoxField.propTypes = {
	className: PropTypes.string,
	disabled: PropTypes.bool,
	field: PropTypes.shape(ConfigurationFieldPropTypes),
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.object,
};
