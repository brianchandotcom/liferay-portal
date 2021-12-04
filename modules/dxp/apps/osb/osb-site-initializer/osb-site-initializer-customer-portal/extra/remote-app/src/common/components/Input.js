import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useField} from 'formik';
import {required, validate} from '../utils/validations.form';
import WarningBadge from './WarningBadge';

const Input = ({groupStyle, helper, label, validations, ...props}) => {
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
				{`${label} `}

				{props.required && (
					<span className="inline-item-after reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				)}

				<ClayInput {...field} {...props} />
			</label>

			{meta.error && meta.touched ? (
				<WarningBadge>
					<span className="pl-1">{meta.error}</span>
				</WarningBadge>
			) : (
				helper && (
					<div className="ml-3 pl-3 text-neutral-3 text-paragraph-sm">
						{helper}
					</div>
				)
			)}
		</ClayForm.Group>
	);
};

export default Input;
