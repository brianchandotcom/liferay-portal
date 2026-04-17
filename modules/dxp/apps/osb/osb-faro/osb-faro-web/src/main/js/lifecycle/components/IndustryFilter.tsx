import FieldValueFilter from './FieldValueFilter';
import React from 'react';

const IndustryFilter = () => (
	<FieldValueFilter
		entityLabel={Liferay.Language.get('industries')}
		fieldMappingFieldName='industry'
		filterKey='industryFilter'
	/>
);

export default IndustryFilter;
