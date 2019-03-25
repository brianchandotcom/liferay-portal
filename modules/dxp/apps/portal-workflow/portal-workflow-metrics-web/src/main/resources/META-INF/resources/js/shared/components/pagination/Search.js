import Icon from '../Icon';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
export default class Search extends React.Component {
	constructor(props) {
		super(props);
		this.state = { value: '' };
		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleChange(event) {
		this.setState({ value: event.target.value });
	}

	handleSubmit(event) {
		const { value } = this.state;
		const { onSearch } = this.props;

		event.preventDefault();

		onSearch(value);
	}

	render() {
		const { disabled } = this.props;
		const { value } = this.state;

		return (
			<form onSubmit={this.handleSubmit} role="search">
				<div className="input-group">
					<div className="input-group-item">
						<input
							className="form-control input-group-inset input-group-inset-after"
							disabled={disabled}
							onChange={this.handleChange}
							placeholder={Liferay.Language.get('search-for')}
							type="text"
							value={value}
						/>
						<span className="input-group-inset-item input-group-inset-item-after">
							<button
								className="btn btn-unstyled"
								disabled={disabled}
								type="submit"
							>
								<Icon iconName="search" />
							</button>
						</span>
					</div>
				</div>
			</form>
		);
	}
}