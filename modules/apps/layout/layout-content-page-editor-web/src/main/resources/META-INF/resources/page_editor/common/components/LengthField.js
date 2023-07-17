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

import {ClayButtonWithIcon} from '@clayui/button';
import {LengthInput} from '@liferay/layout-js-components-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {VIEWPORT_SIZES} from '../../app/config/constants/viewportSizes';
import {useSelector} from '../../app/contexts/StoreContext';
import {getResetLabelByViewport} from '../../app/utils/getResetLabelByViewport';
import {ConfigurationFieldPropTypes} from '../../prop_types/index';

const RESTORABLE_FIELDS = new Set(['opacity', 'borderWidth']);

export function LengthField({field, onEnter, onValueSelect, value}) {
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const [showRestoreButton, setShowRestoreButton] = useState(
		RESTORABLE_FIELDS.has(field.name) && value !== field.defaultValue
	);

	const resetButtonLabel = useMemo(
		() => getResetLabelByViewport(selectedViewportSize),
		[selectedViewportSize]
	);

	return (
		<div className="align-items-center d-flex page-editor__length-field">
			<LengthInput
				className={field.icon ? 'mb-0' : null}
				defaultUnit={field.typeOptions?.defaultUnit}
				field={field}
				onEnter={onEnter}
				onValueSelect={(name, value) => {
					if (RESTORABLE_FIELDS.has(field.name)) {
						setShowRestoreButton(true);
					}

					onValueSelect(name, value);
				}}
				showLabel={!field.icon}
				value={value}
			/>

			{showRestoreButton ? (
				<ClayButtonWithIcon
					aria-label={resetButtonLabel}
					className="border-0 flex-shrink-0 mb-0 ml-2"
					displayType="secondary"
					onClick={() => {
						setShowRestoreButton(
							selectedViewportSize !== VIEWPORT_SIZES.desktop
						);
						onValueSelect(field.name, null);
					}}
					size="sm"
					symbol="restore"
					title={resetButtonLabel}
				/>
			) : null}
		</div>
	);
}

LengthField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onEnter: PropTypes.func,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};
