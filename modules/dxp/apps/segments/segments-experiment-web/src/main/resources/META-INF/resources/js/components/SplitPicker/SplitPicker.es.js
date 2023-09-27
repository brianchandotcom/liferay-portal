/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useEffect, useReducer} from 'react';

import {SegmentsVariantType} from '../../types.es';
import {SliderWithLabel} from '../SliderWithLabel.es';
import {changeSplitValue} from './utils.es';

function SplitPicker({onChange, variants}) {
	const [splitVariants, dispatch] = useReducer(_reducer, variants);

	useEffect(() => {
		onChange(splitVariants);
	}, [splitVariants, onChange]);

	return (
		<div>
			{splitVariants.map((variant) => {
				return (
					<SliderWithLabel
						key={variant.segmentsExperimentRelId}
						label={variant.name}
						onValueChange={(value) =>
							dispatch({
								type: 'change',
								value,
								variantId: variant.segmentsExperimentRelId,
							})
						}
						value={variant.split}
					/>
				);
			})}
		</div>
	);
}

SplitPicker.propTypes = {
	onChange: PropTypes.func.isRequired,
	variants: PropTypes.arrayOf(SegmentsVariantType),
};

export {SplitPicker};

function _reducer(splitVariants, action) {
	switch (action.type) {
		case 'change':
		default:
			return changeSplitValue(
				splitVariants,
				action.variantId,
				action.value
			);
	}
}
