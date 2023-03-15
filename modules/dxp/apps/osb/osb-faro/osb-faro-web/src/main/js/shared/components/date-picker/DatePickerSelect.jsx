import getCN from 'classnames';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';

export default class DatePickerSelect extends Component {
	static propTypes = {
		className: PropTypes.string,
		onChange: PropTypes.func.isRequired,
		options: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.oneOfType([
					PropTypes.number,
					PropTypes.string
				]),
				value: PropTypes.oneOfType([PropTypes.number, PropTypes.string])
					.isRequired
			})
		).isRequired,
		selected: PropTypes.string
	};

	render() {
		const {className, options, selected, ...otherProps} = this.props;

		const classes = getCN(
			'date-picker-select-root',
			'form-control',
			className
		);

		return (
			<select className={classes} {...otherProps} value={selected}>
				{options.map(({label, value}, index) => (
					<option key={index} value={value}>
						{label ? label : value}
					</option>
				))}
			</select>
		);
	}
}
