import ClayLoadingIndicator from '@clayui/loading-indicator';
import getCN from 'classnames';
import React, {FC} from 'react';

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
		<ClayLoadingIndicator
			className={getCN(fadeIn, 'spinner-root', 'spinner-fade-in')}
		/>
	</div>
);

export default Loading;
