/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Body, Cell, Head, Row, Table, Text} from '@clayui/core';
import {ClayToggle} from '@clayui/form';
import {useModal} from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	TRecommendationConfiguration,
	fetchRecommendationConfiguration,
	updateRecommendationConfiguration,
} from '../../utils/api';
import {useRequest} from '../../utils/useRequest';
import StateRenderer from '../StateRenderer';
import DisableJobModal from './DisableJobModal';
import {Job, jobs as initialJobs} from './jobs';

const header: {
	displayText?: boolean;
	key: string;
	value?: string;
	width?: string;
}[] = [
	{
		key: 'modelName',
		value: Liferay.Language.get('model-name'),
	},
	{
		key: 'description',
		value: Liferay.Language.get('description'),
	},
	{
		key: 'type',
		value: Liferay.Language.get('type'),
		width: '104px',
	},
	{
		displayText: false,
		key: 'toggle',
		value: Liferay.Language.get('toggle'),
		width: '80px',
	},
];

interface IRecommendationsContentProps {
	data: TRecommendationConfiguration | null;
}

const RecommendationsContent: React.FC<IRecommendationsContentProps> = ({
	data,
}) => {
	const [jobs, setJobs] = useState<Job[]>(initialJobs);
	const [selectedJobIndex, setSelectedJobIndex] = useState<number>(-1);
	const {observer, onOpenChange, open} = useModal();

	useEffect(() => {
		if (data) {
			setJobs(
				initialJobs.map((job: Job) => ({
					...job,
					active: data[job.id],
				}))
			);
		}
	}, [data]);

	const handleDisableJob = async (index: number, message: string) => {
		const updatedJobs = [...jobs];

		updatedJobs[index] = {
			...updatedJobs[index],
			active: !updatedJobs[index].active,
		};

		const recomendationConfiguration: TRecommendationConfiguration | {} =
			updatedJobs.reduce((acc, cur) => {
				return {
					...acc,
					[cur.id]: cur.active,
				};
			}, {});

		const {ok} = await updateRecommendationConfiguration(
			recomendationConfiguration as TRecommendationConfiguration
		);

		if (ok) {
			setJobs(updatedJobs);

			Liferay.Util.openToast({
				message: sub(message, [updatedJobs[index].title]),
			});
		}
	};

	return (
		<>
			<Table className="table-bordered" columnsVisibility={false}>
				<Head>
					{header.map(({displayText = true, key, value, width}) => (
						<Cell key={key} textValue={value} width={width}>
							{displayText ? value : ''}
						</Cell>
					))}
				</Head>

				<Body>
					{jobs.map(
						({active, description, id, title, type}, index) => (
							<Row data-testid={id} key={id}>
								<Cell>
									<Text size={3} weight="bold">
										{title}
									</Text>
								</Cell>

								<Cell>
									<Text size={3}>{description}</Text>
								</Cell>

								<Cell>
									<Text size={3}>{type}</Text>
								</Cell>

								<Cell id="toggle" textValue={title}>
									<ClayToggle
										onToggle={async () => {
											if (jobs[index].active) {
												onOpenChange(true);
												setSelectedJobIndex(index);

												return;
											}
											else {
												handleDisableJob(
													index,
													Liferay.Language.get(
														'x-was-updated-successfully'
													)
												);

												return;
											}
										}}
										toggled={active}
									/>
								</Cell>
							</Row>
						)
					)}
				</Body>
			</Table>

			{open && (
				<DisableJobModal
					observer={observer}
					onCancel={() => {
						setSelectedJobIndex(-1);
						onOpenChange(false);
					}}
					onDisable={() => {
						handleDisableJob(
							selectedJobIndex,
							Liferay.Language.get('x-was-disabled-successfully')
						);

						setSelectedJobIndex(-1);
						onOpenChange(false);
					}}
					title={jobs?.[selectedJobIndex]?.modalTitle ?? ''}
				/>
			)}
		</>
	);
};

const Recommendations: React.FC = () => {
	const {data, error, loading} = useRequest<TRecommendationConfiguration>(
		fetchRecommendationConfiguration
	);

	return (
		<StateRenderer empty={!data} error={error} loading={loading}>
			<StateRenderer.Success>
				<RecommendationsContent data={data} />
			</StateRenderer.Success>
		</StateRenderer>
	);
};

export default Recommendations;
