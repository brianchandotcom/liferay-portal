/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Link} from 'react-router-dom';

import Container from '../../components/Layout/Container';
import QATable from '../../components/Table/QATable';
import TableChart from '../../components/TableChart';
import i18n from '../../i18n';
import {TestrayRun} from '../../services/rest';

type CompareRunsDetailsProps = {
	matrixData: number[][];
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
					<Link to={`/project/${project?.id}/routines`}>
						{project?.name}
					</Link>
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
				value: run.name.replaceAll('|', ' + '),
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
						matrixData={matrixData}
						title={i18n.translate('number-of-case-results')}
					/>
				</div>
			</div>
		</Container>
	);
};

export default CompareRunDetails;
