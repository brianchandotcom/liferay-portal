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

import ClayIcon from '@clayui/icon';
import React from 'react';
import {withRouter} from 'react-router-dom';

import TabItem from './TabItem.es';

class Tabs extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			expanded: false,
		};
	}

	hideNavbar() {
		this.setState({
			expanded: false,
		});
	}

	toggleExpanded() {
		this.setState({
			expanded: !this.state.expanded,
		});
	}

	render() {
		const {expanded} = this.state;
		const {
			location: {pathname},
			tabs,
		} = this.props;

		const isActive = tab => pathname.includes(tab.key);

		const activeTab = tabs.filter(isActive)[0] || {};

		let navbarClassName = '';
		let togglerClassName = 'collapsed';

		if (expanded) {
			navbarClassName = 'show';
			togglerClassName = '';
		}

		return (
			<nav className="navbar navbar-collapse-absolute navbar-expand-md navbar-underline navigation-bar navigation-bar-secondary">
				<div className="container-fluid container-fluid-max-xl">
					<button
						aria-expanded={expanded}
						aria-label={Liferay.Language.get('toggle-navigation')}
						className={`${togglerClassName} navbar-toggler navbar-toggler-link`}
						onClick={this.toggleExpanded.bind(this)}
					>
						{activeTab.name}

						<ClayIcon symbol="caret-bottom" />
					</button>

					<div
						className={`collapse navbar-collapse ${navbarClassName}`}
					>
						<div className="container-fluid container-fluid-max-xl">
							<ul
								className="navbar-nav"
								data-testid="tabElements"
							>
								{tabs.map((tab, index) => (
									<li
										className="nav-item"
										key={index}
										onClick={this.hideNavbar.bind(this)}
									>
										<TabItem
											{...tab}
											active={isActive(tab)}
										/>
									</li>
								))}
							</ul>
						</div>
					</div>
				</div>
			</nav>
		);
	}
}

export default withRouter(Tabs);
export {Tabs};
