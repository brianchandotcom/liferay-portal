import React from 'react';
import {Text} from '@clayui/core';

interface ISubHeaderProps {
	title: string;
}

export const SubHeader: React.FC<ISubHeaderProps> = ({title}) => (
	<>
		<Text color='secondary' size={3} weight='semi-bold'>
			{title.toUpperCase()}
		</Text>

		<hr className='my-2' />
	</>
);
