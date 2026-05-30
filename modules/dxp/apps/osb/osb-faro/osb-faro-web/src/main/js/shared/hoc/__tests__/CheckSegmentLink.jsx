import * as API from 'shared/api';
import CheckSegmentLink from '../CheckSegmentLink';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter} from 'react-router-dom';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const mockNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useNavigate: () => mockNavigate
}));

const RenderText = () => 'Wrapped component text';

const renderAt = (ui, pathname) =>
	render(<MemoryRouter initialEntries={[pathname]}>{ui}</MemoryRouter>);

describe('CheckSegmentLink', () => {
	afterEach(() => {
		cleanup();
		mockNavigate.mockClear();
	});

	it('should render', () => {
		const WrappedComponent = CheckSegmentLink(RenderText);

		const {getByText} = renderAt(
			<WrappedComponent />,
			'/workspace/faro-dev-liferay/123/contacts/segments/456'
		);

		expect(getByText('Wrapped component text')).toBeTruthy();
	});

	it('should request and replace url if channelId is not in location', async () => {
		const WrappedComponent = CheckSegmentLink(RenderText);

		API.individualSegment.fetch.mockReturnValueOnce(
			Promise.resolve({channelId: 123, id: 456})
		);

		const {container} = renderAt(
			<WrappedComponent groupId='faro-dev-liferay' />,
			'/workspace/faro-dev-liferay/contacts/segments/456'
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(mockNavigate).toBeCalledWith(
			'/workspace/faro-dev-liferay/123/contacts/segments/456',
			{replace: true}
		);
	});
});
