import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {useField} from 'formik';
import {email, required, validate} from '../utils/validations.form';

const Input = ({groupStyle, helper, label, validations, ...props}) => {
	if (props.type === 'email') {
		validations = validations
			? [(value) => email(value), ...validations]
			: [(value) => email(value)];
	}

	if (props.required) {
		validations = validations
			? [...validations, (value) => required(value)]
			: [(value) => required(value)];
	}

	const [field, meta] = useField({
		...props,
		validate: (value) => validate(validations, value),
	});

	return (
		<ClayForm.Group
			className={classNames('w-100', {
				groupStyle,
				'has-error': meta.touched && meta.error,
				'has-success': meta.touched && !meta.error,
			})}
		>
			<label>
				{label}{' '}
				{props.required && (
					<span className="ml-n1 text-danger text-paragraph-sm">
						*
					</span>
				)}
				<ClayInput {...field} {...props} />
			</label>
			{helper && (
				<div className="ml-3 pl-3 text-neutral-3 text-paragraph-sm">
					{helper}
				</div>
			)}
		</ClayForm.Group>
	);
};

export default Input;
