import React, {Component} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '../shared/ClayButton.es';

class CriteriaSidebarSearchBar extends Component {
	static propTypes = {
		onChange: PropTypes.func,
		searchValue: PropTypes.string
	};

	_handleChange = event => {
		this.props.onChange(event.target.value);
	}

	_handleClear = event => {
		event.preventDefault();

		this.props.onChange('');
	}

	render() {
		const {searchValue} = this.props;

		return (
			<div className="input-group">
				<div className="input-group-item">
					<input
						className="form-control input-group-inset input-group-inset-after"
						onChange={this._handleChange}
						placeholder={Liferay.Language.get('search')}
						type="text"
						value={searchValue}
					/>

					<div className="input-group-inset-item input-group-inset-item-after">
						{searchValue ?
							<ClayButton
								iconName="times"
								onClick={this._handleClear}
								style="unstyled"
							/> :
							<ClayButton
								iconName="search"
								style="unstyled"
							/>
						}
					</div>
				</div>
			</div>
		);
	}
}

export default CriteriaSidebarSearchBar;