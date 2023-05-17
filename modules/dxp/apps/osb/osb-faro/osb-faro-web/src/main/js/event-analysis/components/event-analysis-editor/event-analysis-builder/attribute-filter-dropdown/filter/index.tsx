import BooleanFilter from './BooleanFilter';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import DateFilter from './DateFilter';
import DurationFilter from './DurationFilter';
import FilterInfo from '../../FilterInfo';
import NumberFilter from './NumberFilter';
import React from 'react';
import StringFilter from './StringFilter';
import {
	AddFilter,
	EditFilter,
	withAttributesConsumer
} from '../../../context/attributes';
import {
	Attribute,
	AttributeOwnerTypes,
	DataTypes,
	Filter,
	Filters
} from 'event-analysis/utils/types';

const FILTERS_MAP = {
	[DataTypes.Boolean]: BooleanFilter,
	[DataTypes.Date]: DateFilter,
	[DataTypes.Duration]: DurationFilter,
	[DataTypes.Number]: NumberFilter,
	[DataTypes.String]: StringFilter
};

interface IFilterOptionsProps extends React.HTMLAttributes<HTMLDivElement> {
	addFilter: AddFilter;
	attribute: Attribute;
	attributeOwnerType: AttributeOwnerTypes;
	editFilter: EditFilter;
	eventId: string;
	filterId?: string;
	filters: Filters;
	onActiveChange: (active: boolean) => void;
	onAttributeChange: (attribute?: Attribute) => void;
	onEditClick?: (id: string) => void;
}

const FilterOptions: React.FC<IFilterOptionsProps> = ({
	addFilter,
	attribute,
	attributeOwnerType,
	editFilter,
	eventId,
	filterId,
	filters,
	onActiveChange,
	onAttributeChange,
	onEditClick
}) => {
	const {
		dataType,
		description,
		displayName,
		id: attributeId,
		name,
		type
	} = attribute;

	const FilterBody = FILTERS_MAP[dataType];

	const filter = filters[filterId];

	const onSubmit = (newFilter: Filter) => {
		if (filterId) {
			editFilter({
				attribute,
				filter: newFilter,
				id: filterId
			});
		} else {
			addFilter({
				attribute,
				filter: newFilter
			});
		}

		onAttributeChange(null);

		onActiveChange(false);

		const {attributeType} = newFilter;
		analytics.track('Event Analysis Editor - Selected a Filter', {
			attributeName: displayName || name,
			attributeType,
			type
		});
	};

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

			<FilterBody
				attributeId={attributeId}
				attributeOwnerType={attributeOwnerType}
				description={description}
				displayName={displayName}
				eventId={eventId}
				filter={filter?.attributeId === attributeId ? filter : null}
				onSubmit={onSubmit}
			/>
		</div>
	);
};

export default withAttributesConsumer(FilterOptions);
