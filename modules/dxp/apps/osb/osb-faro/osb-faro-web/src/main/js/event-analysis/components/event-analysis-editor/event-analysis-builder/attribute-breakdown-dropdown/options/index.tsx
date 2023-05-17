import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import DateBreakdown from './DateBreakdown';
import DurationBreakdown from './DurationBreakdown';
import FilterInfo from '../../FilterInfo';
import NumberBreakdown from './NumberBreakdown';
import React from 'react';
import {
	AddBreakdown,
	EditBreakdown,
	withAttributesConsumer
} from '../../../context/attributes';
import {
	Attribute,
	AttributeOwnerTypes,
	Breakdowns,
	DataTypes
} from 'event-analysis/utils/types';

const BREAKDOWNS_MAP = {
	[DataTypes.Date]: DateBreakdown,
	[DataTypes.Duration]: DurationBreakdown,
	[DataTypes.Number]: NumberBreakdown
};

interface IBreakdownOptionsProps extends React.HTMLAttributes<HTMLDivElement> {
	addBreakdown: AddBreakdown;
	attribute: Attribute;
	attributeOwnerType: AttributeOwnerTypes;
	breakdownId?: string;
	breakdowns: Breakdowns;
	editBreakdown: EditBreakdown;
	onActiveChange: (active: boolean) => void;
	onAttributeChange: (attribute?: Attribute) => void;
	onEditClick?: (id: string) => void;
}

const BreakdownOptions: React.FC<IBreakdownOptionsProps> = ({
	addBreakdown,
	attribute,
	attributeOwnerType,
	breakdownId,
	breakdowns,
	editBreakdown,
	onActiveChange,
	onAttributeChange,
	onEditClick
}) => {
	const {
		dataType,
		description,
		displayName,
		id: attributeId,
		name
	} = attribute;

	const breakdown = breakdowns[breakdownId];

	const BreakdownBody = BREAKDOWNS_MAP[dataType];

	return (
		<div className='attribute-options'>
			<div className='options-header'>
				<ClayButton
					className='button-root back-to-attributes-button'
					displayType='unstyled'
					onClick={() => onAttributeChange(null)}
					size='sm'
				>
					<ClayIcon
						className='icon-root mr-2'
						symbol='angle-left-small'
					/>

					{Liferay.Language.get('back-to-attributes')}
				</ClayButton>

				<FilterInfo
					dataType={dataType}
					name={displayName || name}
					onEditClick={onEditClick}
				/>
			</div>

			<BreakdownBody
				attributeId={attributeId}
				attributeOwnerType={attributeOwnerType}
				breakdown={
					breakdown?.attributeId === attributeId ? breakdown : null
				}
				description={description}
				displayName={displayName}
				onSubmit={newBreakdown => {
					if (breakdownId) {
						editBreakdown({
							attribute,
							breakdown: newBreakdown,
							id: breakdownId
						});
					} else {
						addBreakdown({
							attribute,
							breakdown: newBreakdown
						});
					}

					onAttributeChange(null);

					onActiveChange(false);
				}}
			/>
		</div>
	);
};

export default withAttributesConsumer(BreakdownOptions);
