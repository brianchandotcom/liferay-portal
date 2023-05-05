import AudienceReportCard from 'shared/components/audience-report/AudienceReportBaseCard';
import BlogMetricCard from 'assets/blog/components/BlogMetricCard';
import DevicesCard from 'assets/blog/hocs/DevicesCard';
import LocationsCard from 'assets/blog/hocs/LocationsCard';
import React from 'react';
import TouchpointsListCard from 'assets/hocs/TouchpointsListCard';
import {MetricName} from 'shared/types/MetricName';
import {Name} from 'shared/components/audience-report/types';

const Overview = () => (
	<>
		<div className='row'>
			<div className='col-sm-12'>
				<BlogMetricCard
					label={Liferay.Language.get('visitors-behavior')}
				/>
			</div>
		</div>

		<div className='row'>
			<div className='col-sm-12'>
				<AudienceReportCard
					knownIndividualsTitle={Liferay.Language.get(
						'segmented-views'
					)}
					query={{
						metricName: MetricName.Views,
						name: Name.Blog
					}}
					uniqueVisitorsTitle={Liferay.Language.get('views')}
				/>
			</div>
		</div>

		<div className='row'>
			<div className='col-lg-6 col-md-12'>
				<LocationsCard
					label={Liferay.Language.get('views-by-location')}
					legacyDropdownRangeKey={false}
				/>
			</div>

			<div className='col-lg-6 col-md-12'>
				<DevicesCard
					label={Liferay.Language.get('views-by-technology')}
					legacyDropdownRangeKey={false}
				/>
			</div>
		</div>

		<div className='row'>
			<div className='col-sm-12'>
				<TouchpointsListCard
					assetType='BLOG'
					label={Liferay.Language.get('asset-appears-on')}
					legacyDropdownRangeKey={false}
				/>
			</div>
		</div>
	</>
);

export default Overview;
