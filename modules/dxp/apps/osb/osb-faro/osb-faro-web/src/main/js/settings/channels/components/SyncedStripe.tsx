import getCN from 'classnames';
import React from 'react';
import {sub} from 'shared/util/lang';

interface ISyncedStripeProps {
	channelsSyncedCount: number;
	sitesSyncedCount: number;
}

function getTitle(sitesSyncedCount: number, channelsSyncedCount: number) {
	let language = null;

	if (sitesSyncedCount === 1) {
		if (channelsSyncedCount === 1) {
			language = Liferay.Language.get(
				'there-is-x-site-and-x-channel-synced-to-this-property'
			);
		} else {
			language = Liferay.Language.get(
				'there-is-x-site-and-x-channels-synced-to-this-property'
			);
		}
	} else {
		if (channelsSyncedCount === 1) {
			language = Liferay.Language.get(
				'there-are-x-sites-and-x-channel-synced-to-this-property'
			);
		} else {
			language = Liferay.Language.get(
				'there-are-x-sites-and-x-channels-synced-to-this-property'
			);
		}
	}

	return sub(language, [sitesSyncedCount, channelsSyncedCount]);
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
			{getTitle(sitesSyncedCount, channelsSyncedCount)}
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
