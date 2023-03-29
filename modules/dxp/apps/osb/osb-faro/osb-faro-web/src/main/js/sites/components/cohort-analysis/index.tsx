import CohortChart, {CohortHeatMapType} from 'shared/components/cohort-chart';
import React from 'react';
import {
	formatDate,
	getColorHex,
	getPeriodLabel,
	IntervalType,
	VisitorsType
} from './utils';
import {get} from 'lodash';

type RawHeatMapType = {
	colDimension: string;
	retention: number;
	rowDimension: string;
	rowKey: string;
	value: number;
};

interface ICohortCardProps {
	data: {
		anonymousVisitors: {
			items: RawHeatMapType[];
		};
		knownVisitors: {
			items: RawHeatMapType[];
		};
		visitors: {
			items: RawHeatMapType[];
		};
	};
	interval: IntervalType;
	visitorsType: VisitorsType;
}

export default class CohortCard extends React.Component<ICohortCardProps> {
	formatCohortHeatMap(items) {
		const {interval, visitorsType} = this.props;

		return items.reduce(
			(acc, {colDimension, retention, rowDimension, rowKey, value}) => {
				const period = parseInt(colDimension);

				const item: CohortHeatMapType = {
					colorHex:
						period !== 0
							? getColorHex(retention, visitorsType)
							: null,
					date: rowKey,
					dateLabelFn: (date, abbreviated) =>
						formatDate(date, interval, abbreviated),
					periodLabel: getPeriodLabel(period, interval),
					retention,
					value
				};

				const row = get(acc, rowDimension, []);

				acc[rowDimension] = row.concat(item);

				return acc;
			},
			{}
		);
	}

	getAggregatedCounts(formattedData) {
		return formattedData[0];
	}

	getDateLabels(formattedData) {
		return formattedData.map(row => {
			const {date, dateLabelFn} = row[0];

			return dateLabelFn(date, false);
		});
	}

	getHeatMapData(formattedData) {
		return Object.values(formattedData).slice(1) as CohortHeatMapType[][];
	}

	render() {
		const {data, visitorsType} = this.props;

		const {items} = data[visitorsType];

		const formattedData = this.formatCohortHeatMap(items);

		const heatMapData = this.getHeatMapData(formattedData);

		const aggregatedCounts = this.getAggregatedCounts(formattedData);

		return (
			<CohortChart
				aggregatedCounts={aggregatedCounts}
				data={heatMapData}
				dateLabels={this.getDateLabels(heatMapData)}
				periodLabels={aggregatedCounts.map(
					({periodLabel}) => periodLabel
				)}
			/>
		);
	}
}
