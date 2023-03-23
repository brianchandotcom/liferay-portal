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

import {useOutletContext} from 'react-router-dom';

import TableChart from '../../components/TableChart';
import i18n from '../../i18n';
import {ApiResponse} from './CompareRunsOutlet';

type ComponentsOutlet = {
	apiResponse: ApiResponse[];
};

const CompareRunsComponents = () => {
	document.title = i18n.sub('compare-x', 'cases');

	const {apiResponse} = useOutletContext<ComponentsOutlet>();

	return (
		<div className="d-flex flex-wrap mt-5">
			{apiResponse.map(({components, values}, index) => {
				return (
					<div className="col-6 col-lg-6 col-md-12 mb-3" key={index}>
						<TableChart
							matrixData={values}
							title={components.name}
						/>
					</div>
				);
			})}
		</div>
	);
};

export default CompareRunsComponents;
