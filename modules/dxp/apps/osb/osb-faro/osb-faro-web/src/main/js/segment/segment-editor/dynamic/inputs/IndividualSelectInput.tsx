import DXPUsersQuery from '../queries/DXPUsersQuery';
import getDXPEntitiesQuery from '../queries/DXPEntitiesQuery';
import React, {useEffect} from 'react';
import SelectEntityInput from './components/SelectEntityInput';
import {createOrderIOMap, NAME} from 'shared/util/pagination';
import {EntityType} from '../context/referencedObjects';
import {
	getMapResultToProps,
	mapPropsToOptions
} from '../mappers/dxp-entity-bag-mapper';
import {IPagination} from 'shared/types';
import {ISegmentEditorInputBase} from '../utils/types';
import {OrderedMap} from 'immutable';

const QUERY_MAP = {
	userId: DXPUsersQuery
};

export const ENTITY_MAP = {
	groupIds: EntityType.Groups,
	roleIds: EntityType.Roles,
	teamIds: EntityType.Teams,
	userGroupIds: EntityType.UserGroups,
	userId: EntityType.Users
};

const LABEL_MAP = {
	groupIds: Liferay.Language.get('site-memberships'),
	roleIds: Liferay.Language.get('roles'),
	teamIds: Liferay.Language.get('teams'),
	userGroupIds: Liferay.Language.get('user-groups')
};

const nameCol = {
	accessor: 'name',
	className: 'table-cell-expand',
	label: Liferay.Language.get('name'),
	title: true
};

const PROPERTY_COLUMNS_MAP = {
	userId: [
		nameCol,
		{
			accessor: 'screenName',
			className: 'table-cell-expand',
			label: Liferay.Language.get('screen-name')
		}
	]
};

interface IIndividualSelectProps extends ISegmentEditorInputBase, IPagination {
	channelId: string;
	groupId: string;
	touched: boolean;
	valid: boolean;
	value: string;
}

const IndividualSelectInput: React.FC<IIndividualSelectProps> = ({
	channelId,
	id,
	onChange,
	property,
	valid,
	value,
	...otherProps
}) => {
	let _completedAnalytics = false;

	const {entityName, type} = property;

	useEffect(() => {
		if (!id && valid && !_completedAnalytics) {
			_completedAnalytics = true;

			analytics.track('Dynamic Segment Creation - Completed Attribute', {
				entityName,
				type
			});
		}
	});

	const entityType: EntityType = ENTITY_MAP[property.name];

	const graphqlEntityType =
		entityType === EntityType.UserGroups
			? 'userGroups'
			: (entityType as string);

	const handleItemsChange = (items: OrderedMap<string, any>) => {
		const entity = items.first();

		if (items.size === 1) {
			onChange({
				valid: true,
				value: entity.id
			});
		} else {
			onChange(
				items
					.valueSeq()
					.toArray()
					.map(({id}) => ({
						valid: true,
						value: id
					}))
			);
		}
	};

	return (
		<SelectEntityInput
			channelId={channelId}
			className='individual-select-input-root'
			columns={PROPERTY_COLUMNS_MAP[property.name] || [nameCol]}
			entityLabel={LABEL_MAP[property.name]}
			entityType={entityType}
			graphqlProps={{
				graphqlQuery:
					QUERY_MAP[property.name] ||
					getDXPEntitiesQuery(graphqlEntityType),
				mapPropsToOptions,
				mapResultToProps: getMapResultToProps(graphqlEntityType)
			}}
			initialOrderIOMap={createOrderIOMap(NAME)}
			onItemsChange={handleItemsChange}
			onValidChange={onChange}
			orderByOptions={[
				{
					label: Liferay.Language.get('name'),
					value: NAME
				}
			]}
			property={property}
			valid={valid}
			value={value}
			{...otherProps}
		/>
	);
};

export default IndividualSelectInput;
