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
