/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';

import TableChart from '../../components/TableChart';
import i18n from '../../i18n';
import useCompareRuns from './useCompareRuns';

const CompareRunsComponents = () => {
	document.title = i18n.sub('compare-x', 'cases');

	const components = useCompareRuns('components');

	return (
		<div className="d-flex flex-wrap mt-5">
			{components.map(({component, values}, index) => (
				<ClayLayout.Col className="mb-3" key={index} lg={6} md={12}>
					<TableChart
						matrixData={values}
						title={component?.name as string}
					/>
				</ClayLayout.Col>
			))}
		</div>
	);
};

export default CompareRunsComponents;
