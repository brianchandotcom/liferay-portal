import * as data from 'test/data';
import CriteriaCard from '../index';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {Segment} from 'shared/util/records';

jest.unmock('react-dom');

describe('CriteriaCard', () => {
	const innerHeight = window.innerHeight;

	afterEach(() => {
		cleanup();

		window.innerHeight = innerHeight;
	});

	const mockSegment = data.getImmutableMock(Segment, data.mockSegment, 0, {
		referencedObjects: {
			fieldMappings: {
				individual: {
					demographics: {
						firstName: {
							context: 'demographics',
							id: null,
							name: 'firstName',
							ownerType: 'individual',
							propertyKey: '',
							rawType: 'text',
							type: 'text'
						}
					}
				}
			}
		}
	});

	it('should render', () => {
		const {container} = render(
			<CriteriaCard
				criteriaString={"demographics/firstName/value eq 'Test'"}
				segment={mockSegment}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ anonymous label', () => {
		const {queryByText} = render(
			<CriteriaCard
				criteriaString={"demographics/name/value eq 'Test'"}
				includeAnonymousUsers
				segment={mockSegment}
			/>
		);

		expect(queryByText('Include Anonymous')).toBeTruthy();
	});

	it('should render w/ truncation', () => {
		window.innerHeight = 0;

		const {queryByText} = render(
			<CriteriaCard
				criteriaString={"demographics/name/value eq 'Test'"}
				includeAnonymousUsers
				segment={mockSegment}
			/>
		);

		expect(queryByText('View All Criteria')).toBeTruthy();
	});
});
