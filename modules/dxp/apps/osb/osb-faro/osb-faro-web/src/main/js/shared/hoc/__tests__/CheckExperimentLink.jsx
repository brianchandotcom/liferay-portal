import CheckExperimentLink from '../CheckExperimentLink';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {EXPERIMENT_ROOT_QUERY} from 'experiments/queries/ExperimentQuery';
import {MockedProvider} from '@apollo/react-testing';

jest.unmock('react-dom');

const RenderText = () => 'Wrapped component text';

export function mockExperimentRootReq() {
	return {
		request: {
			query: EXPERIMENT_ROOT_QUERY,
			variables: {
				experimentId: '456'
			}
		},
		result: {
			data: {
				experiment: {
					__typename: 'Experiment',
					channelId: '123',
					id: '456',
					name: 'Test 5',
					pageURL: 'http://172.16.11.12:8090/web/guest/4',
					publishable: false,
					status: 'TERMINATED'
				}
			}
		}
	};
}

describe('CheckExperimentLink', () => {
	afterEach(cleanup);

	it('should render a wrapped component', () => {
		const WrappedComponent = CheckExperimentLink(RenderText);

		const {getByText} = render(
			<WrappedComponent
				location={{
					pathname:
						'/workspace/faro-dev-liferay/123/tests/overview/456'
				}}
			/>
		);

		expect(getByText('Wrapped component text')).toBeTruthy();
	});

	it('should request and replace url if channelId is not in location', () => {
		const spy = jest.fn();
		const WrappedComponent = CheckExperimentLink(RenderText);

		render(
			<MockedProvider mocks={[mockExperimentRootReq()]}>
				<WrappedComponent
					groupId='faro-dev-liferay'
					history={{replace: spy}}
					location={{
						pathname:
							'/workspace/faro-dev-liferay/tests/overview/456'
					}}
				/>
			</MockedProvider>
		);

		jest.runAllTimers();

		expect(spy).toBeCalledWith(
			'/workspace/faro-dev-liferay/123/tests/overview/456'
		);
	});
});
