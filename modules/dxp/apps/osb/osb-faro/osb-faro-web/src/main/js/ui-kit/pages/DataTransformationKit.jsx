import * as API from 'shared/api';
import * as data from 'test/data';
import DataTransformation from 'settings/components/DataTransformation';
import Item from '../components/Item';
import Promise from 'metal-promise';
import React from 'react';
import Row from '../components/Row';
import {noop} from 'lodash';

Object.assign(API.dataSource.fetchMappings, () =>
	Promise.resolve([
		data.mockFieldMapping(1, {
			name: 'foo',
			suggestions: [data.mockFieldMapping(2, {name: 'bar'})]
		})
	])
);

const DefaultDataTransformation = (props = {}) => (
	<DataTransformation
		addAlert={noop}
		groupId='23'
		onSubmit={noop}
		{...props}
	/>
);

export default class DataTransformationKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Item>
						<DefaultDataTransformation />
					</Item>
				</Row>
			</div>
		);
	}
}
