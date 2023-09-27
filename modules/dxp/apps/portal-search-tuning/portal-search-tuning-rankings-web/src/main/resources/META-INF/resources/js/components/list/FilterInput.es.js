/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ManagementToolbar} from 'frontend-js-components-web';
import {PropTypes} from 'prop-types';
import React, {Component} from 'react';

class FilterInput extends Component {
	static propTypes = {
		disableSearch: PropTypes.bool,
		onChange: PropTypes.func,
		onSubmit: PropTypes.func,
		searchBarTerm: PropTypes.string,
	};
	static defaultProps = {
		disableSearch: false,
	};

	_handleChange = (event) => {
		event.preventDefault();

		this.props.onChange(event.target.value);
	};

	_handleKeyDown = (event) => {
		if (event.key === 'Enter' && event.currentTarget.value.trim()) {
			this.props.onSubmit();
		}
	};

	render() {
		const {disableSearch, onSubmit, searchBarTerm} = this.props;

		return (
			<ManagementToolbar.Search>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							aria-label={Liferay.Language.get('search')}
							className="form-control input-group-inset input-group-inset-after"
							disabled={disableSearch}
							onChange={this._handleChange}
							onKeyDown={this._handleKeyDown}
							placeholder={Liferay.Language.get('contains-text')}
							type="text"
							value={searchBarTerm}
						/>

						<ClayInput.GroupInsetItem after tag="span">
							<ClayButton
								aria-label={Liferay.Language.get('search-icon')}
								displayType="unstyled"
								onClick={onSubmit}
								title={Liferay.Language.get('search-icon')}
							>
								<ClayIcon symbol="search" />
							</ClayButton>
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ManagementToolbar.Search>
		);
	}
}

export default FilterInput;
