import AutocompleteInput from './AutocompleteInput';
import Checkbox from './Checkbox';
import DateInput from './DateInput';
import DateRangeInput from './DateRangeInput';
import getCN from 'classnames';
import Input from './Input';
import InputList from './InputList';
import Label from './Label';
import PasswordInput from './PasswordInput';
import RadioGroup from './RadioGroup';
import React from 'react';
import SearchableSelect from './SearchableSelect';
import Select from './Select';
import ToggleSwitch from './ToggleSwitch';
import {Field, Formik} from 'formik';

interface IFormGroupProps extends React.HTMLAttributes<HTMLDivElement> {
	autoFit?: boolean;
}

const FormGroup: React.FC<IFormGroupProps> = ({
	autoFit = false,
	children,
	className
}) => {
	const classes = getCN('form-group', className, {
		'form-group-autofit': autoFit
	});

	return <div className={classes}>{children}</div>;
};

interface IFormGroupItemProps extends React.HTMLAttributes<HTMLDivElement> {
	label?: boolean;
	labelSpacer?: boolean;
	shrink?: boolean;
}

const FormGroupItem: React.FC<IFormGroupItemProps> = ({
	children,
	className,
	label = false,
	labelSpacer = false,
	shrink = false
}) => {
	const classes = getCN('form-group-item', className, {
		'form-group-item-label': label,
		'form-group-item-label-spacer': labelSpacer,
		'form-group-item-shrink': shrink
	});

	return <div className={classes}>{children}</div>;
};

/**
 * Wrap a form component with the Formik Field component.
 * @param {Component} FormComponent
 */
export const withField = FormComponent =>
	React.forwardRef<
		typeof FormComponent,
		React.ComponentProps<typeof FormComponent>
	>(({name, ...otherParams}, ref) => {
		const innerRef = ref ? {innerRef: ref} : {};

		return (
			<Field
				{...otherParams}
				{...innerRef}
				component={FormComponent}
				name={name}
			/>
		);
	});

export default Object.assign(Formik, {
	AutocompleteInput: withField(AutocompleteInput),
	Checkbox: withField(Checkbox),
	DateInput: withField(DateInput),
	DateRangeInput: withField(DateRangeInput),
	Form: ({
		className,
		...otherProps
	}: {
		className?: string;
		[key: string]: any;
	}) => <form className={getCN('form-root', className)} {...otherProps} />,
	Group: FormGroup,
	GroupItem: FormGroupItem,
	Input: withField(Input),
	InputList: withField(InputList),
	Label,
	PasswordInput,
	RadioGroup: Object.assign(withField(RadioGroup), {
		Option: RadioGroup.Option,
		Subsection: RadioGroup.Subsection
	}),
	SearchableSelect: withField(SearchableSelect),
	Select: Object.assign(withField(Select), {Item: Select.Item}),
	ToggleSwitch: withField(ToggleSwitch)
});

export * from 'shared/util/validators';
