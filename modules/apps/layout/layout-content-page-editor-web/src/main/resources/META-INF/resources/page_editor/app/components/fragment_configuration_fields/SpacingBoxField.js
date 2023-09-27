/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useControlledState} from '@liferay/layout-js-components-web';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import InvisibleFieldset from '../../../common/components/InvisibleFieldset';
import SpacingBox from '../../../common/components/SpacingBox';
import {ConfigurationFieldPropTypes} from '../../../prop_types/index';
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
