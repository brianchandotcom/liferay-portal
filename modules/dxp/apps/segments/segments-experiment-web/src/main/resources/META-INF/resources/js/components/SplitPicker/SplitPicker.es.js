/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
