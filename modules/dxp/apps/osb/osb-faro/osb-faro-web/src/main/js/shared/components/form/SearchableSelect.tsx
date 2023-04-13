import autobind from 'autobind-decorator';
import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import PropTypes from 'prop-types';
import React from 'react';
import SearchableSelect from '../SearchableSelect';
import Select from '../Select';
import {FieldProps} from 'formik';
import {noop} from 'lodash';

interface IFormSearchableSelectProps
	extends FieldProps,
		React.HTMLAttributes<HTMLElement> {
	initialValue: any;
	label: React.ReactNode;
	onSearchChange: (value: string) => void;
	onSelect: (value: any) => void;
}

class FormSearchableSelect extends React.Component<IFormSearchableSelectProps> {
	static defaultProps = {
		onSearchChange: noop
	};

	static propTypes = {
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
		initialValue: PropTypes.any,
		label: PropTypes.node,
		onSearchChange: PropTypes.func,
		onSelect: PropTypes.func
	};

	@autobind
	handleSelect(value) {
		const {
			field: {name},
			form: {setFieldValue},
			onSelect
		} = this.props;

		setFieldValue(name, value);

		if (onSelect) {
			onSelect(value);
		}
	}

	render() {
		const {
			className,
			field,
			form,
			label,
			onSearchChange,
			...otherProps
		} = this.props;

		const {name, value} = field;

		const error = form.errors[name];
		const touched = form.touched[name];

		const classes = getCN(className, {
			'has-error': error && touched,
			'has-success': !error && touched
		});

		return (
			<SearchableSelect
				{...omitDefinedProps(
					otherProps,
					FormSearchableSelect.propTypes
				)}
				className={classes}
				inputPlaceholder={Liferay.Language.get('search')}
				label={label}
				name={name}
				onSearchChange={onSearchChange}
				onSelect={this.handleSelect}
				selectedItem={value}
			/>
		);
	}
}

export default Object.assign(FormSearchableSelect, {Item: Select.Item});
