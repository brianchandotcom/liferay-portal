/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';

class FilterInput extends Component {
	static propTypes = {
		disableSearch: PropTypes.bool,
		onChange: PropTypes.func,
		onSubmit: PropTypes.func,
		searchBarTerm: PropTypes.string
	};
	static defaultProps = {
		disableSearch: false
	};

	_handleChange = event => {
		event.preventDefault();

		this.props.onChange(event.target.value);
	};

	_handleKeyDown = event => {
		if (event.key === 'Enter' && event.currentTarget.value.trim()) {
			this.props.onSubmit();
		}
	};

	render() {
		const {disableSearch, onSubmit, searchBarTerm} = this.props;

		return (
			<div className="navbar-nav navbar-nav-expand">
				<div className="container-fluid container-fluid-max-xl">
					<div className="input-group">
						<div className="input-group-item">
							<input
								aria-label={Liferay.Language.get('search')}
								className="form-control input-group-inset input-group-inset-after"
								disabled={disableSearch}
								onChange={this._handleChange}
								onKeyDown={this._handleKeyDown}
								placeholder={Liferay.Language.get(
									'contains-text'
								)}
								type="text"
								value={searchBarTerm}
							/>

							<div className="input-group-inset-item input-group-inset-item-after">
								<ClayButton
									displayType="unstyled"
									onClick={onSubmit}
									title={Liferay.Language.get('search-icon')}
								>
									<ClayIcon symbol="search" />
								</ClayButton>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}
}

export default FilterInput;
