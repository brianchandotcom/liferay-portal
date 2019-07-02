import React from 'react';

export const withParams = (...args) => ({
	location: {search},
	match: {params}
}) =>
	args.map((Component, index) => (
		<Component {...params} key={index} query={search} />
	));
