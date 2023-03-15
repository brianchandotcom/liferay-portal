import React from 'react';
import SelectDataSource from 'settings/components/SelectDataSource';

const DATA_SOURCE_ARRAY = [
	{
		dataSources: [
			{
				iconName: 'csv-logo',
				iconSize: 'xl',
				name: 'test',
				url: '#'
			},
			{
				iconName: 'csv-logo',
				iconSize: 'xl',
				name: 'test1',
				url: '#'
			},
			{
				iconName: 'liferay-logo',
				iconSize: 'xxl',
				name: 'test 2',
				url: '#'
			},
			{
				iconName: 'csv-logo',
				iconSize: 'xl',
				name: 'testing long text that should wrap',
				url: '#'
			}
		],
		title: 'These are data sources'
	}
];

class SelectDataSourceKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<SelectDataSource sections={DATA_SOURCE_ARRAY} />
			</div>
		);
	}
}

export default SelectDataSourceKit;
