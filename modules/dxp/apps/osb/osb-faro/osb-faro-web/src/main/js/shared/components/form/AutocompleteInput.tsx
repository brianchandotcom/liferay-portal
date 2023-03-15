import AutocompleteInput from 'shared/components/AutocompleteInput';
import getCN from 'classnames';
import HelpBlock from './HelpBlock';
import Label from './Label';
import React from 'react';
import {FieldProps} from 'formik';

interface FormAutocompleteInputType {
	onChange: (value: string | number) => void;
	required?: boolean;
	value: string | number;
	label?: string;
	className?: string;
}
const FormAutocompleteInput: React.FC<
	FormAutocompleteInputType & FieldProps
> = ({
	className,
	field: {name},
	form: {errors},
	label,
	required,
	...otherProps
}) => {
	const error = errors[name];

	const classes = getCN(className, {
		'has-error': error
	});

	return (
		<div className={classes}>
			{label && (
				<Label htmlFor={name} required={required}>
					{label}
				</Label>
			)}

			<AutocompleteInput inputName={name} {...otherProps} />

			<HelpBlock name={name} />
		</div>
	);
};
export default FormAutocompleteInput;
