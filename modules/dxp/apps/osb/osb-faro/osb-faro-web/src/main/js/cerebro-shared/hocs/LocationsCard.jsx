import BaseCard from 'shared/components/base-card';
import Card from 'shared/components/Card';
import GeoMap from 'shared/components/geo-map/GeoMapCard';
import React from 'react';
import {compose} from 'redux';
import {HOC_CARD_PROPTYPES} from 'shared/util/proptypes';
import {PropTypes} from 'prop-types';
import {withEmpty, withError, withLoading} from 'shared/hoc/util';

/**
 * HOC
 * @description Locations Card Data
 */
const withLocationsCard = (
	withLocations,
	withCountries,
	{documentationTitle, documentationUrl, title}
) => {
	const LocationsGeoMap = compose(
		withLocations(),
		withCountries(),
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
						href={documentationUrl}
						key='DOCUMENTATION'
						target='_blank'
					>
						{documentationTitle}
					</a>
				</>
			),
			title
		})
	)(GeoMap);

	LocationsGeoMap.propTypes = HOC_CARD_PROPTYPES;

	const defaultProps = {
		className: 'analytics-locations-card',
		metricLabel: Liferay.Language.get('views')
	};

	const propTypes = {
		metricLabel: PropTypes.string
	};

	const LocationsCard = ({
		className,
		label,
		legacyDropdownRangeKey,
		metricLabel
	}) => (
		<BaseCard
			className={className}
			label={label}
			legacyDropdownRangeKey={legacyDropdownRangeKey}
			minHeight={536}
		>
			{({filters, interval, rangeSelectors, router}) => (
				<Card.Body>
					<LocationsGeoMap
						filters={filters}
						height={400}
						interval={interval}
						metricLabel={metricLabel}
						rangeSelectors={rangeSelectors}
						router={router}
						width='calc(60% - 2rem)'
					/>
				</Card.Body>
			)}
		</BaseCard>
	);

	LocationsCard.defaultProps = defaultProps;
	LocationsCard.propTypes = propTypes;

	return LocationsCard;
};

export {withLocationsCard};
export default withLocationsCard;
