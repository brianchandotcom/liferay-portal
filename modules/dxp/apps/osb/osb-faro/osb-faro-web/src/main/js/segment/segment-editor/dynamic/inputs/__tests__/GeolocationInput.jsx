import * as data from 'test/data';
import GeolocationInput from '../GeolocationInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {createCustomValueMap} from '../../utils/custom-inputs';
import {Property} from 'shared/util/records';
import {RelationalOperators, TimeSpans} from '../../utils/constants';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'context/country',
				value: 'foo country'
			},
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'completeDate',
				value: TimeSpans.Last7Days
			},
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'context/region',
				value: 'foo region'
			},
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'context/city',
				value: 'foo city'
			}
		]
	}
]);

const emptyMockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'context/country',
				value: ''
			},
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'completeDate',
				value: TimeSpans.Last7Days
			}
		]
	}
]);

describe('GeolocationInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<GeolocationInput
				onChange={jest.fn()}
				property={new Property(data.mockProperty({}))}
				touched={false}
				valid={false}
				value={emptyMockValue}
			/>
		);
		expect(container).toMatchSnapshot();
	});

	it('should render with has-error', () => {
		const {container} = render(
			<GeolocationInput
				onChange={jest.fn()}
				property={new Property(data.mockProperty({}))}
				touched
				valid={false}
				value={emptyMockValue}
			/>
		);

		expect(container.querySelector('.select-input-root')).toHaveClass(
			'has-error'
		);
	});

	it('should render inputs for all location values if they all have non-zero length values', () => {
		const {container} = render(
			<GeolocationInput
				onChange={jest.fn()}
				property={new Property(data.mockProperty({}))}
				touched={false}
				valid
				value={mockValue}
			/>
		);

		expect(container.querySelectorAll('input').length).toBe(4);
	});

	it('should render the region input as a button if the initial value for the input was empty', () => {
		const {container} = render(
			<GeolocationInput
				onChange={jest.fn()}
				property={new Property(data.mockProperty({}))}
				touched={false}
				valid
				value={createCustomValueMap([
					{
						key: 'criterionGroup',
						value: [
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'context/country',
								value: 'foo country'
							},
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'completeDate',
								value: TimeSpans.Last7Days
							},
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'context/region',
								value: ''
							}
						]
					}
				])}
			/>
		);

		expect(container.querySelectorAll('.button-root')[0]).toHaveTextContent(
			'Add Region'
		);
	});

	it('should render the city input as a button if the initial value for the input was empty', () => {
		const {container} = render(
			<GeolocationInput
				onChange={jest.fn()}
				property={new Property(data.mockProperty({}))}
				touched={false}
				valid
				value={createCustomValueMap([
					{
						key: 'criterionGroup',
						value: [
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'context/country',
								value: 'foo country'
							},
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'completeDate',
								value: TimeSpans.Last7Days
							},
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'context/region',
								value: 'foo region'
							},
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'context/city',
								value: ''
							}
						]
					}
				])}
			/>
		);

		expect(container.querySelector('.button-root')).toHaveTextContent(
			'Add City'
		);
	});
});
