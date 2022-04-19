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

import TableChart from '../../components/TableChart';
import useTableChartData from '../../data/useTableChartData';

const CompareRunsComponents: React.FC = () => {
	const {colors, columns, data} = useTableChartData();

	return (
		<div className="d-flex flex-wrap mt-5">
			<div className="col-6 col-lg-6 col-md-12 mb-3">
				<TableChart
					colors={colors}
					columns={columns}
					data={data}
					title="Number of Case Results"
				/>
			</div>

			<div className="col-6 col-lg-6 col-md-12 mb-3">
				<TableChart
					colors={colors}
					columns={columns}
					data={data}
					title="Number of Case Results"
				/>
			</div>

			<div className="col-6 col-lg-6 col-md-12 mb-3">
				<TableChart
					colors={colors}
					columns={columns}
					data={data}
					title="Number of Case Results"
				/>
			</div>

			<div className="col-6 col-lg-6 col-md-12 mb-3">
				<TableChart
					colors={colors}
					columns={columns}
					data={data}
					title="Number of Case Results"
				/>
			</div>
		</div>
	);
};

export default CompareRunsComponents;
