import autobind from 'autobind-decorator';
import BaseSelect from './BaseSelect';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

class SelectInput extends React.Component {
	static defaultProps = {
		placeholder: ''
	};

	static propTypes = {
		placeholder: PropTypes.string,
		selectedItem: PropTypes.any
	};

	state = {
		query: ''
	};

	_baseSelectRef = React.createRef();

	focus() {
		this._baseSelectRef.current.focus();
	}

	@autobind
	handleInputValueChange(query) {
		this.setState({
			query
		});
	}

	render() {
		const {
			props: {placeholder, selectedItem, ...otherProps},
			state: {query}
		} = this;

		return (
			<BaseSelect
				{...omitDefinedProps(otherProps, SelectInput.propTypes)}
				emptyInputOnInactive
				inputValue={query}
				onInputValueChange={this.handleInputValueChange}
				placeholder={selectedItem ? '' : placeholder}
				ref={this._baseSelectRef}
				selectedItem={selectedItem}
			/>
		);
	}
}

export default SelectInput;
