import React from 'react';
import {Text} from '@clayui/core';

interface IUsageMetricProps {
	description: string;
	title: string;
}

export const UsageMetric: React.FC<IUsageMetricProps> = ({
	children,
	description,
	title
}) => (
	<>
		<Text color='secondary' size={3} weight='semi-bold'>
			{title.toUpperCase()}
		</Text>

		<hr className='my-2' />

		<Text color='secondary' size={3}>
			{description}
		</Text>

		<div className='mt-3'>{children}</div>
	</>
);
