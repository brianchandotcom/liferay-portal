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

import ClayLayout from '@clayui/layout';

import TableChart from '../../components/TableChart';
import i18n from '../../i18n';
import useCompareRuns from './useCompareRuns';

const CompareRunsTeams = () => {
	document.title = i18n.sub('compare-x', 'cases');

	const teams = useCompareRuns('teams');

	return (
		<div className="d-flex flex-wrap mt-5">
			{teams.map(({team, values}, index) => (
				<ClayLayout.Col className="mb-3" key={index} lg={6} md={12}>
					<TableChart
						matrixData={values}
						title={team?.name as string}
					/>
				</ClayLayout.Col>
			))}
		</div>
	);
};

export default CompareRunsTeams;
