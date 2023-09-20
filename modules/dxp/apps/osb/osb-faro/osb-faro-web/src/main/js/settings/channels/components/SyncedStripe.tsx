import getCN from 'classnames';
import React from 'react';
import {sub} from 'shared/util/lang';

interface ISyncedStripeProps {
	channelsSyncedCount: number;
	sitesSyncedCount: number;
}

const SyncedStripe: React.FC<ISyncedStripeProps> = ({
	channelsSyncedCount,
	sitesSyncedCount
}) => (
	<div
		className={getCN('sites-synced-stripe-root', {
			empty: !sitesSyncedCount && !channelsSyncedCount
		})}
	>
		<div className='title d-flex align-items-center'>
			{sub(
				sitesSyncedCount === 1
					? Liferay.Language.get(
							'there-is-x-site(s)-and-x-channel(s)-synced-to-this-property'
					  )
					: Liferay.Language.get(
							'there-are-x-site(s)-and-x-channel(s)-synced-to-this-property'
					  ),
				[sitesSyncedCount, channelsSyncedCount]
			)}
		</div>

		<div>
			{sub(
				Liferay.Language.get(
					'manage-sites-synced-to-this-property-by-going-to-x-in-your-dxp-instance'
				),
				[
					<b key='INSTANCE_SETTINGS'>
						{Liferay.Language.get(
							'instance-settings-analytics-cloud-fragment'
						)}
					</b>
				],
				false
			)}
		</div>
	</div>
);

export default SyncedStripe;
