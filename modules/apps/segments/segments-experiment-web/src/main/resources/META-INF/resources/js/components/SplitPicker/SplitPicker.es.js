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

import React, {useReducer, useEffect} from 'react';
import PropTypes from 'prop-types';
import {SliderWithLabel} from '../SliderWithLabel.es';
import {SegmentsVariantType} from '../../types.es';
import {changeSplitValue} from './utils.es';

function SplitPicker({variants, onChange}) {
	const [splitVariants, dispatch] = useReducer(_reducer, variants);

	useEffect(() => {
		onChange(splitVariants);
	}, [splitVariants, onChange]);

	return (
		<div>
			{splitVariants.map(variant => {
				return (
					<SliderWithLabel
						key={variant.segmentsExperimentRelId}
						label={variant.name}
						onValueChange={value =>
							dispatch({
								type: 'change',
								value,
								variantId: variant.segmentsExperimentRelId
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
	variants: PropTypes.arrayOf(SegmentsVariantType)
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
