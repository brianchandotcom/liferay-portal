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

import PropTypes from 'prop-types';
import React, {Component} from 'react';

import {propertyGroupShape} from '../../utils/types.es';
import CriteriaSidebarCollapse from './CriteriaSidebarCollapse.es';
import CriteriaSidebarSearchBar from './CriteriaSidebarSearchBar.es';

class CriteriaSidebar extends Component {
	static propTypes = {
		onTitleClicked: PropTypes.func,
		propertyGroups: PropTypes.arrayOf(propertyGroupShape),
		propertyKey: PropTypes.string
	};

	state = {
		searchValue: ''
	};

	_handleOnSearchChange = value => {
		this.setState({searchValue: value});
	};

	render() {
		const {onTitleClicked, propertyGroups, propertyKey} = this.props;

		const {searchValue} = this.state;

		return (
			<div className="criteria-sidebar-root">
				<div className="sidebar-header">
					{Liferay.Language.get('properties')}
				</div>

				<div className="sidebar-search">
					<CriteriaSidebarSearchBar
						onChange={this._handleOnSearchChange}
						searchValue={searchValue}
					/>
				</div>

				<div className="sidebar-collapse">
					<CriteriaSidebarCollapse
						onCollapseClick={onTitleClicked}
						propertyGroups={propertyGroups}
						propertyKey={propertyKey}
						searchValue={searchValue}
					/>
				</div>
			</div>
		);
	}
}

export default CriteriaSidebar;
