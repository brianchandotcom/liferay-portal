import getCN from 'classnames';
import HelpBlock from './HelpBlock';
import Label from './Label';
import React from 'react';
import Select from '../Select';
import {FieldProps} from 'formik';

interface IFormSelectProps
	extends FieldProps,
		React.HTMLAttributes<HTMLElement> {
	disabled: boolean;
	inline?: boolean;
	label: React.ReactNode;
	popover: {
		content: React.ReactNode;
		title: React.ReactNode;
	};
	required?: boolean;
	secondaryInfo?: React.ReactNode;
}

const FormSelect: React.FC<IFormSelectProps> = ({
	children,
	className,
	field,
	form,
	inline = false,
	label,
	onChange,
	popover,
	required = false,
	secondaryInfo,
	...otherProps
}) => {
	const {disabled} = otherProps;
	const {name} = field;

	const error = form.errors[name];
	const touched = form.touched[name];

	const classes = getCN(className, {
		'form-inline-group': inline,
		'has-error': touched && error,
		'has-success': touched && !error && !disabled
	});

	const handleChange = event => {
		field.onChange(event);

		if (onChange) {
			onChange(event);
		}
	};

	return (
		<div className={classes}>
			{label && (
				<Label htmlFor={name} popover={popover} required={required}>
					{label}
				</Label>
			)}

			{secondaryInfo && (
				<Label className='font-weight-normal' htmlFor={name}>
					<p>{secondaryInfo}</p>
				</Label>
			)}

			<Select
				{...otherProps}
				{...field}
				id={name}
				name={name}
				onChange={handleChange}
			>
				{children}
			</Select>

			<HelpBlock name={name} />
		</div>
	);
};

export default Object.assign(FormSelect, {Item: Select.Item});
