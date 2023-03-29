import * as API from 'shared/api';
import CustomStringInput from './CustomStringInput';
import React, {useEffect} from 'react';
import {getPropertyValue} from '../utils/custom-inputs';
import {ICustomStringInputProps} from './CustomStringInput';

const OrganizationTextInput: React.FC<ICustomStringInputProps> = props => {
	const {
		channelId,
		groupId,
		property: {entityName, id, type},
		valid,
		value
	} = props;

	let _completedAnalytics = false;

	useEffect(() => {
		if (!id && valid && !_completedAnalytics) {
			_completedAnalytics = true;

			analytics.track('Dynamic Segment Creation - Completed Attribute', {
				entityName,
				type
			});
		}
	});

	const fieldValuesDataSourceFn = () =>
		API.individuals
			.fetchFieldValues({
				channelId,
				fieldMappingFieldName: id,
				groupId,
				query: getPropertyValue(value, 'value', 0)
			})
			.then(({items}) => items);
	return (
		<CustomStringInput
			{...props}
			autocomplete={!!id}
			fieldValuesDataSourceFn={fieldValuesDataSourceFn}
		/>
	);
};
export default OrganizationTextInput;
