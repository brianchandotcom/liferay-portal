import ClayButton from '@clayui/button';
import {DropDown} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';
import {POSSIBLE_SUBSCRIPTIONS_STATUS} from '../../utils/constants';

const SubscriptionsFilterByStatus = ({selectedStatus, setSelectedStatus}) => {
	const [active, setActive] = useState(false);

	const handleChange = (status) => {
		if (status === 'All') {
			return setSelectedStatus(
				selectedStatus.length ===
					Object.keys(POSSIBLE_SUBSCRIPTIONS_STATUS).length
					? []
					: [
							POSSIBLE_SUBSCRIPTIONS_STATUS.active,
							POSSIBLE_SUBSCRIPTIONS_STATUS.expired,
							POSSIBLE_SUBSCRIPTIONS_STATUS.future,
					  ]
			);
		}

		setSelectedStatus(
			selectedStatus.includes(status)
				? selectedStatus.filter((value) => status !== value)
				: [...selectedStatus, status]
		);
	};

	return (
		<div className="d-flex ml-3">
			<h6 className="mr-2 my-auto">Status:</h6>

			<DropDown
				active={active}
				closeOnClickOutside
				menuElementAttrs={{
					className: 'subscription-status-filter',
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="font-weight-semi-bold shadow-none text-brand-primary"
						displayType="unstyled"
					>
						{`${
							selectedStatus.length ===
							Object.keys(POSSIBLE_SUBSCRIPTIONS_STATUS).length
								? 'All'
								: selectedStatus.length === 0
								? 'None'
								: selectedStatus.join(', ')
						}`}{' '}
						<></>
						<ClayIcon symbol="caret-bottom" />
					</ClayButton>
				}
			>
				<DropDown.Item
					onClick={() => handleChange('All')}
					symbolRight={
						selectedStatus.length ===
						Object.keys(POSSIBLE_SUBSCRIPTIONS_STATUS).length
							? 'check'
							: ''
					}
				>
					All
				</DropDown.Item>

				<DropDown.Item
					onClick={() =>
						handleChange(POSSIBLE_SUBSCRIPTIONS_STATUS.active)
					}
					symbolRight={
						selectedStatus.includes(
							POSSIBLE_SUBSCRIPTIONS_STATUS.active
						)
							? 'check'
							: ''
					}
				>
					{POSSIBLE_SUBSCRIPTIONS_STATUS.active}
				</DropDown.Item>

				<DropDown.Item
					onClick={() =>
						handleChange(POSSIBLE_SUBSCRIPTIONS_STATUS.expired)
					}
					symbolRight={
						selectedStatus.includes(
							POSSIBLE_SUBSCRIPTIONS_STATUS.expired
						)
							? 'check'
							: ''
					}
				>
					{POSSIBLE_SUBSCRIPTIONS_STATUS.expired}
				</DropDown.Item>

				<DropDown.Item
					onClick={() =>
						handleChange(POSSIBLE_SUBSCRIPTIONS_STATUS.future)
					}
					symbolRight={
						selectedStatus.includes(
							POSSIBLE_SUBSCRIPTIONS_STATUS.future
						)
							? 'check'
							: ''
					}
				>
					{POSSIBLE_SUBSCRIPTIONS_STATUS.future}
				</DropDown.Item>
			</DropDown>
		</div>
	);
};

export default SubscriptionsFilterByStatus;
