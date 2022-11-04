/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import ClayTabs from '@clayui/tabs';
import React, {useState} from 'react';

import {updateProperty} from '../../utils/api';
import {SUCCESS_MESSAGE} from '../../utils/constants';
import Loading from '../Loading';
import ChannelTab from '../properties/ChannelTab';
import {TProperty} from '../properties/Properties';
import SitesTab from '../properties/SitesTab';
import {TItem} from '../table/Table';

interface IAssignModalProps {
	observer: any;
	onCloseModal: () => void;
	property: TProperty;
}

export enum ETabs {
	Channel = 0,
	Sites = 1,
}

function getIds(items: TItem[]): number[] {
	return items
		.filter(({checked, disabled}) => checked && !disabled)
		.map(({id}) => Number(id));
}

const AssignModal: React.FC<IAssignModalProps> = ({
	observer,
	onCloseModal,
	property,
}) => {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState<ETabs>(
		ETabs.Channel
	);
	const [submitting, setSubmitting] = useState(false);
	const [commerceChannelIds, setCommerceChannelIds] = useState<number[]>([]);
	const [siteIds, setSiteIds] = useState<number[]>([]);

	const handleSubmit = () => {
		setSubmitting(true);

		const request = async () => {
			const {channelId, commerceSyncEnabled, dataSources} = property;

			const {ok} = await updateProperty({
				channelId,
				commerceChannelIds,
				commerceSyncEnabled,
				dataSourceId: dataSources[0]?.dataSourceId,
				siteIds,
			});

			setSubmitting(false);

			if (ok) {
				Liferay.Util.openToast({
					message: SUCCESS_MESSAGE,
				});

				onCloseModal();
			}
		};

		request();
	};

	return (
		<ClayModal center observer={observer} size="lg">
			<ClayModal.Header>
				{Liferay.Language.get('assign-to')} {property.name}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayTabs displayType="underline" modern>
					<ClayTabs.Item
						active={activeTabKeyValue === ETabs.Channel}
						innerProps={{
							'aria-controls': 'tabpanel-1',
						}}
						onClick={() => setActiveTabKeyValue(ETabs.Channel)}
					>
						{Liferay.Language.get('channel')}
					</ClayTabs.Item>

					<ClayTabs.Item
						active={activeTabKeyValue === ETabs.Sites}
						innerProps={{
							'aria-controls': 'tabpanel-2',
						}}
						onClick={() => setActiveTabKeyValue(ETabs.Sites)}
					>
						{Liferay.Language.get('sites')}
					</ClayTabs.Item>
				</ClayTabs>

				<ClayTabs.Content activeIndex={activeTabKeyValue} fade>
					<ClayTabs.TabPane aria-labelledby="tab-1">
						<ChannelTab
							onChannelsChange={(items) =>
								setCommerceChannelIds(getIds(items))
							}
							property={property}
						/>
					</ClayTabs.TabPane>

					<ClayTabs.TabPane aria-labelledby="tab-2">
						<SitesTab
							onSitesChange={(items) => {
								setSiteIds(getIds(items));
							}}
							property={property}
						/>
					</ClayTabs.TabPane>
				</ClayTabs.Content>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onCloseModal()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={submitting}
							displayType="primary"
							onClick={handleSubmit}
							type="submit"
						>
							{submitting && <Loading inline />}

							{Liferay.Language.get('assign')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

export default AssignModal;
