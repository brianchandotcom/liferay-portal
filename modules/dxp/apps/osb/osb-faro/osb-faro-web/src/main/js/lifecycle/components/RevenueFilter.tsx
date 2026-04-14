import ClayButton from '@clayui/button';
import Dropdown from '@clayui/drop-down';
import Form, {ClayInput} from '@clayui/form';
import Icon from '@clayui/icon';
import React, {useContext, useState} from 'react';
import {
	FilterType,
	REVENUE_CONFIG,
	REVENUE_FILTER_KEYS,
	REVENUE_LABELS
} from '../utils/constants';
import {LifecycleContext} from '../context/LifecycleContext';
import {Option, Picker, Text} from '@clayui/core';
import {sub} from 'shared/util/lang';

const buildODataString = (values, fieldName = 'revenue') => {
	const {filterType} = values;
	const config = REVENUE_CONFIG[filterType];

	if (!config?.op) return '';

	const parts = config.fields
		.map((field, index) => {
			const val = values[field];
			const operator = Array.isArray(config.op)
				? config.op[index]
				: config.op;
			return val !== '' ? `${fieldName} ${operator} ${val}` : null;
		})
		.filter(Boolean);

	if (parts.length === 0) return '';

	const joined = parts.join(' and ');
	return Array.isArray(config.op) && parts.length > 1
		? `(${joined})`
		: joined;
};

const RevenueFilter = () => {
	const {filters, updateFilters} = useContext(LifecycleContext);

	const [dropdownActive, setDropdownActive] = useState(false);

	const [formValue, setFormValue] = useState(
		filters.revenueFilter.form || {
			filterType: FilterType.ALL,
			max: '',
			min: ''
		}
	);

	const handleSelectionChange = key => {
		const config = REVENUE_CONFIG[key];
		setFormValue({
			filterType: key,
			max: config.fields.includes('max') ? '10' : '',
			min: config.fields.includes('min') ? '1' : ''
		});
	};

	const handleInputChange = event => {
		const {name, value} = event.target;
		if (value !== '' && parseFloat(value) < 0) return;

		setFormValue(prev => ({...prev, [name]: value}));
	};

	const handleApply = () => {
		updateFilters({
			...filters,
			revenueFilter: {
				filterString: buildODataString(formValue),
				form: formValue
			}
		});
		setDropdownActive(false);
	};

	const currentConfig = REVENUE_CONFIG[formValue.filterType];
	const isInvalid = currentConfig.fields.some(field => !formValue[field]);

	return (
		<Dropdown
			active={dropdownActive}
			onActiveChange={setDropdownActive}
			trigger={
				<ClayButton
					className='rounded-lg mx-2'
					displayType='secondary'
					size='sm'
				>
					<span className='inline-item inline-item-before'>
						<Icon symbol='filter' />
					</span>
					{REVENUE_LABELS[formValue.filterType]}
				</ClayButton>
			}
			triggerIcon='caret-bottom'
		>
			<div className='container-fluid p-3'>
				<Form>
					<div className='mb-3 text-uppercase'>
						<Text color='secondary' size={2} weight='semi-bold'>
							{sub(Liferay.Language.get('filter-by-x'), [
								Liferay.Language.get('revenue')
							])}
						</Text>
					</div>

					<Form.Group>
						<label className='text-secondary text-weight-normal'>
							{Liferay.Language.get('condition')}
						</label>
						<Picker
							activeItem={formValue.filterType}
							as={React.forwardRef((props, ref) => (
								<ClayButton
									{...props}
									className='d-flex justify-content-between rounded-lg w-100'
									displayType='secondary'
									ref={ref}
									size='sm'
								>
									{REVENUE_LABELS[formValue.filterType]}
									<span className='inline-item inline-item-after'>
										<Icon symbol='caret-double' />
									</span>
								</ClayButton>
							))}
							items={REVENUE_FILTER_KEYS}
							onSelectionChange={handleSelectionChange}
						>
							{key => (
								<Option key={key}>{REVENUE_LABELS[key]}</Option>
							)}
						</Picker>
					</Form.Group>

					{currentConfig.fields.length > 0 && (
						<div className='gx-1 mt-3 row'>
							{currentConfig.fields.map(field => (
								<div
									className={
										currentConfig.fields.length > 1
											? 'col-6'
											: 'col-12'
									}
									key={field}
								>
									<Form.Group className='mb-0' small>
										<ClayInput
											min={0}
											name={field}
											onChange={handleInputChange}
											type='number'
											value={formValue[field]}
										/>
									</Form.Group>
								</div>
							))}
						</div>
					)}

					<ClayButton
						block
						className='mt-3'
						disabled={isInvalid}
						displayType='primary'
						onClick={handleApply}
						size='sm'
					>
						{Liferay.Language.get('apply')}
					</ClayButton>
				</Form>
			</div>
		</Dropdown>
	);
};

export default RevenueFilter;
