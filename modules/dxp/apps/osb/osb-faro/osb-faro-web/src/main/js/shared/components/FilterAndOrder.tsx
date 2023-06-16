import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button/lib/Button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React from 'react';
import {ClayCheckbox, ClayRadio} from '@clayui/form';
import {FilterByType, FilterInputType, FilterOptionType} from 'shared/types';
import {get, noop, uniqueId} from 'lodash';
import {Map, Set} from 'immutable';
import {sub} from 'shared/util/lang';

interface IItemProps {
	active: boolean;
	className?: string;
	field?: string;
	label: string;
	name?: string;
	onChange: (val: string, field: string) => void;
	type: FilterInputType;
	value: string;
}

const Item: React.FC<IItemProps> = ({
	active,
	className,
	field,
	label,
	name,
	onChange,
	type,
	value,
	...otherProps
}) => {
	const handleChange = () => onChange(value, field);

	const Component = type === 'radio' ? ClayRadio : ClayCheckbox;

	return (
		<ClayDropDown.Item active={active} className={className}>
			<Component
				{...otherProps}
				checked={active}
				label={label}
				name={name}
				onChange={handleChange}
				value={value}
			/>
		</ClayDropDown.Item>
	);
};

const FlatList: React.FC<Omit<FilterOptionsListPropsType, 'flat'>> = ({
	className,
	filterBy,
	filterByOptions,
	onChange
}) => (
	<>
		{filterByOptions.map(({key, label, type = 'checkbox', values}, i) => (
			<ClayDropDown.Group
				header={
					sub(Liferay.Language.get('filter-by-x'), [label]) as string
				}
				key={key}
			>
				{values.map(({label: itemLabel, value}) => (
					<Item
						active={filterBy.hasIn([key, value])}
						className={className}
						field={key}
						key={value}
						label={itemLabel}
						onChange={onChange}
						type={type}
						value={value}
					/>
				))}

				{i < filterByOptions.length - 1 && <hr />}
			</ClayDropDown.Group>
		))}
	</>
);

const NestedList: React.FC<Omit<FilterOptionsListPropsType, 'flat'>> = ({
	className,
	filterBy,
	filterByOptions,
	onChange
}) => (
	<ClayDropDown.Group header={Liferay.Language.get('filter-by')}>
		{filterByOptions.map(({key, label, values}) =>
			values.length > 1 ? (
				<ClayDropDown
					alignmentPosition={Align.RightCenter}
					key={key}
					trigger={
						<ClayDropDown.Item className='d-flex justify-content-between align-items-center w-100'>
							<span className='text-truncate'>{label}</span>

							<span className='caret-root'>
								<ClayIcon symbol='caret-right' />
							</span>
						</ClayDropDown.Item>
					}
				>
					{values.map(({label: itemLabel, value}) => (
						<Item
							active={filterBy.hasIn([key, value])}
							className={className}
							field={key}
							key={value}
							label={itemLabel}
							onChange={onChange}
							type='checkbox'
							value={value}
						/>
					))}
				</ClayDropDown>
			) : (
				<Item
					active={filterBy.hasIn([key, get(values, ['0', 'value'])])}
					field={key}
					key={key}
					label={get(values, ['0', 'label'])}
					onChange={onChange}
					type='checkbox'
					value={get(values, ['0', 'value'])}
				/>
			)
		)}
	</ClayDropDown.Group>
);

type FilterOptionsListPropsType = Pick<
	IFilterAndOrderProps,
	'className' | 'flat' | 'filterBy' | 'filterByOptions'
> & {
	onChange: (value: string, field: string) => void;
};

const FilterOptionsList: React.FC<FilterOptionsListPropsType> = ({
	flat,
	...otherProps
}) => (flat ? <FlatList {...otherProps} /> : <NestedList {...otherProps} />);

export const getFilterAndOrderLabel = ({filterByOptions, orderByOptions}) => {
	if (filterByOptions.length && orderByOptions.length) {
		return Liferay.Language.get('filter-and-order');
	} else if (filterByOptions.length) {
		return Liferay.Language.get('filter');
	}

	return Liferay.Language.get('order[sort]');
};

interface IFilterAndOrderProps extends React.HTMLAttributes<HTMLElement> {
	disabled?: boolean;
	filterBy?: FilterByType;
	filterByOptions?: FilterOptionType[];
	flat: boolean;
	onFilterByChange?: (filterMap: Map<any, any>) => void;
	onOrderFieldChange?: (field: string) => void;
	orderField: string;
	orderByOptions?: {label: string; value: string}[];
	trigger?: React.ReactElement<any, string>;
}

class FilterAndOrder extends React.Component<IFilterAndOrderProps> {
	static defaultProps = {
		disabled: false,
		filterBy: Map(),
		filterByOptions: [],
		flat: false,
		onFilterByChange: noop,
		onOrderFieldChange: noop,
		orderByOptions: [],
		orderField: '',
		trigger: null
	};

	_name = uniqueId('filterAndOrder');

	@autobind
	handleFilterChange(value, field) {
		const {filterBy, filterByOptions, onFilterByChange} = this.props;

		const {type} = filterByOptions.find(({key}) => key === field);

		onFilterByChange(
			filterBy.update(field, (values = Set()) => {
				if (type === 'radio') {
					return Set([value]);
				}

				return values.has(value)
					? values.delete(value)
					: values.add(value);
			})
		);
	}

	render() {
		const {
			className,
			disabled,
			filterBy,
			filterByOptions,
			flat,
			onOrderFieldChange,
			orderByOptions,
			orderField,
			trigger
		} = this.props;

		const hasFilterBy = !!filterByOptions.length;
		const hasOrderBy = !!orderByOptions.length;

		return (
			<ClayDropDown
				className={getCN(
					'dropdown-root',
					'filter-and-order-root',
					className
				)}
				trigger={
					trigger ? (
						trigger
					) : (
						<ClayButton
							block
							className='filter-and-order-button'
							data-testid='filter-and-order-button'
							disabled={disabled}
							displayType='unstyled'
						>
							<span className='text-truncate'>
								{getFilterAndOrderLabel({
									filterByOptions,
									orderByOptions
								})}
							</span>

							<span className='caret-root'>
								<ClayIcon symbol='caret-bottom' />
							</span>
						</ClayButton>
					)
				}
			>
				{hasFilterBy && (
					<FilterOptionsList
						filterBy={filterBy}
						filterByOptions={filterByOptions}
						flat={flat}
						onChange={this.handleFilterChange}
					/>
				)}

				{hasOrderBy && (
					<ClayDropDown.ItemList>
						<ClayDropDown.Group
							header={Liferay.Language.get('order-by')}
						>
							{orderByOptions.map(({label, value}) => (
								<Item
									active={value === orderField}
									key={value}
									label={label}
									name={this._name}
									onChange={onOrderFieldChange}
									type='radio'
									value={value}
								/>
							))}
						</ClayDropDown.Group>
					</ClayDropDown.ItemList>
				)}
			</ClayDropDown>
		);
	}
}

export default FilterAndOrder;
