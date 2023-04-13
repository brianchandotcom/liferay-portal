import BarComparisonCell from './BarComparisonCell';
import EventAnalysisResultQuery, {
	EventAnalysisResultData,
	EventAnalysisResultVariables
} from 'event-analysis/queries/EventAnalysisResultQuery';
import getCN from 'classnames';
import PercentOfCell from './PercentOfCell';
import React, {useEffect, useRef, useState} from 'react';
import Table from 'shared/components/table';
import TextTruncate from 'shared/components/TextTruncate';
import WithEmptyState from './hoc/WithEmptyState';
import {
	Attributes,
	Breakdown,
	BreakdownData,
	Breakdowns,
	CalculationTypes,
	Event,
	Filters,
	ParsedBreakdownData,
	ParsedBreakdownItem
} from 'event-analysis/utils/types';
import {compose} from 'redux';
import {debounce, get, isNil, omit} from 'lodash';
import {EditBreakdown, withAttributesConsumer} from '../context/attributes';
import {getMaxEventValue, parseBreakdownData} from 'event-analysis/utils/utils';
import {getSafeRangeSelectors} from 'shared/util/util';
import {OrderedMap} from 'immutable';
import {SafeResults} from 'shared/hoc/util';
import {sub} from 'shared/util/lang';
import {useQuery} from '@apollo/react-hooks';
import {useStatefulPagination} from 'shared/hooks';
import {withPaginationBar} from 'shared/hoc';
import {WithRangeKeyProps} from 'shared/hoc/WithRangeKey';

export interface IBreakdownTableWithSafeResultsProps
	extends IBreakdownTableProps {
	channelId: string;
	filterOrder: string[];
}

export interface IBreakdownTableProps
	extends WithRangeKeyProps,
		React.HTMLAttributes<HTMLElement> {
	attributes: Attributes;
	breakdownOrder: string[];
	breakdowns: Breakdowns;
	compareToPrevious: boolean;
	delta: number;
	editBreakdown: EditBreakdown;
	event: Event;
	eventAnalysisResult: EventAnalysisResultData;
	filters: Filters;
	onDeltaChange: (page: number) => void;
	onPageChange: (page: number) => void;
	page: number;
	type: CalculationTypes;
}

type TableColumnAttributesType = {
	charWidthRatio: number;
	columnSizePercentage: {
		breakdownColumn1x: number;
		breakdownColumn2x: number;
		breakdownColumn3x: number;
	};
	fontSize: number;
	paddingX: number;
	truncateGap: number;
};

const TABLE_COLUMN_ATTRIBUTES_MAP: TableColumnAttributesType = {
	charWidthRatio: 2,
	columnSizePercentage: {
		breakdownColumn1x: 0.33,
		breakdownColumn2x: 0.25,
		breakdownColumn3x: 0.2
	},
	fontSize: 14,
	paddingX: 32,
	truncateGap: 1
};

const getBreakdownByAccessor = (
	accessor: string,
	breakdownOrder: string[],
	breakdowns: Breakdowns
): Breakdown => {
	const orderIndex = Number(accessor.split('breakdown').pop());
	const breakdownId = breakdownOrder[orderIndex];

	return breakdowns[breakdownId];
};

const TableWithPagination = withPaginationBar()(Table);

