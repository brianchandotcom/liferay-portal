/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
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
			<div className="input-group">
				<div className="input-group-item">
					<input
						className="form-control input-group-inset input-group-inset-after"
						data-testid="search-input"
						onChange={this._handleChange}
						placeholder={Liferay.Language.get('search')}
						type="text"
						value={searchValue}
					/>

					<div className="input-group-inset-item input-group-inset-item-after">
						<ClayButton
							data-testid="search-button"
							displayType="unstyled"
							onClick={
								searchValue ? this._handleClear : undefined
							}
						>
							<ClayIcon
								symbol={searchValue ? 'times' : 'search'}
							/>
						</ClayButton>
					</div>
				</div>
			</div>
		);
	}
}

export default CriteriaSidebarSearchBar;
