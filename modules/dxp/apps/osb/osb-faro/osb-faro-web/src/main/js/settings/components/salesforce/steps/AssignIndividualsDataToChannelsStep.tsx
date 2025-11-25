import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import getCN from 'classnames';
import React, {useEffect, useState} from 'react';
import {Alert} from 'shared/types';
import {ButtonGroup} from './ButtonGroup';
import {ClayCheckbox} from '@clayui/form';
import {modalTypes} from 'shared/actions/modals';
import {Routes, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {updateSalesforce} from 'shared/api/data-source';
import {useConnectSalesforce} from 'settings/components/salesforce/ConnectSalesforceContext';
import {useHistory, useParams} from 'react-router-dom';

const AssignIndividualsDatatoPropertiesStep = ({
	addAlert,
	close,
	onPrev,
	open
}) => {
	const history = useHistory();
	const {groupId} = useParams();
	const [selectedItems, setSelectedItems] = useState([]);
	const [allChannelsSelected, setAllChannelsSelected] = useState(false);
	const {dataSource} = useConnectSalesforce();
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		const channelsConfiguration = dataSource?.provider?.get(
			'channelsConfiguration'
		);

		if (channelsConfiguration) {
			setSelectedItems(
				channelsConfiguration
					.get('channels')
					.toJS()
					.map(channel => channel.channelId)
			);
			setAllChannelsSelected(
				channelsConfiguration.get('enableAllChannels')
			);
		}
	}, [dataSource]);

	return (
		<ClayForm
			onSubmit={async event => {
				event.preventDefault();

				const updatedDataSource = {
					channelsConfiguration: {
						channels: selectedItems.map(channelId => ({
							channelId,
							enabled: true
						})),
						enableAllChannels: allChannelsSelected
					},
					groupId,
					id: dataSource.id
				} as any;

				try {
					setLoading(true);

					await updateSalesforce(updatedDataSource);

					const accountsEnabled = dataSource.provider.getIn([
						'accountsConfiguration',
						'enableAllAccounts'
					]);

					const contactsConfiguration = dataSource.provider.get(
						'contactsConfiguration'
					);

					const individualsEnabled =
						contactsConfiguration.get('enableAllContacts') &&
						contactsConfiguration.get('enableAllLeads');

					if (accountsEnabled || individualsEnabled) {
						addAlert({
							alertType: Alert.Types.Success,
							message: Liferay.Language.get(
								'the-data-source-setup-is-now-complete,-and-you-will-begin-to-see-data-as-activities-occur-on-your-sites'
							)
						});
					} else {
						addAlert({
							alertType: Alert.Types.Success,
							message: Liferay.Language.get(
								'the-data-source-setup-has-finished'
							)
						});
					}

					history.push(
						toRoute(Routes.SETTINGS_DATA_SOURCE, {
							groupId,
							id: dataSource.id
						})
					);
				} catch (error) {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get(
							'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
						)
					});
				} finally {
					setLoading(false);
				}
			}}
		>
			<div
				className={getCN({
					'opacity-50': allChannelsSelected
				})}
			>
				<div className='mb-2'>
					<Text size={2} weight='semi-bold'>
						{Liferay.Language.get(
							'select-properties'
						).toUpperCase()}
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
								{sub(
									selectedItems.length === 1
										? Liferay.Language.get(
												'x-property-selected'
										  )
										: Liferay.Language.get(
												'x-properties-selected'
										  ),
									[selectedItems.length]
								)}
							</ClayList.ItemTitle>
						</ClayList.ItemField>

						<ClayList.ItemField>
							<ClayButton
								disabled={allChannelsSelected}
								displayType='secondary'
								onClick={() => {
									open(modalTypes.SELECT_CHANNELS_MODAL, {
										groupId,
										initialItems: selectedItems,
										onClose: close,
										onSelect: setSelectedItems
									});
								}}
								size='sm'
							>
								{Liferay.Language.get('select')}
							</ClayButton>
						</ClayList.ItemField>
					</ClayList.Item>
				</ClayList>
			</div>

			<div className='d-flex'>
				<ClayCheckbox
					checked={allChannelsSelected}
					id='checkAllChannels'
					inline
					onChange={() =>
						setAllChannelsSelected(!allChannelsSelected)
					}
				/>

				<label className='ml-2' htmlFor='checkAllChannels'>
					<Text size={3} weight='normal'>
						{Liferay.Language.get(
							'make-individual-data-from-this-data-source-available-in-all-properties,-including-those-not-yet-created'
						)}
					</Text>
				</label>
			</div>

			<ButtonGroup
				nextButtonLabel={Liferay.Language.get('finish-setup')}
				nextButtonLoading={loading}
				onCancel={onPrev}
				prevButtonLabel={Liferay.Language.get('previous')}
			/>
		</ClayForm>
	);
};

export {AssignIndividualsDatatoPropertiesStep};
