import CriteriaRow from 'components/criteria_builder/CriteriaRow.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';
import {PROPERTY_TYPES, SUPPORTED_PROPERTY_TYPES} from 'utils/constants.es';

const connectDnd = jest.fn(el => el);

describe('CriteriaRow', () => {
	afterEach(cleanup);

	it('should render', () => {
		const OriginalCriteriaRow = CriteriaRow.DecoratedComponent;

		const {asFragment} = render(
			<OriginalCriteriaRow
				connectDragPreview={connectDnd}
				connectDragSource={connectDnd}
				connectDropTarget={connectDnd}
				criterion={{
					operatorName:
						SUPPORTED_PROPERTY_TYPES[PROPERTY_TYPES.STRING],
					propertyName: 'test_prop',
					value: 'test_val'
				}}
				groupId='group_01'
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				onMove={jest.fn()}
				propertyKey='user'
				supportedProperties={[
					{
						label: 'Test Property',
						name: 'test_prop',
						type: PROPERTY_TYPES.STRING
					}
				]}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render and inform there is an unknown property', () => {
		const OriginalCriteriaRow = CriteriaRow.DecoratedComponent;

		const {asFragment} = render(
			<OriginalCriteriaRow
				connectDragPreview={connectDnd}
				connectDragSource={connectDnd}
				connectDropTarget={connectDnd}
				criterion={{
					operatorName:
						SUPPORTED_PROPERTY_TYPES[PROPERTY_TYPES.STRING],
					propertyName: 'unknown_prop',
					value: 'test_val'
				}}
				groupId='group_01'
				index={0}
				onAdd={jest.fn()}
				onChange={jest.fn()}
				onDelete={jest.fn()}
				onMove={jest.fn()}
				propertyKey='user'
				supportedProperties={[
					{
						label: 'Test Property',
						name: 'test_prop',
						type: PROPERTY_TYPES.STRING
					}
				]}
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
