import * as data from 'test/data';
import CriteriaView from '../CriteriaView';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {Segment} from 'shared/util/records';
import {withReferencedObjectsProvider} from 'segment/segment-editor/dynamic/context/referencedObjects';

jest.unmock('react-dom');

describe('CriteriaView', () => {
	afterEach(cleanup);

	it('should render', () => {
		const WrappedCriteriaView = withReferencedObjectsProvider(CriteriaView);
		const {container} = render(
			<WrappedCriteriaView
				criteria={data.mockNewCriteria(1, {
					propertyName: 'demographics/firstName/value'
				})}
				segment={data.getImmutableMock(Segment, data.mockSegment, 0, {
					referencedObjects: {
						fieldMappings: {
							individual: {
								demographics: {
									firstName: {
										context: 'demographics',
										id: null,
										name: 'firstName',
										ownerType: 'individual',
										rawType: 'Text',
										type: 'Text'
									}
								}
							}
						}
					}
				})}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ Non-Existent Property message', () => {
		const {queryByText} = render(
			<CriteriaView criteria={data.mockNewCriteria()} />
		);

		expect(queryByText('Attribute no longer exists.')).toBeTruthy();
	});
});
