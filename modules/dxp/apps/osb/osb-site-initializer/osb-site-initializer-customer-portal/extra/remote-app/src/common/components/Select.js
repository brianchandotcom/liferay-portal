import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useField} from 'formik';
import {required, validate} from '../utils/validations.form';
import WarningBadge from './WarningBadge';

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

	const getStyleStatus = () => {
		if (meta.touched) {
			return meta.error ? ' has-error' : ' has-success';
		}

		return '';
	};

	return (
		<ClayForm.Group
			className={`w-100${getStyleStatus()} ${
				groupStyle ? groupStyle : ''
			}`}
		>
			<label>
				{`${label} `}

				{props.required && (
					<span className="inline-item-after reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				)}

				<div className="position-relative">
					<ClayIcon className="select-icon" symbol="caret-bottom" />

					<ClaySelectWithOption {...field} {...props} />
				</div>
			</label>

			{meta.touched && meta.error && props.required && (
				<WarningBadge>
					<span className="pl-1">{meta.error}</span>
				</WarningBadge>
			)}

			{helper && <div>{helper}</div>}
		</ClayForm.Group>
	);
};

export default Select;
