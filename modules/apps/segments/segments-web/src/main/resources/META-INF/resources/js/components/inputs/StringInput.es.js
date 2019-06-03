import ClaySelect from 'components/shared/ClaySelect.es';
import propTypes from 'prop-types';
import React from 'react';

class StringInput extends React.Component {
	static propTypes = {
		disabled: propTypes.bool,
		onChange: propTypes.func.isRequired,
		options: propTypes.array,
		value: propTypes.oneOfType([propTypes.string, propTypes.number])
	};

	static defaultProps = {
		options: []
	};

	_handleChange = event => {
		this.props.onChange({value: event.target.value});
	};

	render() {
		const {disabled, options, value} = this.props;

		return options.length === 0 ? (
			<input
				className='criterion-input form-control'
				data-testid='simple-string'
				disabled={disabled}
				onChange={this._handleChange}
				type='text'
				value={value}
			/>
		) : (
			<ClaySelect
				className='criterion-input form-control'
				data-testid='options-string'
				disabled={disabled}
				onChange={this._handleChange}
				options={options.map(o => ({
					label: o.label,
					value: o.value
				}))}
				selected={value}
			/>
		);
	}
}

export default StringInput;
