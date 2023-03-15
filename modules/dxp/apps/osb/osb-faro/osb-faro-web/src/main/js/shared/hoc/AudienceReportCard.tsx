import AudienceReport from 'shared/components/AudienceReport';
import BaseCard from 'cerebro-shared/components/base-card';
import Card from 'shared/components/Card';
import React from 'react';
import {compose} from 'redux';
import {withError, withLoading} from 'shared/hoc';

interface IAudienceReportCardProps {
	className?: string;
	knownIndividualsTitle: string;
	label: string;
	legacyDropdownRangeKey?: boolean;
	metricAction: React.ReactText;
	segmentsTitle?: string;
	uniqueVisitorsTitle?: string;
}

/**
 * HOC
 * @description Audience Report Card
 * @param {function} withAudienceReportCard
 */
const withAudienceReportCard = withData => {
	const AudienceReportWithData = compose<any>(
		withData(),
		withLoading({alignCenter: true, page: false}),
		withError({page: false})
	)(AudienceReport);

	const AudienceReportCard: React.FC<IAudienceReportCardProps> = ({
		className = 'analytics-audience-report-card',
		knownIndividualsTitle,
		label,
		legacyDropdownRangeKey,
		metricAction,
		segmentsTitle,
		uniqueVisitorsTitle
	}) => (
		<BaseCard
			className={className}
			label={label}
			legacyDropdownRangeKey={legacyDropdownRangeKey}
			minHeight={536}
		>
			{({filters, interval, rangeSelectors, router}) => (
				<Card.Body>
					<AudienceReportWithData
						filters={filters}
						interval={interval}
						knownIndividualsTitle={knownIndividualsTitle}
						metricAction={metricAction}
						rangeSelectors={rangeSelectors}
						router={router}
						segmentsTitle={segmentsTitle}
						uniqueVisitorsTitle={uniqueVisitorsTitle}
					/>
				</Card.Body>
			)}
		</BaseCard>
	);

	return AudienceReportCard;
};

export {withAudienceReportCard};
export default withAudienceReportCard;
