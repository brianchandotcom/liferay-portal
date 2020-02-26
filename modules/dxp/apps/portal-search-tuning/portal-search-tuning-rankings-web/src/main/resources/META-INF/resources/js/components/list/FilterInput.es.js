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
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
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
			<ClayManagementToolbar.Search>
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
								displayType="unstyled"
								onClick={onSubmit}
								title={Liferay.Language.get('search-icon')}
							>
								<ClayIcon symbol="search" />
							</ClayButton>
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayManagementToolbar.Search>
		);
	}
}

export default FilterInput;
