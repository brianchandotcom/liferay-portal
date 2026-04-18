import CountryFilter from './CountryFilter';
import IndustryFilter from './IndustryFilter';
import React from 'react';

const GlobalFilters = () => (
	<div className='d-flex'>
		<IndustryFilter />

		<CountryFilter />
	</div>
);

export default GlobalFilters;
