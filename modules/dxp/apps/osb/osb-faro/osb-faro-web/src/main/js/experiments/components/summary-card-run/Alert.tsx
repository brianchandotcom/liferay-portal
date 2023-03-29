import ClayIcon from '@clayui/icon';
import React from 'react';
import {CLASSNAME} from '../summary-base-card/constants';

interface SummaryBaseCardAlertIProps extends React.HTMLAttributes<HTMLElement> {
	symbol?: string;
}

const SummaryBaseCardAlert: React.FC<SummaryBaseCardAlertIProps> = ({
	children,
	symbol
}) => (
	<div className={`${CLASSNAME}-alert w-100 p-4`}>
		{symbol ? (
			<>
				<ClayIcon symbol={symbol} />
				<div>{children}</div>
			</>
		) : (
			children
		)}
	</div>
);

export default SummaryBaseCardAlert;
