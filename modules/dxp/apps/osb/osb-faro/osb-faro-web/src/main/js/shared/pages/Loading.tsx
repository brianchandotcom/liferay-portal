import getCN from 'classnames';
import React, {FC} from 'react';
import Spinner from '../components/Spinner';

export interface ILoadingProps extends React.HTMLAttributes<HTMLDivElement> {
	fadeIn?: boolean;
	displayCard?: boolean;
}

const Loading: FC<ILoadingProps> = ({
	className,
	displayCard = false,
	fadeIn = true,
	...otherProps
}) => (
	<div
		className={getCN('loading-root', className, {
			'display-card': displayCard
		})}
		{...otherProps}
	>
		<Spinner fadeIn={fadeIn} />
	</div>
);

export default Loading;
