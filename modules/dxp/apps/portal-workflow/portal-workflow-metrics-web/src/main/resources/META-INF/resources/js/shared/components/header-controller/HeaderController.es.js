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

import HeaderMenuBackItem from './HeaderMenuBackItem.es';
import HeaderTitle from './HeaderTitle.es';
import React from 'react';

export default class HeaderController extends React.Component {
	componentWillMount() {
		const {namespace} = this.props;

		const headerContainer = document.getElementById(
			`${namespace}controlMenu`
		);

		if (headerContainer) {
			this.backButtonContainer = headerContainer.querySelector(
				'.sites-control-group .control-menu-nav'
			);
			this.titleContainer = headerContainer.querySelector(
				'.tools-control-group .control-menu-level-1-heading'
			);
		}
	}

	render() {
		const {basePath, title} = this.props;

		return (
			<>
				<HeaderMenuBackItem
					basePath={basePath}
					container={this.backButtonContainer}
				/>

				<HeaderTitle container={this.titleContainer} title={title} />
			</>
		);
	}
}
