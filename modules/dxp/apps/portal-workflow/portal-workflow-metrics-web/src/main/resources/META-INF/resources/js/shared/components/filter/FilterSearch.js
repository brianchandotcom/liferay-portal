import React, { Fragment } from 'react';
import Icon from '../Icon';

export default class FilterSearch extends React.Component {
	render() {
		const { children, onChange, totalCount } = this.props;

		const searchEnabled = totalCount > 12;

		return (
			<Fragment>
				{searchEnabled && (
					<form>
						<div className="dropdown-section">
							<div className="input-group input-group-sm">
								<div className="input-group-item">
									<input
										className="form-control input-group-inset input-group-inset-after"
										onChange={onChange}
										placeholder={Liferay.Language.get('search-for')}
										type="text"
									/>

									<span className="input-group-inset-item input-group-inset-item-after">
										<button className="btn btn-unstyled" type="button">
											<Icon iconName="search" />
										</button>
									</span>
								</div>
							</div>
						</div>
					</form>
				)}

				{children}
			</Fragment>
		);
	}
}