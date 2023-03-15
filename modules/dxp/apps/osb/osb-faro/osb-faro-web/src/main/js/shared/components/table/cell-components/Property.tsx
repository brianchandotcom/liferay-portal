import getCN from 'classnames';
import React from 'react';
import {getSafeDisplayValue} from 'shared/util/util';

interface IPropertyCellProps {
	className?: string;
	data: {
		name: string;
		value: string;
	};
}

const PropertyCell: React.FC<IPropertyCellProps> = ({
	className,
	data: {name, value}
}) => (
	<td className={getCN('property-cell', className)}>
		<div className='name'>{name}</div>

		<div className='table-title'>{getSafeDisplayValue(value)}</div>
	</td>
);

export default PropertyCell;
