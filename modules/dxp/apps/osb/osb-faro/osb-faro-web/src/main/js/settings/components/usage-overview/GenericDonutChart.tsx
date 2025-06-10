import ClayLayout from '@clayui/layout';
import React from 'react';
import {Cell, Label, Pie, PieChart, ResponsiveContainer} from 'recharts';

const PIE_COLOR = '#80ACFF';

const PIE_DIMENSIONS = {
	endAngle: 235,
	innerRadius: 50,
	outerRadius: 70,
	startAngle: 90
};

export const GenericDonutChart = ({capacity, measurement}) => {
	const data = [{value: 10}];

	return (
		<>
			<ClayLayout.Col className='d-flex mt-4 donut-chart-item' xl={4}>
				<ResponsiveContainer width='50%'>
					<PieChart>
						<Pie
							cornerRadius={5}
							data={[{value: 10}]}
							dataKey='value'
							endAngle={PIE_DIMENSIONS.endAngle}
							fill={PIE_COLOR}
							innerRadius={PIE_DIMENSIONS.innerRadius}
							outerRadius={PIE_DIMENSIONS.outerRadius}
							startAngle={PIE_DIMENSIONS.startAngle}
						>
							{data.map((entry, index) => (
								<Cell key={`cell-${index}`} stroke='none' />
							))}
							<Label
								className='generic-pie-label'
								position='center'
								value='##'
							/>
						</Pie>
					</PieChart>
				</ResponsiveContainer>
				<div className='d-flex flex-column justify-content-center'>
					<h4>{capacity}</h4>
					<h4>{measurement}</h4>
				</div>
			</ClayLayout.Col>
		</>
	);
};
