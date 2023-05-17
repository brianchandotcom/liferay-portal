import Item from './Item';
import React from 'react';

export type CohortHeatMapType = {
	colorHex: string | null;
	date: string;
	dateLabelFn: (string, boolean) => string;
	periodLabel: string | string[];
	retention: number;
	value: number;
};

interface ICohortChartProps {
	aggregatedCounts: CohortHeatMapType[];
	data: CohortHeatMapType[][];
	dateLabels: string[];
	periodLabels: string[];
}

export default class CohortChart extends React.Component<ICohortChartProps> {
	renderAggregated() {
		const {aggregatedCounts} = this.props;

		const aggregatedVisitorsCount = aggregatedCounts[0].value;

		return (
			<tr>
				<th />

				<th className='visitors table-column-text-end'>
					{aggregatedVisitorsCount.toLocaleString()}
				</th>

				{aggregatedCounts.map(({retention}, i) => (
					<th className='table-column-text-center' key={i}>
						{`${retention.toFixed(2)}%`}
					</th>
				))}
			</tr>
		);
	}

	renderPeriods() {
		const {periodLabels} = this.props;

		return (
			<tr>
				<th />

				<th className='visitors-header table-column-text-end'>
					{Liferay.Language.get('visitors')}
				</th>

				{periodLabels.map(periodLabel => (
					<th
						className='period table-column-text-center'
						key={periodLabel}
					>
						{periodLabel}
					</th>
				))}
			</tr>
		);
	}

	renderRow(row, rowIndex) {
		const {dateLabels} = this.props;

		const rowVisitorsCount = row[0].value;

		return (
			<tr key={rowIndex}>
				<td className='interval'>{dateLabels[rowIndex]}</td>

				<td className='visitors table-column-text-end'>
					{rowVisitorsCount.toLocaleString()}
				</td>

				{row.map(
					(
						{
							colorHex,
							date,
							dateLabelFn,
							periodLabel,
							retention,
							value
						},
						i
					) => (
						<Item
							colorHex={colorHex}
							date={date}
							dateLabelFn={dateLabelFn}
							key={`${rowIndex}-${i}`}
							periodLabel={periodLabel}
							retention={retention}
							value={value}
						/>
					)
				)}
			</tr>
		);
	}

	render() {
		const {data} = this.props;

		return (
			<table className='cohort-chart-root'>
				<thead>
					{this.renderPeriods()}

					{this.renderAggregated()}
				</thead>

				<tbody>{data.map((row, i) => this.renderRow(row, i))}</tbody>
			</table>
		);
	}
}
