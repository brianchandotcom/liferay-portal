import getCN from 'classnames';
import React from 'react';

export const SummaryTitle = ({className, label}) => (
	<h3 className={getCN('font-weight-bold', className)}>{label}</h3>
);
