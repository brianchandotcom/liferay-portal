import AudienceReportCard from 'assets/form/hocs/AudienceReportCard';
import DevicesCard from 'assets/form/hocs/DevicesCard';
import FormAbandonmentCard from 'assets/form/hocs/FormAbandonmentCard';
import FormMetricCard from 'assets/form/components/FormMetricCard';
import LocationsCard from 'assets/form/hocs/LocationsCard';
import React from 'react';
import TouchpointsListCard from 'assets/hocs/TouchpointsListCard';
import {ENABLE_FORM_ABANDONMENT} from 'shared/util/constants';

const Overview = () => (
	<>
		<div className='row'>
			<div className='col-sm-12'>
				<FormMetricCard
					label={Liferay.Language.get('visitors-behavior')}
					legacyDropdownRangeKey={false}
				/>
			</div>
		</div>

		<div className='row'>
			<div className='col-sm-12'>
				<AudienceReportCard
					knownIndividualsTitle={Liferay.Language.get(
						'segmented-submissions'
					)}
					label={Liferay.Language.get('audience')}
					legacyDropdownRangeKey={false}
					metricAction={Liferay.Language.get(
						'submission'
					).toLowerCase()}
					segmentsTitle={Liferay.Language.get('submitter-segments')}
					uniqueVisitorsTitle={Liferay.Language.get('submissions')}
				/>
			</div>
		</div>

		<div className='row'>
			<div className='col-lg-6 col-md-12'>
				<LocationsCard
					label={Liferay.Language.get('submissions-by-location')}
					legacyDropdownRangeKey={false}
					metricLabel={Liferay.Language.get('submissions')}
				/>
			</div>

			<div className='col-lg-6 col-md-12'>
				<DevicesCard
					label={Liferay.Language.get('submissions-by-technology')}
					legacyDropdownRangeKey={false}
					metricLabel={Liferay.Language.get('submissions')}
				/>
			</div>
		</div>

		{ENABLE_FORM_ABANDONMENT && (
			<div className='row'>
				<div className='col-sm-12'>
					<FormAbandonmentCard
						label={Liferay.Language.get('form-abandonment')}
						legacyDropdownRangeKey={false}
					/>
				</div>
			</div>
		)}

		<div className='row'>
			<div className='col-sm-12'>
				<TouchpointsListCard
					assetType='FORM'
					label={Liferay.Language.get('asset-appears-on')}
					legacyDropdownRangeKey={false}
				/>
			</div>
		</div>
	</>
);

export default Overview;
