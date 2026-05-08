import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import Label from '@clayui/label';
import React from 'react';
import {ConnectorEntityDescriptor} from './types';
import {getEntityDisplay} from './getEntityDisplay';
import {sub} from 'shared/util/lang';

interface IConnectorEntitiesProps {
	entities: ConnectorEntityDescriptor[];
	syncedCounts: {[entity: string]: number | undefined};
}

const ConnectorEntities: React.FC<IConnectorEntitiesProps> = ({
	entities,
	syncedCounts
}) => (
	<div className='pt-1'>
		<ClayList className='mb-0'>
			{entities.map(({entity}) => {
				const {icon, label} = getEntityDisplay(entity);
				const count = syncedCounts[entity];
				const configured = typeof count === 'number' && count > 0;

				return (
					<ClayList.Item flex key={entity}>
						<ClayList.ItemField>
							<ClaySticker displayType='unstyled'>
								<ClayIcon
									className='text-secondary'
									symbol={icon}
								/>
							</ClaySticker>
						</ClayList.ItemField>

						<ClayList.ItemField expand>
							<ClayList.ItemTitle>{label}</ClayList.ItemTitle>

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
									configured ? 'success' : 'secondary'
								}
							>
								{Liferay.Language.get(
									configured ? 'configured' : 'unconfigured'
								)}
							</Label>
						</ClayList.ItemField>
					</ClayList.Item>
				);
			})}
		</ClayList>
	</div>
);

export default ConnectorEntities;
