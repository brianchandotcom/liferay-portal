import * as data from 'test/data';
import OutputVersionsCard from '../OutputVersionsCard';
import React from 'react';
import {JobRunFrequencies, JobRunStatuses} from 'shared/util/constants';
import {MockedProvider} from '@apollo/react-testing';
import {mockRecommendationJobRunsReq} from 'test/graphql-data';
import {render} from '@testing-library/react';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

describe('OutputVersionsCard', () => {
	it('should render', async () => {
		const {container} = render(
			<MockedProvider
				mocks={[
					mockRecommendationJobRunsReq([
						data.mockRecommendationJobRun(0),
						data.mockRecommendationJobRun(1, {
							status: JobRunStatuses.Failed
						}),
						data.mockRecommendationJobRun(2, {
							status: JobRunStatuses.Published
						}),
						data.mockRecommendationJobRun(3, {
							status: JobRunStatuses.Running
						})
					])
				]}
			>
				<OutputVersionsCard
					nextRunDate={new Date()}
					router={{params: {jobId: '321'}}}
					runFrequency={JobRunFrequencies.Every14Days}
				/>
			</MockedProvider>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render w/o "Next X" date', async () => {
		const {queryByText} = render(
			<MockedProvider
				mocks={[
					mockRecommendationJobRunsReq([
						data.mockRecommendationJobRun(0),
						data.mockRecommendationJobRun(1, {
							status: JobRunStatuses.Failed
						}),
						data.mockRecommendationJobRun(2, {
							status: JobRunStatuses.Published
						}),
						data.mockRecommendationJobRun(3, {
							status: JobRunStatuses.Running
						})
					])
				]}
			>
				<OutputVersionsCard
					router={{params: {jobId: '321'}}}
					runFrequency={JobRunFrequencies.Every14Days}
				/>
			</MockedProvider>
		);

		jest.runAllTimers();

		expect(queryByText(/Next/)).toBeNull();
	});
});
