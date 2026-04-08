import ClayLabel from '@clayui/label';
import FaroConstants from 'shared/util/constants';
import React from 'react';

const {cur, delta, deltaValues} = FaroConstants.pagination;

export const pagination = {
	deltas: deltaValues.map(delta => ({label: delta})),
	initialDelta: delta,
	initialPageNumber: cur
};


export const frontendDataSetColumns = {
	cmsLabel: ({
		displayType,
		label
	}: {
		displayType: 'danger' | 'info' | 'secondary' | 'success' | 'warning';
		label: React.ReactNode;
	}) =>
		React.createElement(
			ClayLabel,
			{
				className: 'fds-label font-weight-semi-bold rounded',
				displayType
			},
			label
		)
};
