import AudienceReport from './AudienceReport';
import BaseCard from 'shared/components/base-card';
import Card from '../Card';
import React from 'react';
import {AssetAudienceReportQuery, PageAudienceReportQuery} from './queries';
import {IAudienceReportBaseCardProps, Name} from './types';

function AudienceReportBaseCard({
	query: {metricName, name},
	...props
}: IAudienceReportBaseCardProps) {
	const AudienceReportQuery =
		name === Name.Page ? PageAudienceReportQuery : AssetAudienceReportQuery;

	return (
		<BaseCard
			className='analytics-audience-report-card'
			label={Liferay.Language.get('audience')}
			legacyDropdownRangeKey={false}
			minHeight={536}
		>
			{({filters, rangeSelectors}) => (
				<Card.Body>
					<AudienceReport
						{...props}
						filters={filters}
						mapper={result => result?.[name]?.[metricName]}
						name={name}
						Query={AudienceReportQuery({
							metricName,
							name
						})}
						rangeSelectors={rangeSelectors}
					/>
				</Card.Body>
			)}
		</BaseCard>
	);
}

export default AudienceReportBaseCard;
