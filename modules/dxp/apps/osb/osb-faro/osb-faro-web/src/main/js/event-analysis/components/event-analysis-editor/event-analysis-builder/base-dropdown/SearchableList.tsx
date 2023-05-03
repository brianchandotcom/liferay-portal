import ClayDropdown from '@clayui/drop-down';
import ListItem from './ListItem';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';
import {Attribute, Breakdown, Event, Filter} from 'event-analysis/utils/types';
import {spritemap} from 'shared/util/constants';

interface ISearchableListProps {
	activeId?: string;
	disabledIds?: string[];
	items: (Attribute | Event)[];
	onEditClick: (item?: Attribute | Event) => void;
	onItemClick: (
		item: Attribute | Event,
		breakdown?: Breakdown,
		filter?: Filter
	) => void;
	onItemOptionsClick?: (item: Attribute) => void;
	onQueryChange: (query: string) => void;
	query: string;
	showOptionsCondition?: (item: Attribute) => boolean;
	uneditableIds?: string[];
}

const SearchableList: React.FC<ISearchableListProps> = ({
	activeId,
	disabledIds,
	items,
	onEditClick,
	onItemClick,
	onItemOptionsClick,
	onQueryChange,
	query,
	showOptionsCondition = () => false,
	uneditableIds
}) => {
	const filteredItems = items.filter(({displayName, name}) =>
		(displayName || name)
			.toString()
			.toLowerCase()
			.includes(query.toLowerCase())
	);

	const noResults = !filteredItems.length;

	return (
		<>
			<ClayDropdown.Search
				formProps={{onSubmit: e => e.preventDefault()}}
				onChange={onQueryChange}
				placeholder={Liferay.Language.get('search')}
				spritemap={spritemap}
				value={query}
			/>

			{noResults && <NoResultsDisplay spacer />}

			{!noResults && (
				<ClayDropdown.ItemList className='base-dropdown-list'>
					{filteredItems.map(item => {
						const active = activeId === item.id;

						const disabled =
							disabledIds &&
							disabledIds.some(
								id => id === item.id && id !== activeId
							);

						const editable =
							!(
								uneditableIds &&
								uneditableIds.some(id => id === item.id)
							) && !active;

						return (
							<ListItem
								active={active}
								disabled={disabled}
								editable={editable}
								item={item}
								key={item.id}
								onClick={() => onItemClick(item)}
								onEditClick={() => onEditClick(item)}
								onOptionsClick={
									showOptionsCondition(item as Attribute) &&
									onItemOptionsClick
										? () =>
												onItemOptionsClick(
													item as Attribute
												)
										: null
								}
							/>
						);
					})}
				</ClayDropdown.ItemList>
			)}
		</>
	);
};

export default SearchableList;
