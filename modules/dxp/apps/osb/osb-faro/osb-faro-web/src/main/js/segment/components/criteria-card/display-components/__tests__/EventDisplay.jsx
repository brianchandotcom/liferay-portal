import * as data from 'test/data';
import EventDisplay from '../EventDisplay';
import React from 'react';
import {
	CustomFunctionOperators,
	FunctionalOperators,
	PropertyTypes,
	RelationalOperators,
	TimeSpans
} from 'segment/segment-editor/dynamic/utils/constants';
import {DataTypes, EventTypes} from 'event-analysis/utils/types';
import {List, Map} from 'immutable';
import {Property, Segment} from 'shared/util/records';
import {render} from '@testing-library/react';
import {withReferencedObjectsProvider} from 'segment/segment-editor/dynamic/context/referencedObjects';

jest.unmock('react-dom');

describe('EventDisplay', () => {
	const WrappedEventDisplay = withReferencedObjectsProvider(EventDisplay);

	const mockSegment = data.getImmutableMock(Segment, data.mockSegment, 0, {
		referencedObjects: {
			attributes: {
				1: {
					dataType: DataTypes.String,
					displayName: 'Foo Attribute String',
					id: '1'
				}
			},
			events: {
				123: {
					description: null,
					displayName: 'Downloaded Document',
					id: '123',
					name: 'downloadedDocument',
					type: EventTypes.Global
				}
			}
		}
	});

	const mockCriterion = {
		operatorName: CustomFunctionOperators.EventsFilterByCount,
		propertyName: 'eventDefinitionId',
		value: Map({
			criterionGroup: Map({
				items: List([
					Map({
						operatorName: RelationalOperators.EQ,
						propertyName: 'eventDefinitionId',
						value: '123'
					}),
					Map({
						operatorName: FunctionalOperators.Contains,
						propertyName: 'attribute/1',
						value: 'Test String'
					}),
					Map({
						operatorName: RelationalOperators.GT,
						propertyName: 'day',
						value: TimeSpans.Last24Hours
					})
				])
			}),
			operator: RelationalOperators.GE,
			value: 2
		})
	};

	const mockProperty = data.getImmutableMock(Property, data.mockProperty, 1, {
		entityName: 'Individual',
		label: 'Downloaded Document',
		name: 'documentDownloaded',
		propertykey: 'event',
		type: PropertyTypes.Event
	});

	it('renders', () => {
		const {container} = render(
			<WrappedEventDisplay
				criterion={mockCriterion}
				property={mockProperty}
				segment={mockSegment}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
