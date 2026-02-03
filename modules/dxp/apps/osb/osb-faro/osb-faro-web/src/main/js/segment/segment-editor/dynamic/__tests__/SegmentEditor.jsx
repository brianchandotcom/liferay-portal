import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import SegmentEditor, {validateSegmentEditor} from '../index';
import {BrowserRouter} from 'react-router-dom';
import {
	cleanup,
	fireEvent,
	render,
	screen,
	waitFor
} from '@testing-library/react';
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
						<SegmentEditor
							channelId='321'
							groupId='23'
							type='BATCH'
						/>
					</DndProvider>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with error message', () => {
		render(
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
							type='BATCH'
						/>
					</DndProvider>
				</BrowserRouter>
			</Provider>
		);

		expect(screen.getByText('Error:')).not.toBeNull();
	});

	it('renders the realtime segment with sequencial card disabled', () => {
		render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DndProvider backend={HTML5Backend}>
						<SegmentEditor
							channelId='321'
							groupId='23'
							type='REAL_TIME'
						/>
					</DndProvider>
				</BrowserRouter>
			</Provider>
		);

		expect(screen.getByText('Order')).toBeInTheDocument();
		expect(screen.getByText('Enabled Sequencial')).toBeInTheDocument();
		expect(
			screen.getByText(
				'When this is enabled, Event 2 must occur after Event 1, with any number of events in between. When this is disabled, events can be completed in any order. Nested criteria are not supported.'
			)
		).toBeInTheDocument();

		expect(
			screen.getByText(
				'Drag and drop criterion from the right to add rules.'
			)
		).toBeInTheDocument();
		expect(
			screen.getByText(
				'Drag and drop over an existing criteria to form groups.'
			)
		).toBeInTheDocument();
	});

	it('renders the realtime segment with sequencial card and user enable it', async () => {
		render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<DndProvider backend={HTML5Backend}>
						<SegmentEditor
							channelId='321'
							groupId='23'
							type='REAL_TIME'
						/>
					</DndProvider>
				</BrowserRouter>
			</Provider>
		);

		expect(screen.getByText('Order')).toBeInTheDocument();
		expect(screen.getByText('Enabled Sequencial')).toBeInTheDocument();

		expect(
			screen.getByText(
				'When this is enabled, Event 2 must occur after Event 1, with any number of events in between. When this is disabled, events can be completed in any order. Nested criteria are not supported.'
			)
		).toBeInTheDocument();

		fireEvent.click(screen.getByText('Enabled Sequencial'));

		await waitFor(() => {
			expect(
				screen.queryByText(
					'Drag and drop criterion from the right to add rules.'
				)
			).toBeInTheDocument();

			expect(
				screen.queryByText(
					'Drag and drop over an existing criteria to form groups.'
				)
			).not.toBeInTheDocument();
		});
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
