import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import getCN from 'classnames';
import React, {useState} from 'react';
import {ButtonGroup} from './ButtonGroup';
import {ClayCheckbox} from '@clayui/form';
import {modalTypes} from 'shared/actions/modals';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {useParams} from 'react-router-dom';

const AssignIndividualsDatatoPropertiesStep = ({
	close,
	onNext,
	onPrev,
	open
}) => {
	const {groupId} = useParams();
	const [selectedItems, setSelectedItems] = useState([]);
	const [allChannelsSelected, setAllChannelsSelected] = useState(false);

	return (
		<ClayForm
			onSubmit={event => {
				event.preventDefault();

				onNext();
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
				onCancel={onPrev}
				prevButtonLabel={Liferay.Language.get('previous')}
			/>
		</ClayForm>
	);
};

export {AssignIndividualsDatatoPropertiesStep};
