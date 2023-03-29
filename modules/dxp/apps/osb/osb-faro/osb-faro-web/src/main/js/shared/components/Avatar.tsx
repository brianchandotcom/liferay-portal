import getCN from 'classnames';
import React from 'react';
import Sticker, {getDisplayForId, getSymbol} from './Sticker';
import {EntityTypes} from '../util/constants';
import {get} from 'lodash';

const getInitials = (first: string, last: string): string => {
	let retVal = first ? first.substring(0, 1) : '';

	if (last) {
		retVal += last.substring(0, 1);
	}

	return retVal.toUpperCase();
};

interface IAvatarProps extends React.HTMLAttributes<HTMLDivElement> {
	entity: {
		id: string;
		properties?: {
			familyName: string;
			givenName: string;
		};
		type: EntityTypes;
	};
}

const Avatar: React.FC<IAvatarProps> = ({
	className,
	entity: {id, properties, type},
	...otherProps
}) => {
	const image = get(properties, 'image');

	return (
		<Sticker
			className={getCN('avatar-root', className)}
			display={getDisplayForId(id)}
			style={
				!image
					? undefined
					: {
							backgroundImage: `url(${image})`
					  }
			}
			symbol={type !== EntityTypes.Individual ? getSymbol(type) : null}
			{...otherProps}
		>
			{type === EntityTypes.Individual &&
				!image &&
				getInitials(properties.givenName, properties.familyName)}
		</Sticker>
	);
};

export default Avatar;
