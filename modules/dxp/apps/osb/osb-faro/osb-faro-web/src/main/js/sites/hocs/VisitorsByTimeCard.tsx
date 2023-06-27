import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import CardWithRangeKey from 'shared/hoc/CardWithRangeKey';
import ChartTooltip, {
	Alignments,
	Weights
} from 'shared/components/chart-tooltip';
import HeatmapChart from 'shared/components/HeatmapChart';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';
import ReactDOMServer from 'react-dom/server';
import URLConstants from 'shared/util/url-constants';
import VisitorsByTimeQuery from 'shared/queries/VisitorsByTimeQuery';
import {compose} from 'shared/hoc';
import {graphql} from '@apollo/react-hoc';
import {IBasePageContext} from 'shared/types';
import {
	mapPropsToOptions,
	mapResultToProps
} from './mappers/visitors-by-time-query';
import {sub} from 'shared/util/lang';
import {withEmpty, withError, withLoading} from 'shared/hoc';

export const formatHour = (hour: string) => {
	const hourAsNumber = parseInt(hour);
	const suffix = hourAsNumber >= 12 ? 'PM' : 'AM';
	let hourDisplay = hourAsNumber;

	if (hourAsNumber === 0) {
		hourDisplay = 12;
	} else if (hourAsNumber > 12) {
		hourDisplay = hourAsNumber - 12;
	}

	return `${hourDisplay} ${suffix}`;
};

export const renderTooltip = ({column, row, value}) =>
	ReactDOMServer.renderToString(
		<ChartTooltip
			header={[
				{
					columns: [
						{
							label: `${column} - ${formatHour(row)}`,
							weight: Weights.Semibold
						}
					]
				}
			]}
			rows={[
				{
					columns: [
						{
							align: Alignments.Center,
							label: sub(Liferay.Language.get('x-visitors'), [
								value.toLocaleString()
							]) as string
						}
					]
				}
			]}
		/>
	);

const HeatmapChartWithData = compose<any>(
	graphql(VisitorsByTimeQuery, {
		options: mapPropsToOptions,
		props: mapResultToProps
	}),
	withLoading(),
	withError({page: false}),
	withEmpty({
		description: (
			<>
				<span className='mr-1'>
					{Liferay.Language.get(
						'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
					)}
				</span>

				<a
					href={URLConstants.SitesDashboardVisitorsByDayAndTime}
					key='DOCUMENTATION'
					target='_blank'
				>
					{Liferay.Language.get(
						'learn-more-about-visitors-by-day-and-time'
					)}
				</a>
			</>
		),
		title: Liferay.Language.get(
			'there-are-no-visitors-on-the-selected-period'
		)
	})
)(HeatmapChart);

interface IVisitorsByTimeCardProps {
	className: string;
	label: string;
}

const VisitorsByTimeCard: React.FC<IVisitorsByTimeCardProps> = ({
	className,
	label
}) => {
	const {router} = useContext(
		BasePage.Context as React.Context<IBasePageContext>
	);

	return (
		<CardWithRangeKey
			className={className}
			label={label}
			legacyDropdownRangeKey={false}
		>
			{({rangeSelectors}) => (
				<Card.Body>
					<HeatmapChartWithData
						columnAxisFormatter={col => col.slice(0, 3)}
						rangeSelectors={rangeSelectors}
						renderTooltip={renderTooltip}
						router={router}
						rowAxisFormatter={formatHour}
					/>
				</Card.Body>
			)}
		</CardWithRangeKey>
	);
};

VisitorsByTimeCard.propTypes = {
	className: PropTypes.string,
	label: PropTypes.string
};

export default VisitorsByTimeCard;
