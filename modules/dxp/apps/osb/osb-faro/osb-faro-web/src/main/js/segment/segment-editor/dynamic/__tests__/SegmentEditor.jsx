import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import SegmentEditor, {validateSegmentEditor} from '../index';
import {BrowserRouter} from 'react-router-dom';
import {cleanup, render} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import {Segment} from 'shared/util/records';
import {SegmentStates} from 'shared/util/constants';

jest.mock('segment/segment-editor/dynamic/criteria-sidebar/index');

jest.unmock('react-dom');

describe('SegmentEditor', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DndProvider backend={HTML5Backend}>
						<SegmentEditor channelId='321' groupId='23' />
					</DndProvider>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with error message', () => {
		const {getByText} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DndProvider backend={HTML5Backend}>
						<SegmentEditor
							channelId='321'
							groupId='23'
							segment={data.getImmutableMock(
								Segment,
								data.mockSegment,
								123,
								{
									state: SegmentStates.Disabled
								}
							)}
						/>
					</DndProvider>
				</BrowserRouter>
			</Provider>
		);

		expect(getByText('Error:')).not.toBeNull();
	});
});

describe('validateSegmentEditor', () => {
	const emptyCriteria = {items: []};
	const criteriaWithItems = {items: [{valid: true}]};
	const criteriaWithInvalidItems = {items: [{valid: false}, {valid: true}]};
	const criteriaWithItemsObject = {
		items: [{valid: {bar: true, foo: true}}]
	};
	const criteriaWithInvalidItemsObject = {
		items: [{valid: {bar: false, foo: false}}]
	};

	it.each`
		criteria                          | error
		${null}                           | ${'Empty Fields'}
		${undefined}                      | ${'Empty Fields'}
		${emptyCriteria}                  | ${'Empty Fields'}
		${criteriaWithInvalidItems}       | ${'Empty Fields'}
		${criteriaWithInvalidItemsObject} | ${'Empty Fields'}
		${criteriaWithItemsObject}        | ${undefined}
		${criteriaWithItems}              | ${undefined}
	`('should return $error for $criteria', ({criteria, error}) => {
		expect(validateSegmentEditor(criteria)).toBe(error);
	});
});
