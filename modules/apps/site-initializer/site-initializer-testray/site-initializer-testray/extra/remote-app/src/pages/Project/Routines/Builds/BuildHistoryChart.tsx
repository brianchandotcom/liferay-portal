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

import ClayChart from '@clayui/charts';
import i18n from '~/i18n';
import {TestrayBuild} from '~/services/rest';
import {DATA_COLORS, Statuses} from '~/util/constants';

type BuildHistoryChartProps = {
	builds: TestrayBuild[];
};

const BuildHistoryChart: React.FC<BuildHistoryChartProps> = ({builds}) => (
	<div className="graph-container graph-container-sm">
		<ClayChart
			axis={{
				x: {
					label: {
						position: 'outer-center',
						text: i18n.translate('builds-ordered-by-date'),
					},
				},
				y: {
					label: {
						position: 'outer-middle',
						text: i18n.translate('tests').toUpperCase(),
					},
				},
			}}
			bar={{
				width: {
					max: 30,
				},
			}}
			data={{
				colors: {
					[Statuses.BLOCKED]: DATA_COLORS['metrics.blocked'],
					[Statuses.FAILED]: DATA_COLORS['metrics.failed'],
					[Statuses.INCOMPLETE]: DATA_COLORS['metrics.incomplete'],
					[Statuses.PASSED]: DATA_COLORS['metrics.passed'],
					[Statuses.TEST_FIX]: DATA_COLORS['metrics.testfix'],
				},
				columns: [
					[
						Statuses.PASSED,
						...builds.map(({caseResultPassed = 0}) =>
							Number(caseResultPassed)
						),
					],
					[
						Statuses.FAILED,
						...builds.map(({caseResultFailed = 0}) =>
							Number(caseResultFailed)
						),
					],
					[
						Statuses.BLOCKED,
						...builds.map(({caseResultBlocked = 0}) =>
							Number(caseResultBlocked)
						),
					],
					[
						Statuses.TEST_FIX,
						...builds.map(({caseResultTestFix = 0}) =>
							Number(caseResultTestFix)
						),
					],
					[
						Statuses.INCOMPLETE,
						...builds.map(
							({
								caseResultInProgress = 0,
								caseResultUntested = 0,
							}) =>
								Number(caseResultInProgress) +
								Number(caseResultUntested)
						),
					],
				],
				stack: {
					normalize: true,
				},
				type: 'area',
			}}
			legend={{
				inset: {
					anchor: 'top-right',
					step: 1,
					x: 10,
					y: -30,
				},
				item: {
					tile: {
						height: 12,
						width: 12,
					},
				},
				position: 'inset',
			}}
			padding={{bottom: 5, top: 30}}
		/>
	</div>
);

export default BuildHistoryChart;
