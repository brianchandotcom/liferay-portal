import autobind from 'autobind-decorator';
import getCN from 'classnames';
import HelpBlock from 'shared/components/form/HelpBlock';
import Input from '../Input';
import Label from './Label';
import MaskedInput from '../MaskedInput';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import PropTypes from 'prop-types';
import React from 'react';
import {FieldProps, FormikErrors, FormikTouched} from 'formik';
import {isEmpty, isNumber, mapValues, pickBy} from 'lodash';

const APPEND_POSITIONS = ['append', 'prepend'];
const INSET_POSITIONS = ['after', 'before'];

type AppendPositions = 'append' | 'prepend';
type InsetPositions = 'after' | 'before';

const getOptionalProps = propsConfig => {
	const validProps = pickBy(propsConfig, ({test, value}) =>
		test ? test(value) : value
	);

	return mapValues(validProps, ({value}) => value);
};

interface IFormInputProps
	extends FieldProps,
		React.HTMLAttributes<HTMLInputElement> {
	contentAfter: React.ReactNode;
	inline: boolean;
	inset: {
		content: React.ReactNode;
		position: InsetPositions;
	};
	label: string;
	mask: any;
	onChange: (event) => void;
	popover?: {
		content: React.ReactNode;
		title: React.ReactNode;
	};
	required: boolean;
	secondaryInfo?: React.ReactNode;
	showHelpBlock: boolean;
	showSuccess: boolean;
	text: {
		content: React.ReactNode;
		position: AppendPositions;
	};
	width: number;
}

export default class FormInput extends React.Component<IFormInputProps> {
	static defaultProps = {
		inline: false,
		inset: {},
		required: false,
		showHelpBlock: true,
		showSuccess: true
	};

	static propTypes = {
		contentAfter: PropTypes.node,
		field: PropTypes.shape({
			name: PropTypes.string,
			onBlur: PropTypes.func,
			onChange: PropTypes.func,
			value: PropTypes.any
		}),
		form: PropTypes.shape({
			errors: PropTypes.object,
			touched: PropTypes.object
		}),
		inline: PropTypes.bool,
		inset: PropTypes.shape({
			content: PropTypes.node,
			position: PropTypes.oneOf(INSET_POSITIONS)
		}),
		label: PropTypes.node,
		mask: PropTypes.oneOfType([
			PropTypes.array,
			PropTypes.func,
			PropTypes.bool,
			PropTypes.shape({
				mask: PropTypes.oneOfType([PropTypes.array, PropTypes.func]),
				pipe: PropTypes.func
			})
		]),
		onChange: PropTypes.func,
		popover: PropTypes.shape({
			content: PropTypes.node,
			title: PropTypes.node
		}),
		required: PropTypes.bool,
		showHelpBlock: PropTypes.bool,
		showSuccess: PropTypes.bool,
		text: PropTypes.shape({
			content: PropTypes.node,
			position: PropTypes.oneOf(APPEND_POSITIONS)
		}),
		width: PropTypes.number
	};

	@autobind
	handleChange(event) {
		const {
			field: {name},
			form: {setFieldValue},
			onChange
		} = this.props;

		const {value} = event.target;

		setFieldValue(name, value);

		if (onChange) {
			onChange(event);
		}
	}

	extractProperty(
		object: FormikErrors<any> | FormikTouched<any>,
		path: string
	): any {
		return path
			.replace(/\[/g, '.')
			.replace(/]/g, '')
			.split('.')
			.reduce(
				(currentObjectValue, propToAcess) =>
					currentObjectValue
						? currentObjectValue[propToAcess]
						: undefined,
				object
			);
	}

	renderInput(inputProps) {
		const {contentAfter, inset, mask, text} = this.props;

		const ComponentFn = mask ? MaskedInput : Input;

		const inputComponent = <ComponentFn {...inputProps} />;

		if (!isEmpty(text)) {
			if (text.position === 'prepend') {
				return (
					<Input.Group>
						<Input.GroupItem position='prepend' shrink>
							<Input.Text>{text.content}</Input.Text>
						</Input.GroupItem>

						<Input.GroupItem position='append'>
							{inputComponent}
						</Input.GroupItem>
					</Input.Group>
				);
			} else {
				return (
					<Input.Group>
						<Input.GroupItem position='prepend'>
							{inputComponent}
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
						{inputComponent}

						<Input.Inset position={inset.position}>
							{inset.content}
						</Input.Inset>
					</Input.GroupItem>
				</Input.Group>
			);
		} else if (contentAfter) {
			return (
				<Input.Group className='content-after'>
					<Input.GroupItem>{inputComponent}</Input.GroupItem>

					<Input.GroupItem shrink>{contentAfter}</Input.GroupItem>
				</Input.Group>
			);
		} else {
			return inputComponent;
		}
	}

	render() {
		const {
			className,
			field,
			form,
			inline,
			inset,
			label,
			mask,
			popover,
			required,
			secondaryInfo,
			showHelpBlock,
			showSuccess,
			width,
			...otherProps
		} = this.props;

		const {name} = field;

		const error = this.extractProperty(form.errors, name);
		const touched = this.extractProperty(form.touched, name);

		const classes = getCN(className, {
			'form-inline-group': inline,
			'has-error': touched && error,
			'has-success': showSuccess && touched && !error
		});

		const style = isNumber(width)
			? {flexBasis: `${width}%`, flexGrow: 0}
			: undefined;

		const optionalProps = {
			inset: {value: inset.position},
			mask: {value: mask}
		};

		return (
			<div className={classes} style={style}>
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

				{this.renderInput({
					...field,
					...omitDefinedProps(otherProps, FormInput.propTypes),
					...getOptionalProps(optionalProps),
					id: name,
					onChange: this.handleChange
				})}

				{showHelpBlock && <HelpBlock name={name} />}
			</div>
		);
	}
}
