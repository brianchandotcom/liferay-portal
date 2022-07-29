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
import useTableChartData from '../../data/useTableChartData';
import i18n from '../../i18n';
import {TestrayRun} from '../../services/rest';

const CompareRunDetails: React.FC<{runs: TestrayRun[]}> = ({runs = []}) => {
	const {colors, columns, data} = useTableChartData();

	const [runA, runB] = runs;

	const getRun = (
		run: TestrayRun,
		{divider}: {divider?: boolean} = {divider: false}
	) => {
		if (!run) {
			return [];
		}

		const {
			build: {project, ...build},
		} = run;

		return [
			{
				title: `${i18n.translate('run')} A`,
				value: <Link to="">{run.id}</Link>,
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
					<Link to={`/project/${project?.id}/build/${build.id}`}>
						{build.name}
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
		<Container collapsable title={i18n.translate('compare-details')}>
			<div className="d-flex flex-wrap">
				<div className="col-8 col-lg-8 col-md-12">
					<QATable
						items={[
							...getRun(runA, {divider: true}),
							...getRun(runB),
						]}
					/>
				</div>

				<div className="col-4 col-lg-4 col-md-12 pb-5">
					<TableChart
						colors={colors}
						columns={columns}
						data={data}
						title="Number of Case Results"
					/>
				</div>
			</div>
		</Container>
	);
};

export default CompareRunDetails;
