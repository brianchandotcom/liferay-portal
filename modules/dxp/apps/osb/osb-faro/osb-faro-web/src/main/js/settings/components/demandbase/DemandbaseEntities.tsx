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
	loading?: boolean;
}

const DemandbaseEntities: React.FC<IDemandbaseEntities> = ({
	accountConnectionStatus,
	accountsSyncedCount
}) => (
	<div className='pt-1'>
		<ClayList className='mb-0'>
			<ClayList.Item flex>
				<ClayList.ItemField>
					<ClaySticker displayType='unstyled'>
						<ClayIcon
							className='text-secondary'
							symbol='briefcase'
						/>
					</ClaySticker>
				</ClayList.ItemField>

				<ClayList.ItemField expand>
					<ClayList.ItemTitle>
						{Liferay.Language.get('accounts')}
					</ClayList.ItemTitle>

					<ClayList.ItemText>
						{Liferay.Language.get(
							'represents-fields-from-the-account-object-within-demandbase'
						)}
					</ClayList.ItemText>

					{accountsSyncedCount >= 0 && (
						<ClayList.ItemText>
							{sub(Liferay.Language.get('x-items-synced'), [
								accountsSyncedCount
							])}
						</ClayList.ItemText>
					)}
				</ClayList.ItemField>

				<ClayList.ItemField className='justify-content-center'>
					<Label
						displayType={
							accountConnectionStatus === 'connected'
								? 'success'
								: 'secondary'
						}
					>
						{accountConnectionStatus === 'connected'
							? Liferay.Language.get('connected')
							: Liferay.Language.get('disconnected')}
					</Label>
				</ClayList.ItemField>
			</ClayList.Item>
		</ClayList>
	</div>
);

export default DemandbaseEntities;
