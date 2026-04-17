import CountryFilter from './CountryFilter';
import IndustryFilter from './IndustryFilter';
import React from 'react';

const GlobalFilter = () => (
	<div className='d-flex '>
		<IndustryFilter />

		<CountryFilter />
	</div>
);

export default GlobalFilter;
