import client from 'shared/apollo/client';
import Edit from '../Edit';
import mockStore from 'test/mock-store';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {SegmentTypes} from 'shared/util/constants';
import {StaticRouter} from 'react-router';

jest.mock('shared/apollo/client', () => ({
	query: jest.fn()
}));

jest.unmock('react-dom');

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<DndProvider backend={HTML5Backend}>
				<Edit groupId='23' {...props} />
			</DndProvider>
		</StaticRouter>
	</Provider>
);

describe('Edit', () => {
	it('should render', async () => {
		client.query.mockReturnValueOnce(
			Promise.resolve({
				data: {
					eventDefinitions: {
						__typename: 'EventDefinitionBag',
						eventDefinitions: [
							{
								__typename: 'EventDefinition',
								description: null,
								displayName: 'displayName-1',
								id: '1',
								name: 'name-1',
								type: 'DEFAULT'
							}
						],
						total: 1
					}
				}
			})
		);

		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render a dynamic segment', async () => {
		client.query.mockReturnValueOnce(
			Promise.resolve({
				data: {
					eventDefinitions: {
						__typename: 'EventDefinitionBag',
						eventDefinitions: [
							{
								__typename: 'EventDefinition',
								description: null,
								displayName: 'displayName-1',
								id: '1',
								name: 'name-1',
								type: 'DEFAULT'
							}
						],
						total: 1
					}
				}
			})
		);

		const {getByText} = render(
			<DefaultComponent type={SegmentTypes.Dynamic} />
		);

		jest.runAllTimers();

		expect(getByText('Dynamic Segment')).toBeTruthy();
	});

	it('should render a static segment', () => {
		const {getByText} = render(
			<DefaultComponent type={SegmentTypes.Static} />
		);

		jest.runAllTimers();

		expect(getByText('Static Segment')).toBeTruthy();
	});
});
