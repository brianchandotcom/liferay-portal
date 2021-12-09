import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React, {useEffect} from 'react';

const SubscriptionsFilterByStatus = ({setSelectedStatus}) => {
	const items = [
		{
			items: [
				{
					checked: true,
					label: 'All',
					onChange: () => alert('All'),
					type: 'checkbox',
				},
				{
					checked: false,
					label: 'Active',
					onChange: () => alert('Active'),
					type: 'checkbox',
				},
				{
					checked: false,
					label: 'Future',
					onChange: () => alert('Future'),
					type: 'checkbox',
				},
				{
					checked: false,
					label: 'Expired',
					onChange: () => alert('Expired'),
					type: 'checkbox',
				},
			],
			type: 'group',
		},
	];

	// const handleFilterChange = (event) => {
	// 	setSelectedStatus(event.target.value);
	// };

	useEffect(() => {
		setSelectedStatus('All');
	}, [setSelectedStatus]);

	return (
		<ClayDropDownWithItems
			className="mb-5"
			items={items}
			trigger={
				<ClayButton
					className="font-weight-semi-bold text-brand-primary"
					displayType="unstyled"
				>
					Filter &#8595;
				</ClayButton>
			}
		/>
	);
};

export default SubscriptionsFilterByStatus;
