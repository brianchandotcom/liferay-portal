import ClayButton from '@clayui/button';
import Dropdown from '@clayui/drop-down';
import Form, {ClayInput} from '@clayui/form';
import Icon from '@clayui/icon';
import React, {useContext, useEffect, useState} from 'react';
import {LifecycleContext} from '../context/LifecycleContext';
import {Option, Picker, Text} from '@clayui/core';

const FilterType = {
	ALL: 'ALL',
	GREATER_THAN: 'GT',
	LESS_THAN: 'LT',
	// eslint-disable-next-line sort-keys
	BETWEEN: 'BETWEEN'
};

const REVENUE_CONFIG = {
	[FilterType.ALL]: {fields: [], op: null},
	[FilterType.GREATER_THAN]: {fields: ['min'], op: 'gt'},
	[FilterType.LESS_THAN]: {fields: ['max'], op: 'lt'},
	[FilterType.BETWEEN]: {fields: ['min', 'max'], op: ['ge', 'le']}
};

const REVENUE_LABELS = {
	[FilterType.ALL]: Liferay.Language.get('all-revenue-ranges'),
	[FilterType.GREATER_THAN]: Liferay.Language.get('revenue-is-greater-than'),
	[FilterType.LESS_THAN]: Liferay.Language.get('revenue-is-less-than'),
	[FilterType.BETWEEN]: Liferay.Language.get('revenue-is-between')
};

const REVENUE_FILTER_KEYS = Object.values(FilterType);

const GlobalFilters = () => {
	const {filters, updateFilters} = useContext(LifecycleContext);

	const [dropdownActive, setDropdownActive] = useState(false);

	const [formValue, setFormValue] = useState({
		filterType: FilterType.ALL,
		max: '',
		min: ''
	});

	const buildODataString = (values, fieldName = 'revenue') => {
		const {filterType} = values;
		const config = REVENUE_CONFIG[filterType];

		if (!config?.op) return '';

		const {fields, op} = config;

		const parts = fields
			.map((field, index) => {
				const val = values[field];
				const operator = Array.isArray(op) ? op[index] : op;

				return val !== '' ? `${fieldName} ${operator} ${val}` : null;
			})
			.filter(Boolean);

		if (parts.length === 0) return '';

		const joined = parts.join(' and ');
		return Array.isArray(op) && parts.length > 1 ? `(${joined})` : joined;
	};

	const handleApply = () => {
		updateFilters({
			...filters,
			revenue: {
				...formValue,
				oDataFilterString: buildODataString(formValue)
			}
		});
		setDropdownActive(false);
	};

	const handleSelectionChange = key => {
		const config = REVENUE_CONFIG[key];

		setFormValue({
			filterType: key,
			max: config.fields.includes('max') ? '10' : '',
			min: config.fields.includes('min') ? '1' : ''
		});
	};

	useEffect(() => {
		console.log(filters);
	});

	const currentConfig = REVENUE_CONFIG[formValue.filterType];
	const showMin = currentConfig.fields.includes('min');
	const showMax = currentConfig.fields.includes('max');

	const isInvalid = currentConfig.fields.some(field => !formValue[field]);

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
					{REVENUE_LABELS[formValue.filterType]}
				</ClayButton>
			}
			triggerIcon='caret-bottom'
		>
			<div className='container-fluid p-3'>
				<Form>
					<div className='mb-3 text-uppercase'>
						<Text color='secondary' size={2} weight='semi-bold'>
							{Liferay.Language.get('filter-by-revenue')}
						</Text>
					</div>

					<Form.Group>
						<label className='text-weight-normal text-secondary'>
							{Liferay.Language.get('condition')}
						</label>
						<Picker
							activeItem={formValue.filterType}
							as={React.forwardRef((props, ref) => (
								<ClayButton
									{...props}
									className='justify-content-between rounded-lg w-100'
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
							onSelectionChange={key =>
								handleSelectionChange(key)
							}
						>
							{key => (
								<Option key={key}>{REVENUE_LABELS[key]}</Option>
							)}
						</Picker>
					</Form.Group>

					{(showMin || showMax) && (
						<div className='gx-1 mt-3 row'>
							{showMin && (
								<div className={showMax ? 'col-6' : 'col-12'}>
									<Form.Group className='mb-0' small>
										<ClayInput
											name='min'
											onChange={e =>
												setFormValue({
													...formValue,
													min: e.target.value
												})
											}
											type='number'
											value={formValue.min}
										/>
									</Form.Group>
								</div>
							)}
							{showMax && (
								<div className={showMin ? 'col-6' : 'col-12'}>
									<Form.Group className='mb-0' small>
										<ClayInput
											name='max'
											onChange={e =>
												setFormValue({
													...formValue,
													max: e.target.value
												})
											}
											type='number'
											value={formValue.max}
										/>
									</Form.Group>
								</div>
							)}
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

export default GlobalFilters;
