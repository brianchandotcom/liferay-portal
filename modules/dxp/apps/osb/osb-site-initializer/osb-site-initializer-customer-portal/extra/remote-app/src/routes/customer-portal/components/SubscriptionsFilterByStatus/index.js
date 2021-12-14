import React, {useEffect} from 'react';

const options = [
	{
		label: 'All',
		value: 'All',
	},
	{
		label: 'Active',
		value: 'Active',
	},
	{
		label: 'Expired',
		value: 'Expired',
	},
	{
		label: 'Future',
		value: 'Future',
	},
];

const SubscriptionsFilterByStatus = ({setSelectedStatus}) => {
	const handleFilterChange = (event) => {
		setSelectedStatus(event.target.value);
	};

	useEffect(() => {
		setSelectedStatus(options[0].value);
	}, [setSelectedStatus]);

	return (
		<select
			className="mb-2 w-25"
			id="subscriptionStatusFilter"
			name="subscriptionStatusFilter"
			onChange={handleFilterChange}
		>
			{options.map((option, index) => (
				<option key={index} value={option.value}>
					{option.label}
				</option>
			))}
		</select>
	);
};

export default SubscriptionsFilterByStatus;
