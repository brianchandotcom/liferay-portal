import getCN from 'classnames';
import HelpBlock from './HelpBlock';
import Input from '../Input';
import InputList from '../InputList';
import Label from './Label';
import React, {useEffect, useState} from 'react';
import {FieldProps} from 'formik';
import {IInputListProps as IInputListComponentProps} from 'shared/components/InputList';
import {isEmpty} from 'lodash';

interface IInputListProps
	extends React.HTMLAttributes<HTMLElement>,
		FieldProps {
	contentAfter?: React.ReactNode;
	errorMessage: string;
	inline?: boolean;
	inset?: {
		content: React.ReactNode;
		position: 'after' | 'before';
	};
	label?: React.ReactNode;
	onChangeInputList: (value: string) => void;
	popover?: {
		content: React.ReactNode;
		title: React.ReactNode;
	};
	required?: boolean;
	secondaryInfo?: React.ReactNode;
	showHelpBlock?: boolean;
	text?: {
		content: React.ReactNode;
		position: 'append' | 'prepend';
	};
	validationFn: (string) => boolean;
}

const renderInput: React.FC<
	{contentAfter; inset; text} & IInputListComponentProps
> = ({contentAfter, inset, text, ...props}) => {
	if (!isEmpty(text)) {
		if (text.position === 'prepend') {
			return (
				<Input.Group>
					<Input.GroupItem position='prepend' shrink>
						<Input.Text>{text.content}</Input.Text>
					</Input.GroupItem>

					<Input.GroupItem position='append'>
						<InputList {...props} />
					</Input.GroupItem>
				</Input.Group>
			);
		} else {
			return (
				<Input.Group>
					<Input.GroupItem position='prepend'>
						<InputList {...props} />
					</Input.GroupItem>

					<Input.GroupItem position='append' shrink>
						<Input.Text>{text.content}</Input.Text>
					</Input.GroupItem>
				</Input.Group>
			);
		}
	} else if (!isEmpty(inset)) {
		return (
			<Input.Group>
				<Input.GroupItem>
					<InputList {...props} />

					<Input.Inset position={inset.position}>
						{inset.content}
					</Input.Inset>
				</Input.GroupItem>
			</Input.Group>
		);
	} else if (contentAfter) {
		return (
			<Input.Group>
				<InputList {...props} />

				<Input.GroupItem shrink>{contentAfter}</Input.GroupItem>
			</Input.Group>
		);
	} else {
		return <InputList {...props} />;
	}
};

const FormInputList: React.FC<IInputListProps> = ({
	className,
	contentAfter,
	errorMessage,
	field: {name, value},
	form: {errors, setFieldError, setFieldTouched, setFieldValue, touched},
	inline,
	inset,
	label,
	onChangeInputList,
	popover,
	required,
	secondaryInfo,
	showHelpBlock = true,
	text,
	validationFn = () => true,
	...otherProps
}) => {
	const hasError = errors[name];
	const isTouched = touched[name];

	const [items, onItemsChange] = useState(value || []);
	const [inputValue, onInputChange] = useState<string>();

	useEffect(() => {
		setFieldValue(name, items);
	}, [items]);

	useEffect(() => {
		onItemsChange(value);
	}, [value]);

	useEffect(() => {
		if (inputValue) {
			setFieldTouched(name);
		} else {
			setFieldError(name, '');
		}
	}, [inputValue]);

	onChangeInputList(inputValue);

	const classes = getCN(className, {
		'form-inline-group': inline,
		'has-error': isTouched && hasError,
		'has-success': isTouched && !hasError
	});

	const onValidationFail = () => setFieldError(name, errorMessage);

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

			<div className='input-group'>
				{renderInput({
					contentAfter,
					inputValue,
					inset,
					items,
					onInputChange,
					onItemsChange,
					onValidationFail,
					text,
					validateOnBlur: true,
					validationFn,
					...otherProps
				})}
			</div>

			{showHelpBlock && <HelpBlock name={name} />}
		</div>
	);
};

export default FormInputList;
