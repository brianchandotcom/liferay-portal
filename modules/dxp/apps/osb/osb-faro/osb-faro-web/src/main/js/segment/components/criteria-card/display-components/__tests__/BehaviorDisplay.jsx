import * as data from 'test/data';
import BehaviorDisplay from '../BehaviorDisplay';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {
	CustomFunctionOperators,
	PropertyTypes,
	RelationalOperators,
	TimeSpans
} from 'segment/segment-editor/dynamic/utils/constants';
import {List, Map} from 'immutable';
import {Property, Segment} from 'shared/util/records';
import {withReferencedObjectsProvider} from 'segment/segment-editor/dynamic/context/referencedObjects';

jest.unmock('react-dom');

describe('BehaviorDisplay', () => {
	const WrappedBehaviorDisplay = withReferencedObjectsProvider(
		BehaviorDisplay
	);

	const mockSegment = data.getImmutableMock(Segment, data.mockSegment, 0, {
		referencedObjects: {
			assets: {
				123: {
					description: null,
					id: '123',
					name: 'Cool beans Page',
					type: 'Page',
					url: 'https://www.liferay.com'
				}
			}
		}
	});

	const mockCriterion = {
		operatorName: CustomFunctionOperators.ActivitiesFilterByCount,
		propertyName: 'activityKey',
		value: Map({
			criterionGroup: Map({
				items: List([
					Map({
						operatorName: RelationalOperators.EQ,
						propertyName: 'activityKey',
						value: 'Page#pageViewed#123'
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
		label: 'Viewed Page',
		name: 'pageViewed',
		propertykey: 'web',
		type: PropertyTypes.Behavior
	});

	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<WrappedBehaviorDisplay
				criterion={mockCriterion}
				property={mockProperty}
				segment={mockSegment}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
