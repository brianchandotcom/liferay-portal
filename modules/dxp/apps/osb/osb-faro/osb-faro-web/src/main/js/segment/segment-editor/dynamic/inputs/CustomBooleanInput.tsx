import BooleanInput from './BooleanInput';
import React from 'react';
import {getPropertyValue, setPropertyValue} from '../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../utils/types';

const CustomBooleanInput: React.FC<ISegmentEditorCustomInputBase> = ({
	displayValue,
	id,
	onChange,
	operatorRenderer,
	property,
	value: valueIMap
}) => {
	const handleChange = ({value}) =>
		onChange({value: setPropertyValue(valueIMap, 'value', 0, value)});

	return (
		<BooleanInput
			displayValue={displayValue}
			id={id}
			onChange={handleChange}
			operatorRenderer={operatorRenderer}
			property={property}
			value={getPropertyValue(valueIMap, 'value', 0)}
		/>
	);
};

export default CustomBooleanInput;
