import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import Label from '@clayui/label';
import React from 'react';
import {sub} from 'shared/util/lang';

type connectionStatus = 'connected' | 'disconnected';

interface IDemandbaseEntities {
	accountConnectionStatus: connectionStatus;
	accountsSyncedCount?: number;
	buyingCommitteeConnectionStatus: connectionStatus;
	buyingCommitteeSyncedCount?: number;
	customAttributesConnectionStatus: connectionStatus;
	customAttributesSyncedCount?: number;
	intentDataConnectionStatus: connectionStatus;
	intentDataSyncedCount?: number;
	loading?: boolean;
}

type entity = {
	entitiesSyncedCount?: number;
	entityConnectionStatus?: connectionStatus;
	entityDescription: string;
	entitySymbol: string;
	entityTitle: string;
};

const EntityItem = ({
	entitiesSyncedCount,
	entityConnectionStatus = 'disconnected',
	entityDescription,
	entitySymbol,
	entityTitle
}: entity) => (
	<ClayList.Item flex>
		<ClayList.ItemField>
			<ClaySticker displayType='unstyled'>
				<ClayIcon className='text-secondary' symbol={entitySymbol} />
			</ClaySticker>
		</ClayList.ItemField>

		<ClayList.ItemField expand>
			<ClayList.ItemTitle>{entityTitle}</ClayList.ItemTitle>

			<ClayList.ItemText>{entityDescription}</ClayList.ItemText>

			{entitiesSyncedCount >= 0 && (
				<ClayList.ItemText>
					{sub(Liferay.Language.get('x-items-synced'), [
						entitiesSyncedCount
					])}
				</ClayList.ItemText>
			)}
		</ClayList.ItemField>
		<ClayList.ItemField className='justify-content-center' expand>
			<Label
				className='ml-auto'
				displayType={
					entityConnectionStatus === 'connected'
						? 'success'
						: 'secondary'
				}
			>
				{entityConnectionStatus === 'connected'
					? Liferay.Language.get('connected')
					: Liferay.Language.get('disconnected')}
			</Label>
		</ClayList.ItemField>
	</ClayList.Item>
);

const DemandbaseEntities: React.FC<IDemandbaseEntities> = ({
	accountConnectionStatus,
	accountsSyncedCount,
	buyingCommitteeConnectionStatus,
	buyingCommitteeSyncedCount,
	customAttributesConnectionStatus,
	customAttributesSyncedCount,
	intentDataConnectionStatus,
	intentDataSyncedCount
}) => {
	const entitiesList = [
		{
			entitiesSyncedCount: accountsSyncedCount,
			entityConnectionStatus: accountConnectionStatus,
			entityDescription: Liferay.Language.get(
				'represents-fields-from-the-account-object-within-demandbase'
			),
			entitySymbol: 'briefcase',
			entityTitle: Liferay.Language.get('accounts')
		},
		{
			entitiesSyncedCount: intentDataSyncedCount,
			entityConnectionStatus: intentDataConnectionStatus,
			entityDescription: Liferay.Language.get(
				'represents-signals-showing-account-interest-in-relevant-topics'
			),
			entitySymbol: 'seo',
			entityTitle: Liferay.Language.get('intent-data')
		},
		{
			entitiesSyncedCount: buyingCommitteeSyncedCount,
			entityConnectionStatus: buyingCommitteeConnectionStatus,
			entityDescription: Liferay.Language.get(
				'represents-fields-from-the-buying-committee-object-within-demandbase'
			),
			entitySymbol: 'users',
			entityTitle: Liferay.Language.get('buying-committee')
		},
		{
			entitiesSyncedCount: customAttributesSyncedCount,
			entityConnectionStatus: customAttributesConnectionStatus,
			entityDescription: Liferay.Language.get(
				'represents-fields-from-the-custom-attributes-object-within-demandbase'
			),
			entitySymbol: 'control-panel',
			entityTitle: Liferay.Language.get('custom-attributes')
		}
	];
	return (
		<div className='pt-1'>
			<ClayList className='mb-0'>
				<>
					{entitiesList.map(entity => (
						<EntityItem
							entitiesSyncedCount={entity.entitiesSyncedCount}
							entityConnectionStatus={
								entity.entityConnectionStatus
							}
							entityDescription={entity.entityDescription}
							entitySymbol={entity.entitySymbol}
							entityTitle={entity.entityTitle}
							key={entity.entityTitle}
						/>
					))}
				</>
			</ClayList>
		</div>
	);
};

export default DemandbaseEntities;
