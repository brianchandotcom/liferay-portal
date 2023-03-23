/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {Link} from 'react-router-dom';

import Container from '../../components/Layout/Container';
import QATable from '../../components/Table/QATable';
import TableChart from '../../components/TableChart';
import i18n from '../../i18n';
import {TestrayRun} from '../../services/rest';
import {ApiResponse} from './CompareRunsOutlet';

type CompareRunsDetailsProps = {
	matrixData: ApiResponse;
	runs: TestrayRun[];
};

const CompareRunDetails: React.FC<CompareRunsDetailsProps> = ({
	matrixData,
	runs = [],
}) => {
	document.title = i18n.sub('compare-x', 'cases');

	const [runA, runB] = runs;

	const getRun = (
		run: TestrayRun,
		runTitle: string,
		{divider}: {divider?: boolean} = {divider: false}
	) => {
		if (!run) {
			return [];
		}

		const project = run.build?.project;

		return [
			{
				title: `${i18n.translate('run')} ${runTitle}`,
				value: (
					<Link
						to={`/project/${project?.id}/routines/${run?.build?.routine?.id}/build/${run?.build?.id}/runs`}
					>
						{run.id}
					</Link>
				),
			},
			{
				title: i18n.translate('project-name'),
				value: (
					<Link to={`/project/${project?.id}`}>{project?.name}</Link>
				),
			},
			{
				title: i18n.translate('build'),
				value: (
					<Link
						to={`/project/${project?.id}/routines/${run?.build?.routine?.id}/build/${run?.build?.id}`}
					>
						{run?.build?.name}
					</Link>
				),
			},
			{
				divider,
				title: i18n.translate('environment'),
				value: run.name,
			},
		];
	};

	return (
		<Container collapsable title={i18n.sub('compare-x', 'details')}>
			<div className="d-flex flex-wrap">
				<div className="col-8 col-lg-8 col-md-12">
					<QATable
						items={[
							...getRun(runA, 'A', {divider: true}),
							...getRun(runB, 'B'),
						]}
					/>
				</div>

				<div className="col-4 col-lg-4 col-md-12 pb-5">
					<TableChart
						matrixData={matrixData.values}
						title="Number of Case Results"
					/>
				</div>
			</div>
		</Container>
	);
};

export default CompareRunDetails;
