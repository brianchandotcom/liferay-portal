import * as data from 'test/data';
import mockStore from 'test/mock-store';
import ModalRenderer from 'shared/components/ModalRenderer';
import React from 'react';
import {BrowserRouter, StaticRouter} from 'react-router-dom';
import {fireEvent, render} from '@testing-library/react';
import {INDIVIDUALS} from 'shared/util/router';
import {Provider} from 'react-redux';
import {Segment} from 'shared/util/records';
import {SegmentTypes} from 'shared/util/constants';
import {StaticSegmentEdit} from '../Static';

jest.unmock('react-dom');

let isShowingNavigationWarning;

window.confirm = () => {
	isShowingNavigationWarning = true;
};

describe('StaticSegmentEdit', () => {
	beforeEach(() => {
		isShowingNavigationWarning = false;
	});

	it('should render', () => {
		const {container} = render(
			<BrowserRouter>
				<Provider store={mockStore()}>
					<StaticSegmentEdit groupId='23' type={INDIVIDUALS} />
				</Provider>
			</BrowserRouter>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	xit('should render a navigation warning', () => {
		const {getByText} = render(
			<BrowserRouter>
				<Provider store={mockStore()}>
					<ModalRenderer />

					<StaticSegmentEdit
						groupId='23'
						segment={data.getImmutableMock(
							Segment,
							data.mockSegment,
							1,
							{
								segmentType: SegmentTypes.Static
							}
						)}
						type={INDIVIDUALS}
					/>
				</Provider>
			</BrowserRouter>
		);

		jest.runAllTimers();

		fireEvent.click(getByText('Add Members'));
		jest.runAllTimers();

		fireEvent.click(getByText('Foo Bar'));
		fireEvent.click(getByText('Add'));
		jest.runAllTimers();

		fireEvent.click(getByText('Cancel'));
		jest.runAllTimers();

		expect(isShowingNavigationWarning).toBeTruthy();
	});

	it('should not render a navigation warning after doing anything', () => {
		const {getByText} = render(
			<StaticRouter>
				<Provider store={mockStore()}>
					<ModalRenderer />

					<StaticSegmentEdit
						groupId='23'
						segment={data.getImmutableMock(
							Segment,
							data.mockSegment,
							1,
							{
								segmentType: SegmentTypes.Static
							}
						)}
						type={INDIVIDUALS}
					/>
				</Provider>
			</StaticRouter>
		);

		jest.runAllTimers();

		fireEvent.click(getByText('Cancel'));

		expect(isShowingNavigationWarning).not.toBeTruthy();
	});
});
