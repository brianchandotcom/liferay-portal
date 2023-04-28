import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import Constants, {OrderByDirections} from 'shared/util/constants';
import getCN from 'classnames';
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
}) => {
	const ButtonContent = () => (
		<>
			<span className='text-truncate'>{children}</span>

			{!isNull(sortOrder) && (
				<span className='inline-item inline-item-after'>
					<ClayIcon
						className='icon-root'
						symbol={
							sortOrder === OrderByDirections.Descending
								? 'order-arrow-down'
								: 'order-arrow-up'
						}
					/>
				</span>
			)}
		</>
	);

	return (
		<th className={getCN('table-head-title', className)}>
			{sortable ? (
				headerLink ? (
					<ClayLink
						button
						className='button-root inline-item text-truncate-inline'
						displayType='unstyled'
						href={setUriQueryValues({
							field,
							page: defaultPage,
							sortOrder: sortOrder
								? invertSortOrder(sortOrder)
								: getDefaultSortOrder(field)
						})}
					>
						<ButtonContent />
					</ClayLink>
				) : (
					<ClayButton
						className='inline-item text-truncate-inline'
						displayType='unstyled'
						onClick={() => {
							onSortOrderChange(
								new OrderParams({
									field,
									sortOrder: invertSortOrder(sortOrder)
								})
							);
						}}
					>
						<ButtonContent />
					</ClayButton>
				)
			) : (
				children
			)}
		</th>
	);
};

export default HeaderCell;
