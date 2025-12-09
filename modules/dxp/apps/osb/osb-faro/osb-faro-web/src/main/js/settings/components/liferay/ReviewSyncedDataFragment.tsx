import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import React from 'react';
import {Text} from '@clayui/core';

const DATA_SOURCE_STATUSES = {
	CONFIGURED: {
		display: 'success',
		label: Liferay.Language.get('connected')
	},
	UNCONFIGURED: {
		display: 'secondary',
		label: Liferay.Language.get('disconnected')
	}
};

const ReviewSyncedDataFragment = ({contactsSelected, sitesSelected}) => {
	const getLabelProps = (selected: boolean) =>
		selected
			? DATA_SOURCE_STATUSES.CONFIGURED
			: DATA_SOURCE_STATUSES.UNCONFIGURED;

	const {display: contactsDisplay, label: contactslabel} = getLabelProps(
		contactsSelected
	);

	const {display: sitesDisplay, label: sitesLabel} = getLabelProps(
		sitesSelected
	);

	return (
		<>
			<div className='mb-2'>
				<Text size={2} weight='semi-bold'>
					{Liferay.Language.get('connection-status').toUpperCase()}
				</Text>
			</div>

			<ClayList>
				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker displayType='unstyled'>
							<ClayIcon
								className='text-secondary'
								symbol='nodes'
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						<ClayList.ItemTitle>
							{Liferay.Language.get('properties')}
						</ClayList.ItemTitle>
						<ClayList.ItemText>
							{Liferay.Language.get(
								'used-to-aggregate-data-on-your-users,-sites-and-dxp-commerce-channels'
							)}
						</ClayList.ItemText>
					</ClayList.ItemField>

					<ClayList.ItemField className='justify-content-center'>
						<ClayLabel displayType={sitesDisplay as any}>
							{sitesLabel}
						</ClayLabel>
					</ClayList.ItemField>
				</ClayList.Item>

				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker displayType='unstyled'>
							<ClayIcon
								className='text-secondary'
								symbol='picture'
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						<ClayList.ItemTitle>
							{Liferay.Language.get('sites')}
						</ClayList.ItemTitle>
						<ClayList.ItemText>
							{Liferay.Language.get(
								'represents-the-sites-synced-from-liferay-portal,-under-dxp-instance-settings-analytics-cloud'
							)}
						</ClayList.ItemText>
					</ClayList.ItemField>

					<ClayList.ItemField className='justify-content-center'>
						<ClayLabel displayType={sitesDisplay as any}>
							{sitesLabel}
						</ClayLabel>
					</ClayList.ItemField>
				</ClayList.Item>

				<ClayList.Item flex>
					<ClayList.ItemField>
						<ClaySticker displayType='unstyled'>
							<ClayIcon
								className='text-secondary'
								symbol='users'
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						<ClayList.ItemTitle>
							{Liferay.Language.get('individuals')}
						</ClayList.ItemTitle>
						<ClayList.ItemText>
							{Liferay.Language.get(
								'represents-the-fields-synced-from-the-contact-object-within-liferay-portal,-under-dxp-instance-settings-analytics-cloud'
							)}
						</ClayList.ItemText>
					</ClayList.ItemField>

					<ClayList.ItemField className='justify-content-center'>
						<ClayLabel displayType={contactsDisplay as any}>
							{contactslabel}
						</ClayLabel>
					</ClayList.ItemField>
				</ClayList.Item>
			</ClayList>
		</>
	);
};

export {ReviewSyncedDataFragment};
