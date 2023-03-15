import BaseSelect from './BaseSelect';
import React from 'react';

interface IAutocompleteProps
	extends Omit<React.ComponentProps<typeof BaseSelect>, 'onChange'> {
	onChange: (value: string | number) => void;
	value: string | number;
}

const AutocompleteInput: React.FC<IAutocompleteProps> = ({
	className,
	onBlur,
	onChange,
	value,
	...otherProps
}) => (
	<BaseSelect
		{...otherProps}
		className={className}
		inputValue={value}
		onBlur={onBlur}
		onInputValueChange={onChange}
		onSelect={onChange}
	/>
);
export default AutocompleteInput;
