import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import Label from '@clayui/label';
import React from 'react';
import {ConnectorEntityDescriptor} from './types';
import {sub} from 'shared/util/lang';

type ConnectionStatus = 'connected' | 'disconnected';

interface IConnectorEntitiesProps {
	connectionStatus: ConnectionStatus;
	entities: ConnectorEntityDescriptor[];
	syncedCounts: {[accessor: string]: number | undefined};
}

const ConnectorEntities: React.FC<IConnectorEntitiesProps> = ({
	connectionStatus,
	entities,
	syncedCounts
}) => (
	<div className='pt-1'>
		<ClayList className='mb-0'>
			{entities.map(entity => {
				const count = syncedCounts[entity.accessor];

				return (
					<ClayList.Item flex key={entity.accessor}>
						<ClayList.ItemField>
							<ClaySticker displayType='unstyled'>
								<ClayIcon
									className='text-secondary'
									symbol={entity.icon}
								/>
							</ClaySticker>
						</ClayList.ItemField>

						<ClayList.ItemField expand>
							<ClayList.ItemTitle>
								{entity.label}
							</ClayList.ItemTitle>

							<ClayList.ItemText>
								{entity.description}
							</ClayList.ItemText>

							{typeof count === 'number' && count >= 0 && (
								<ClayList.ItemText>
									{sub(
										Liferay.Language.get('x-items-synced'),
										[count]
									)}
								</ClayList.ItemText>
							)}
						</ClayList.ItemField>

						<ClayList.ItemField className='justify-content-center'>
							<Label
								displayType={
									connectionStatus === 'connected'
										? 'success'
										: 'secondary'
								}
							>
								{connectionStatus === 'connected'
									? Liferay.Language.get('connected')
									: Liferay.Language.get('disconnected')}
							</Label>
						</ClayList.ItemField>
					</ClayList.Item>
				);
			})}
		</ClayList>
	</div>
);

export default ConnectorEntities;
