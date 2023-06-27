import ClayButton from '@clayui/button';
import client from 'shared/apollo/client';
import Form from 'shared/components/form';
import Loading, {Align} from 'shared/components/Loading';
import Modal from 'shared/components/modal';
import React from 'react';
import RecommendationActivitiesQuery from 'settings/recommendations/queries/RecommendationActivitiesQuery';
import RecommendationJobRunsMonthlyStatisticsQuery from 'settings/recommendations/queries/RecommendationJobRunsMonthlyStatisticsQuery';
import {
	Filter,
	getPropertiesFromItems,
	Job,
	JOB_RUN_DATA_PERIODS_LIST,
	JOB_RUN_DATA_PERIODS_RANGE_KEY_MAP,
	JobProperty
} from 'settings/recommendations/utils/utils';
import {get} from 'lodash';
import {JobRunDataPeriods} from 'shared/util/constants';
import {useQuery} from '@apollo/react-hooks';

const ACTIVITIES_THRESHOLD = 1000;

interface IManuallyRetrainModelModalProps {
	job: Job;
	onClose: () => void;
	onSubmit: () => void;
}

const ManuallyRetrainModelModal: React.FC<IManuallyRetrainModelModalProps> = ({
	job,
	onClose,
	onSubmit
}) => {
	const {data, loading} = useQuery(
		RecommendationJobRunsMonthlyStatisticsQuery,
		{
			variables: {
				jobId: get(job, 'id')
			}
		}
	);

	const itemFilters: Filter[] = get(job, 'parameters', []).filter(
		({name}) => name !== 'includePreviousPeriod'
	);

	const propertyFilters: JobProperty[] = getPropertiesFromItems(itemFilters);

	const validateActivitiesCount = (
		JobRunDataPeriod: JobRunDataPeriods
	): Promise<string> => {
		let error = '';

		return client
			.query({
				query: RecommendationActivitiesQuery,
				variables: {
					applicationId: 'Page',
					eventContextPropertyFilters: propertyFilters,
					eventId: 'pageUnloaded',
					rangeKey:
						JOB_RUN_DATA_PERIODS_RANGE_KEY_MAP[JobRunDataPeriod],
					size: 0,
					start: 0
				}
			})
			.then(
				({
					data: {
						activities: {total}
					}
				}) => {
					if (total < ACTIVITIES_THRESHOLD) {
						error = Liferay.Language.get(
							'the-interaction-period-does-not-meet-the-1000-event-minimum-required-to-train-the-model.-please-add-pages-or-increase-the-period'
						);
					}

					return error;
				}
			);
	};

	const availableJobRuns = get(
		data,
		['jobRunsMonthlyStatistics', 'availableJobRuns'],
		0
	);

	return (
		<Modal className='manually-retrain-model-modal-root'>
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('manually-retrain-model')}
			/>

			<Form
				initialValues={{runDataPeriod: get(job, 'runDataPeriod')}}
				onSubmit={onSubmit}
			>
				{({handleSubmit}) => (
					<Form.Form>
						<Modal.Body>
							<div className='description'>
								{Liferay.Language.get(
									'select-an-interaction-period-for-the-selected-items'
								)}
							</div>

							<Form.Group>
								<Form.GroupItem>
									<Form.Select
										label={Liferay.Language.get(
											'select-interaction-period'
										)}
										name='runDataPeriod'
										validate={validateActivitiesCount}
									>
										{JOB_RUN_DATA_PERIODS_LIST.map(
											({name, value}) => (
												<Form.Select.Item
													key={value}
													value={value}
												>
													{name}
												</Form.Select.Item>
											)
										)}
									</Form.Select>
								</Form.GroupItem>
							</Form.Group>

							<div className='training-allowance'>
								<div className='title'>
									{Liferay.Language.get(
										'monthly-training-allowance'
									)}
								</div>

								<div className='d-flex'>
									{`${Liferay.Language.get('scheduled')}:`}

									<span className='count'>
										{loading ? (
											<Loading align={Align.Right} />
										) : (
											get(
												data,
												[
													'jobRunsMonthlyStatistics',
													'scheduledJobRuns'
												],
												0
											)
										)}
									</span>
								</div>

								<div className='d-flex'>
									{`${Liferay.Language.get('remaining')}:`}

									<span className='count'>
										{loading ? (
											<Loading align={Align.Right} />
										) : (
											availableJobRuns
										)}
									</span>
								</div>
							</div>
						</Modal.Body>

						<Modal.Footer>
							<ClayButton
								className='button-root'
								displayType='secondary'
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								className='button-root'
								disabled={availableJobRuns <= 0}
								displayType='primary'
								onClick={() => handleSubmit()}
							>
								{Liferay.Language.get('retrain')}
							</ClayButton>
						</Modal.Footer>
					</Form.Form>
				)}
			</Form>
		</Modal>
	);
};

export default ManuallyRetrainModelModal;
