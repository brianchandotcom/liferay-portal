import CriteriaSidebarCollapse from './CriteriaSidebarCollapse.es';
import CriteriaSidebarSearchBar from './CriteriaSidebarSearchBar.es';
import PropTypes from 'prop-types';
import React, {Component} from 'react';

class CriteriaSidebar extends Component {
	static propTypes = {
		editing: PropTypes.bool,
		onTitleClicked: PropTypes.func,
		propertyGroups: PropTypes.array,
		propertyKey: PropTypes.string
	};

	state = {
		searchValue: ''
	};

	_handleOnSearchChange = value => {
		this.setState({searchValue: value});
	}

	_handleOnClickCollapse = (key, editing) => {
		this.props.onTitleClicked(key, editing);
	}

	render() {
		const {propertyGroups, propertyKey} = this.props;

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
						onCollapseClick={this._handleOnClickCollapse}
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