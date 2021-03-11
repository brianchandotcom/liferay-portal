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

import React, {Component} from 'react';

import {getLocalizedText} from '../utils/utils';
import Icon from './Icon';

class PaneSearchBar extends Component {
	constructor(props) {
		super(props);

		this.onSubmit.bind(this);
	}

	onSubmit(e) {
		e.preventDefault();
	}

	render() {
		const {onLookUp, spritemap} = this.props;

		return (
			<div className="pane-search-bar">
				<form name="searchUser" onSubmit={this.onSubmit}>
					<span>
						<input
							autoComplete="off"
							name="search-user"
							onChange={onLookUp}
							placeholder={`${getLocalizedText('search')}...`}
							tabIndex="4"
							type="text"
						/>
					</span>
					<span>
						<button tabIndex="5" type="submit">
							<Icon spritemap={spritemap} symbol="search" />
						</button>
					</span>
				</form>
			</div>
		);
	}
}

export default PaneSearchBar;
