import CustomSelectEntityInput from './components/CustomSelectEntityInput';
import OrganizationsQuery from '../queries/OrganizationsQuery';
import React, {useEffect} from 'react';
import {createOrderIOMap, NAME} from 'shared/util/pagination';
import {EntityType} from '../context/referencedObjects';
import {
	getMapResultToProps,
	mapPropsToOptions
} from '../mappers/dxp-entity-bag-mapper';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {organizationsListColumns} from 'shared/util/table-columns';

interface IOrganizationSelectProps extends ISegmentEditorCustomInputBase {
	touched: boolean;
	valid: boolean;
}

const OrganizationSelectInput: React.FC<IOrganizationSelectProps> = ({
	id,
	property,
	valid,
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

	return (
		<CustomSelectEntityInput
			className='organization-select-input-root'
			columns={organizationsListColumns}
			entityLabel={Liferay.Language.get('organizations')}
			entityType={EntityType.Organizations}
			graphqlProps={{
				graphqlQuery: OrganizationsQuery,
				mapPropsToOptions,
				mapResultToProps: getMapResultToProps('organizations')
			}}
			initialOrderIOMap={createOrderIOMap(NAME)}
			orderByOptions={[
				{
					label: Liferay.Language.get('name'),
					value: NAME
				}
			]}
			property={property}
			valid={valid}
			{...otherProps}
		/>
	);
};

export default OrganizationSelectInput;
