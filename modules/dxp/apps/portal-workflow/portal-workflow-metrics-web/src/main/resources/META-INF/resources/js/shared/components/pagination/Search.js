import { Redirect, withRouter } from 'react-router-dom';
import Icon from '../Icon';
import pathToRegexp from 'path-to-regexp';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
class Search extends React.Component {
	constructor(props) {
		super(props);
		this.state = { redirect: false, value: '' };
		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleChange(event) {
		this.setState({ value: event.target.value });
	}

	handleSubmit(event) {
		event.preventDefault();

		this.setState({ redirect: true });
	}

	render() {
		const { disabled } = this.props;
		const { redirect, value } = this.state;

		if (redirect) {
			const {
				location: { search },
				match: { params, path }
			} = this.props;

			const values = { page: 1 };

			delete params.search;

			if (value && value.trim()) {
				values.search = encodeURIComponent(value);
			}

			const pathname = pathToRegexp.compile(path)(
				Object.assign({}, params, values)
			);

			this.state.redirect = false;

			return <Redirect to={{ pathname, search }} />;
		}

		return (
			<form method="GET" onSubmit={this.handleSubmit} role="search">
				<div className="input-group">
					<div className="input-group-item">
						<input
							className="form-control input-group-inset input-group-inset-after"
							defaultValue={value}
							disabled={disabled}
							onKeyPress={this.handleChange}
							placeholder={Liferay.Language.get('search-for')}
							type="text"
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

export default withRouter(Search);