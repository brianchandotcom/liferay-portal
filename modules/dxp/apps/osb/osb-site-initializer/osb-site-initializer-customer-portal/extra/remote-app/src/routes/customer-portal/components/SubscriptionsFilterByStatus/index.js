import ClayButton from '@clayui/button';
import {DropDown} from '@clayui/core';
import React, {useState} from 'react';
import {POSSIBLE_STATUS_AMOUNT} from '../Subscriptions';

const SubscriptionsFilterByStatus = ({selectedStatus, setSelectedStatus}) => {
	const [active, setActive] = useState(false);

	const handleChange = (status) => {
		if (status === 'All') {
			setSelectedStatus(
				selectedStatus.length === POSSIBLE_STATUS_AMOUNT
					? []
					: ['Active', 'Expired', 'Future']
			);
		}
		else {
			setSelectedStatus(
				selectedStatus.includes(status)
					? selectedStatus.filter((value) => status !== value)
					: [...selectedStatus, status]
			);
		}
	};

	return (
		<div className="d-flex mb-4">
			<h5 className="mr-2 my-auto">Status:</h5>

			<DropDown
				active={active}
				closeOnClickOutside={false}
				onActiveChange={() => setActive((prevState) => !prevState)}
				trigger={
					<ClayButton
						className="font-weight-semi-bold text-brand-primary"
						displayType="unstyled"
					>
						{`${
							selectedStatus.length === POSSIBLE_STATUS_AMOUNT
								? 'All'
								: selectedStatus.length === 0
								? ' None '
								: selectedStatus.join(', ')
						}`}{' '}
						&#8595;
					</ClayButton>
				}
			>
				<DropDown.Item
					onClick={() => handleChange('All')}
					symbolRight={
						selectedStatus.length === POSSIBLE_STATUS_AMOUNT
							? 'check'
							: ''
					}
				>
					All
				</DropDown.Item>

				<DropDown.Item
					onClick={() => handleChange('Active')}
					symbolRight={
						selectedStatus.includes('Active') ? 'check' : ''
					}
				>
					Active
				</DropDown.Item>

				<DropDown.Item
					onClick={() => handleChange('Expired')}
					symbolRight={
						selectedStatus.includes('Expired') ? 'check' : ''
					}
				>
					Expired
				</DropDown.Item>

				<DropDown.Item
					onClick={() => handleChange('Future')}
					symbolRight={
						selectedStatus.includes('Future') ? 'check' : ''
					}
				>
					Future
				</DropDown.Item>
			</DropDown>
		</div>
	);
};

export default SubscriptionsFilterByStatus;
