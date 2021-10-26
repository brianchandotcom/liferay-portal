import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import {useField} from 'formik';
import {required, validate} from '../utils/validations.form';

const Select = ({groupStyle, helper, label, validations, ...props}) => {
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
			className={classNames(groupStyle, 'w-100', {
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
				<ClaySelectWithOption {...field} {...props} />
			</label>
			{helper && <div>{helper}</div>}
		</ClayForm.Group>
	);
};

export default Select;
