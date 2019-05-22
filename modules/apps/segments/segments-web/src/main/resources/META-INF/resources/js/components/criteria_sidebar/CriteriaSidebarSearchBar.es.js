import ClayButton from '../shared/ClayButton.es';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

class CriteriaSidebarSearchBar extends Component {
	static propTypes = {
		onChange: PropTypes.func.isRequired,
		searchValue: PropTypes.string
	};

	_handleChange = event => {
		this.props.onChange(event.target.value);
	};

	_handleClear = event => {
		event.preventDefault();

		this.props.onChange('');
	};

	render() {
		const {searchValue} = this.props;

		return (
			<div className='input-group'>
				<div className='input-group-item'>
					<input
						className='form-control input-group-inset input-group-inset-after'
						data-testid='search-input'
						onChange={this._handleChange}
						placeholder={Liferay.Language.get('search')}
						type='text'
						value={searchValue}
					/>

					<div className='input-group-inset-item input-group-inset-item-after'>
						<ClayButton
							data-testid='search-button'
							iconName={searchValue ? 'times' : 'search'}
							onClick={
								searchValue ? this._handleClear : undefined
							}
							style='unstyled'
						/>
					</div>
				</div>
			</div>
		);
	}
}

export default CriteriaSidebarSearchBar;
