import FilterInfo from './FilterInfo';
import React from 'react';
import {DataTypes} from 'event-analysis/utils/types';

interface IInfoCardPopoverProps {
	dataType: DataTypes;
	description: string;
	name: string;
	onEditClick?: (id: string) => void;
}

const InfoCardPopover: React.FC<IInfoCardPopoverProps> = ({
	dataType,
	description,
	name,
	onEditClick
}) => (
	<div className='info-card-popover-root'>
		<FilterInfo
			dataType={dataType}
			description={description}
			name={name}
			onEditClick={onEditClick}
			showDescription
		/>
	</div>
);

export default InfoCardPopover;
