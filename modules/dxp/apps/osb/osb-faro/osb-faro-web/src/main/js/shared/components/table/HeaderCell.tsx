import Button from 'shared/components/Button';
import Constants, {OrderByDirections} from 'shared/util/constants';
import getCN from 'classnames';
import Icon from 'shared/components/Icon';
import React from 'react';
import {getDefaultSortOrder, invertSortOrder} from 'shared/util/pagination';
import {isNull, noop} from 'lodash';
import {OrderParams} from 'shared/util/records';
import {setUriQueryValues} from 'shared/util/router';

const {
	pagination: {cur: defaultPage}
} = Constants;

interface IHeaderCellProps {
	children: React.ReactNode;
	className?: string;
	field: string;
	headerLink?: boolean;
	onSortOrderChange?: (orderParams: OrderParams) => void;
	sortable?: boolean;
	sortOrder: OrderByDirections;
}

const HeaderCell: React.FC<IHeaderCellProps> = ({
	children,
	className,
	field,
	headerLink = false,
	onSortOrderChange = noop,
	sortOrder,
	sortable = true
}) => (
	<th className={getCN('table-head-title', className)}>
		{sortable ? (
			<Button
				className='inline-item text-truncate-inline'
				display='unstyled'
				href={
					headerLink
						? setUriQueryValues({
								field,
								page: defaultPage,
								sortOrder: sortOrder
									? invertSortOrder(sortOrder)
									: getDefaultSortOrder(field)
						  })
						: undefined
				}
				onClick={() => {
					onSortOrderChange(
						new OrderParams({
							field,
							sortOrder: invertSortOrder(sortOrder)
						})
					);
				}}
			>
				<span className='text-truncate'>{children}</span>

				{!isNull(sortOrder) && (
					<span className='inline-item inline-item-after'>
						<Icon
							symbol={
								sortOrder === OrderByDirections.Descending
									? 'order-arrow-down'
									: 'order-arrow-up'
							}
						/>
					</span>
				)}
			</Button>
		) : (
			children
		)}
	</th>
);

export default HeaderCell;
