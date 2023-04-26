import autobind from 'autobind-decorator';
import Checkbox from './Checkbox';
import Dropdown from './Dropdown';
import getCN from 'classnames';
import Radio from './Radio';
import React from 'react';
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

	const ComponentFn = type === 'radio' ? Radio : Checkbox;

	return (
		<Dropdown.Item active={active} className={className}>
			<ComponentFn
				{...otherProps}
				checked={active}
				label={label}
				name={name}
				onChange={handleChange}
			/>
		</Dropdown.Item>
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
			<React.Fragment key={key}>
				<Dropdown.Subheader>
					{sub(Liferay.Language.get('filter-by-x'), [label])}
				</Dropdown.Subheader>

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
			</React.Fragment>
		))}
	</>
);

const NestedList: React.FC<Omit<FilterOptionsListPropsType, 'flat'>> = ({
	className,
	filterBy,
	filterByOptions,
	onChange
}) => (
	<>
		<Dropdown.Subheader>
			{Liferay.Language.get('filter-by')}
		</Dropdown.Subheader>

		{filterByOptions.map(({key, label, values}) =>
			values.length > 1 ? (
				<Dropdown key={key} label={label}>
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
				</Dropdown>
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
	</>
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

interface IFilterAndOrderProps extends React.HTMLAttributes<HTMLElement> {
	disabled?: boolean;
	filterBy?: FilterByType;
	filterByOptions?: FilterOptionType[];
	flat: boolean;
	onFilterByChange?: (filterMap: Map<any, any>) => void;
	onOrderFieldChange?: (field: string) => void;
	orderField: string;
	orderByOptions?: {label: string; value: string}[];
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
		orderField: ''
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

	getFilterAndOrderLabel() {
		const {filterByOptions, orderByOptions} = this.props;

		const hasFilterBy = !!filterByOptions.length;
		const hasOrderBy = !!orderByOptions.length;

		if (hasFilterBy && hasOrderBy) {
			return Liferay.Language.get('filter-and-order');
		} else if (hasFilterBy) {
			return Liferay.Language.get('filter');
		}

		return Liferay.Language.get('order');
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
			orderField
		} = this.props;

		const hasFilterBy = !!filterByOptions.length;
		const hasOrderBy = !!orderByOptions.length;

		return (
			<Dropdown
				buttonProps={{
					['data-testid']: 'filter-and-order-button',
					displayType: 'unstyled'
				}}
				className={getCN('filter-and-order-root', className)}
				disabled={disabled}
				label={this.getFilterAndOrderLabel()}
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
					<Dropdown.Subheader>
						{Liferay.Language.get('order-by')}
					</Dropdown.Subheader>
				)}

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
			</Dropdown>
		);
	}
}

export default FilterAndOrder;