const BreakdownTable: React.FC<IBreakdownTableProps> = ({
	attributes,
	breakdownOrder,
	breakdowns,
	compareToPrevious,
	delta,
	editBreakdown,
	event,
	eventAnalysisResult,
	onDeltaChange,
	onPageChange,
	page
}) => {
	const [maxBreakdownLength, setMaxBreakdownLength] = useState<number>();

	const parseData = (
		data: BreakdownData
	): {
		columns: {
			accessor: string;
			cellRenderer: (props: {
				className: string;
				data: ParsedBreakdownItem;
			}) => React.TdHTMLAttributes<HTMLElement>;
			headProps?: {
				order: string;
			};
			label: string;
			sortable: boolean;
		};
		count: number;
		highestValue: number;
		items: ParsedBreakdownData;
	} => {
		const orderedBreakdowns = breakdownOrder.map(
			breakdownId => breakdowns[breakdownId]
		);

		const items = parseBreakdownData(data, orderedBreakdowns);

		const highestValue = getMaxEventValue(items, compareToPrevious);

		const columns = getColumns({
			attributes,
			breakdowns,
			compareToPrevious,
			event,
			highestValue,
			maxBreakdownLength,
			order: breakdownOrder,
			value: data.value
		});

		return {
			columns,
			count: data.count,
			highestValue,
			items
		};
	};

	const {columns, count, highestValue, items} = parseData(
		eventAnalysisResult
	);

	const orderIOMap = OrderedMap(
		breakdownOrder.map((breakdownId, i) => {
			const {sortType} = breakdowns[breakdownId];

			return [
				`breakdown${i}`,
				{field: `breakdown${i}`, sortOrder: sortType}
			];
		})
	);

	const tableRef = useRef<HTMLDivElement>(null);

	const getBreakdownColumnMaxCharLength = (
		tableSize: number,
		breakDownLength: number
	) => {
		const {
			charWidthRatio,
			columnSizePercentage,
			fontSize,
			paddingX,
			truncateGap
		} = TABLE_COLUMN_ATTRIBUTES_MAP;

		const columnTablePercentage = Math.floor(
			tableSize *
				columnSizePercentage[`breakdownColumn${breakDownLength}x`]
		);
		const columnContentWidth = columnTablePercentage - paddingX;
		const pixelsPerFontSize = Math.floor(columnContentWidth / fontSize);
		const maxCharLengthPerColumn =
			pixelsPerFontSize * charWidthRatio - truncateGap;

		setMaxBreakdownLength(maxCharLengthPerColumn);
	};

	useEffect(() => {
		const handleResize = debounce(
			() =>
				getBreakdownColumnMaxCharLength(
					tableRef.current.clientWidth,
					breakdownOrder.length
				),
			100
		);

		window.removeEventListener('resize', handleResize);

		window.addEventListener('resize', handleResize);

		return () => {
			window.removeEventListener('resize', handleResize);
		};
	}, [breakdownOrder.length]);

	useEffect(() => {
		getBreakdownColumnMaxCharLength(
			tableRef.current.clientWidth,
			breakdownOrder.length
		);
	}, [breakdownOrder.length]);

	const handleSort = orderIOMap => {
		const {field, sortOrder} = orderIOMap.first();

		const breakdown = getBreakdownByAccessor(
			field,
			breakdownOrder,
			breakdowns
		);

		const attribute = attributes[breakdown.attributeId];

		editBreakdown({
			attribute,
			breakdown: {
				...breakdown,
				sortType: sortOrder
			},
			id: breakdown.id
		});
	};

	return (
		<div
			className={getCN('breakdown-table-root', {
				'breakdown-single-event': !breakdownOrder.length
			})}
			ref={tableRef}
		>
			{!breakdownOrder.length ? (
				<BarComparisonCell
					compareToPrevious={compareToPrevious}
					event={event}
					events={items[0].events}
					topValue={highestValue}
				/>
			) : (
				<TableWithPagination
					bordered
					columns={columns}
					delta={delta}
					items={items}
					onDeltaChange={onDeltaChange}
					onOrderIOMapChange={handleSort}
					onPageChange={onPageChange}
					onSortChange={handleSort}
					orderIOMap={orderIOMap}
					page={page}
					rowIdentifier='index'
					total={count}
				/>
			)}
		</div>
	);
};

