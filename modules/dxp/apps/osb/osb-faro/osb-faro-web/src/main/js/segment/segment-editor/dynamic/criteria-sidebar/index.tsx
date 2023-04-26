import autobind from 'autobind-decorator';
import CriteriaSidebarCollapse from './CriteriaSidebarCollapse';
import CriteriaSidebarSearchBar from './CriteriaSidebarSearchBar';
import Dropdown from 'shared/components/Dropdown';
import React from 'react';
import {List} from 'immutable';
import {Property, PropertyGroup} from 'shared/util/records';

interface ICriteriaSidebarProps {
	propertyGroupsIList: List<PropertyGroup>;
}

interface ICriteriaSidebarState {
	searchValue: string;
	selectedPropertyKey: string;
}

export default class CriteriaSidebar extends React.Component<
	ICriteriaSidebarProps,
	ICriteriaSidebarState
> {
	state = {
		searchValue: '',
		selectedPropertyKey: null
	};

	constructor(props) {
		super(props);

		const {propertyGroupsIList = List<Property>()} = props;

		this.state = {
			...this.state,
			selectedPropertyKey: propertyGroupsIList.getIn([0, 'propertyKey'])
		};
	}

	@autobind
	handlePropertyGroupSelect(event) {
		this.setState({
			selectedPropertyKey: event.target.value
		});
	}

	@autobind
	handleOnSearchChange(value) {
		this.setState({searchValue: value});
	}

	render() {
		const {
			props: {propertyGroupsIList},
			state: {searchValue, selectedPropertyKey}
		} = this;

		const activePropertyGroup = propertyGroupsIList.find(
			({propertyKey}) => propertyKey === selectedPropertyKey
		);

		return (
			<div className='criteria-sidebar-root'>
				<div className='sidebar-header'>
					{activePropertyGroup ? (
						<Dropdown
							buttonProps={{
								borderless: true,
								displayType: 'secondary',
								outline: true
							}}
							label={activePropertyGroup.label}
						>
							{propertyGroupsIList.map(({label, propertyKey}) => (
								<Dropdown.Item
									active={propertyKey === selectedPropertyKey}
									hideOnClick
									key={propertyKey}
									onClick={this.handlePropertyGroupSelect}
									value={propertyKey}
								>
									{label}
								</Dropdown.Item>
							))}
						</Dropdown>
					) : (
						Liferay.Language.get('properties')
					)}
				</div>

				<div className='sidebar-search'>
					<CriteriaSidebarSearchBar
						onChange={this.handleOnSearchChange}
						searchValue={searchValue}
					/>
				</div>

				<div className='sidebar-collapse'>
					<CriteriaSidebarCollapse
						propertyGroupsIList={propertyGroupsIList}
						propertyKey={selectedPropertyKey}
						searchValue={searchValue}
					/>
				</div>
			</div>
		);
	}
}
