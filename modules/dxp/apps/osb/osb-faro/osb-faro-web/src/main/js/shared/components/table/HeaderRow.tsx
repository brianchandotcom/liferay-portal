import HeaderCell from './HeaderCell';
import React from 'react';
import {Column} from './Row';
import {getFieldNameFromAccessor} from 'shared/util/pagination';
import {OrderedMap} from 'immutable';
import {OrderParams} from 'shared/util/records';

interface IHeaderRowProps {
	className?: string;
	columns: Column[];
	headerLink?: boolean;
	onSortOrderChange: (orderParams: OrderParams) => void;
	orderIOMap?: OrderedMap<string, OrderParams>;
	showCheckbox?: boolean;
	showInlineRowActions?: boolean;
}

const HeaderRow: React.FC<IHeaderRowProps> = ({
	className,
	columns,
	headerLink,
	onSortOrderChange,
	orderIOMap,
	showCheckbox,
	showInlineRowActions
}) => (
	<thead>
		<tr className={className}>
			{showCheckbox && <th />}

			{columns.map((column, i) => {
				const {
					accessor,
					className,
					headProps = {},
					label,
					sortable
				} = column;

				const field = getFieldNameFromAccessor(accessor);

				const {sortOrder} = orderIOMap.get(field, new OrderParams());

				return (
					<HeaderCell
						className={className}
						field={field}
						headerLink={headerLink}
						key={`${label}-${i}`}
						onSortOrderChange={onSortOrderChange}
						sortable={sortable}
						sortOrder={sortOrder}
						{...headProps}
					>
						{label}
					</HeaderCell>
				);
			})}

			{showInlineRowActions && <th />}
		</tr>
	</thead>
);

export default HeaderRow;
