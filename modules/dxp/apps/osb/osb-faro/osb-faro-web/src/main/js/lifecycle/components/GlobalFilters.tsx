import ClayButton from '@clayui/button';
import Dropdown from '@clayui/drop-down';
import Form, {ClayInput} from '@clayui/form';
import Icon from '@clayui/icon';
import React, {useContext, useState} from 'react';
import {LifecycleContext} from '../context/LifecycleContext';

import {Option, Picker} from '@clayui/core';

const REVENUE_FILTER_OPTIONS = [
	'All Revenue Ranges',
	'Revenue is greater than',
	'Revenue is less than',
	'Revenue is between'
];

const GlobalFilters = () => {
	const {filters, updateFilters} = useContext(LifecycleContext);
	const [dropdownActive, setDropdownActive] = useState(false);

	const [formValue, setFormValue] = useState({
		filterOption: REVENUE_FILTER_OPTIONS[0],
		max: '',
		min: ''
	});

	const handleSelectionChange = value => {
		setFormValue({
			...formValue,
			filterOption: value,
			max: '',
			min: ''
		});
	};

	const handleInputChange = event => {
		const {name, value} = event.target;
		setFormValue(prev => ({
			...prev,
			[name]: value
		}));
	};

	const handleApply = () => {
		updateFilters({...filters, revenue: formValue});
		setDropdownActive(false);
	};

	const isBetween = formValue.filterOption === 'Revenue is between';
	const isGreater = formValue.filterOption === 'Revenue is greater than';
	const isLess = formValue.filterOption === 'Revenue is less than';

	return (
		<Dropdown
			active={dropdownActive}
			onActiveChange={setDropdownActive}
			trigger={
				<ClayButton
					className='rounded-lg'
					displayType='secondary'
					size='sm'
				>
					<span className='inline-item inline-item-before'>
						<Icon symbol='filter' />
					</span>
					{formValue.filterOption}
				</ClayButton>
			}
			triggerIcon='caret-bottom'
		>
			<Form className='p-3'>
				<Form.Group>
					<label htmlFor='picker'>{'Range Type'}</label>
					<Picker
						activeItem={formValue.filterOption}
						id='picker'
						items={REVENUE_FILTER_OPTIONS}
						onSelectionChange={handleSelectionChange}
						placeholder={formValue.filterOption}
						width={100}
					>
						{item => <Option key={item}>{item}</Option>}
					</Picker>
				</Form.Group>

				{(isGreater || isLess || isBetween) && (
					<Form.Group className='form-group-autofit'>
						{(isGreater || isBetween) && (
							<Form.Group>
								<ClayInput
									id='min'
									name='min'
									onChange={handleInputChange}
									placeholder='0.00'
									type='number'
									value={formValue.min}
								/>
							</Form.Group>
						)}

						{(isLess || isBetween) && (
							<Form.Group>
								<ClayInput
									id='max'
									name='max'
									onChange={handleInputChange}
									placeholder='1000.00'
									type='number'
									value={formValue.max}
								/>
							</Form.Group>
						)}
					</Form.Group>
				)}

				<ClayButton
					block
					className='mt-3'
					displayType='primary'
					onClick={handleApply}
				>
					{'Apply'}
				</ClayButton>
			</Form>
		</Dropdown>
	);
};

export default GlobalFilters;