const BreakdownWithSafeResults: React.FC<IBreakdownTableWithSafeResultsProps> = ({
	attributes,
	breakdownOrder,
	breakdowns,
	channelId,
	compareToPrevious,
	editBreakdown,
	event,
	filterOrder,
	filters,
	rangeSelectors,
	type
}) => {
	const {delta, onDeltaChange, onPageChange, page} = useStatefulPagination();

	const result = useQuery<
		EventAnalysisResultData,
		EventAnalysisResultVariables
	>(EventAnalysisResultQuery, {
		fetchPolicy: 'network-only',
		variables: {
			analysisType: type,
			channelId,
			compareToPrevious,
			eventAnalysisBreakdowns: breakdownOrder.map(breakdownId =>
				omit(breakdowns[breakdownId], 'id')
			),
			eventAnalysisFilters: filterOrder.map(filterId =>
				omit(filters[filterId], 'id')
			),
			eventDefinitionId: event.id,
			page: page - 1,
			size: delta,
			...getSafeRangeSelectors(rangeSelectors)
		}
	});

	useEffect(() => {
		onPageChange(1);
	}, [breakdownOrder, breakdowns, event, filters, rangeSelectors]);

	return (
		<SafeResults {...result} page={false} pageDisplay={false}>
			{({
				eventAnalysisResult
			}: {
				eventAnalysisResult: EventAnalysisResultData;
			}) => (
				<BreakdownTable
					attributes={attributes}
					breakdownOrder={breakdownOrder}
					breakdowns={breakdowns}
					compareToPrevious={compareToPrevious}
					delta={delta}
					editBreakdown={editBreakdown}
					event={event}
					eventAnalysisResult={eventAnalysisResult}
					filters={filters}
					onDeltaChange={onDeltaChange}
					onPageChange={onPageChange}
					page={page}
					rangeSelectors={rangeSelectors}
					type={type}
				/>
			)}
		</SafeResults>
	);
};

const getColumns = ({
	attributes,
	breakdowns,
	compareToPrevious,
	event,
	highestValue,
	maxBreakdownLength,
	order,
	value
}) => {
	const columns = order.map((breakdownId: string, i: number) => {
		const {attributeId, sortType} = breakdowns[breakdownId];

		const accessor = `breakdown${i}`;

		return {
			accessor,
			cellRenderer: ({className, data}) => {
				const dataEvents = get(data, 'events');
				const dataValue = get(data, accessor);
				const nextDataValue = get(data, `breakdown${i + 1}`);

				if (
					isNil(dataValue) &&
					isNil(dataEvents) &&
					isNil(nextDataValue)
				) {
					return (
						<td
							className={getCN(
								'align-top',
								'empty-breakdown-column',
								className
							)}
						>
							{Liferay.Language.get('no-results')}
						</td>
					);
				} else if (isNil(dataValue)) {
					return null;
				}

				return (
					<td
						className={getCN(
							'font-weight-semibold',
							'align-top',
							className
						)}
						rowSpan={dataValue.rowSpan}
					>
						<TextTruncate
							maxCharLength={maxBreakdownLength}
							title={dataValue.name}
						/>
					</td>
				);
			},
			headProps: {
				order: sortType
			},
			label: (
				<div>
					{/* TODO: Enable when multiple types available <span className='breakdown-category'>{attributeType}</span>*/}

					{attributes[attributeId].displayName}
				</div>
			)
		};
	});

	columns.push({
		cellRenderer: ({className, data: {events}}) => {
			if (isNil(events)) {
				return (
					<td
						className={getCN(
							'align-top',
							'empty-breakdown-column',
							className
						)}
					>
						{Liferay.Language.get('no-results')}
					</td>
				);
			}

			return (
				<td className={getCN('align-top', className)}>
					<BarComparisonCell
						compareToPrevious={compareToPrevious}
						event={event}
						events={events}
						topValue={highestValue}
					/>
				</td>
			);
		},
		label: Liferay.Language.get('events'),
		sortable: false
	});

	columns.push({
		cellRenderer: ({className, data: {events}}) => (
			<td className={getCN('align-top', className)}>
				<PercentOfCell
					compareToPrevious={compareToPrevious}
					events={events}
					totalValue={value}
				/>
			</td>
		),
		label: sub(Liferay.Language.get('percent-of-x'), [
			event.displayName || event.name
		]),
		sortable: false
	});

	return columns;
};

export default compose(
	withAttributesConsumer,
	WithEmptyState
)(BreakdownWithSafeResults);
