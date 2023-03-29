import getCN from 'classnames';
import Label from 'shared/components/Label';
import React from 'react';
import {AccessToken} from '../types';
import {isExpired} from '../pages/AccessTokenList';

const TokenCell: React.FC<
	{data: AccessToken} & React.HTMLAttributes<HTMLElement>
> = ({className, data: {expirationDate, token}}) => {
	const expired = isExpired(expirationDate);

	return (
		<td className={getCN(className)}>
			<span className='text-secondary mr-1'>
				{Liferay.Language.get('token-ending-in-fragment')}
			</span>

			<strong className='font-weight-bold'>{token.slice(-4)}</strong>

			{expired && (
				<Label className='ml-2' display='danger' size='lg' uppercase>
					{Liferay.Language.get('expired')}
				</Label>
			)}
		</td>
	);
};

export default TokenCell;
