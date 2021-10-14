import ClayIcon from '@clayui/icon';

import {CHECK_VALUE, NATURAL_VALUE, UNCHECKED_VALUE} from './constants';

const COLORS = {
	[CHECK_VALUE]: {color: '#39C38D', symbol: 'check'},
	[NATURAL_VALUE]: {fill: '#606167', symbol: 'check'},
	[UNCHECKED_VALUE]: {color: '#EA6136', symbol: 'hr'},
};

export const RuleIcon = ({label, status}) => {
	const ruleConfig = COLORS[status] || {};

	const color = ruleConfig.fill ? ruleConfig.fill : ruleConfig.color;

	return (
		<a style={{color}}>
			<ClayIcon
				className="ca-icon-rule"
				style={{
					...(ruleConfig.fill && {fill: ruleConfig.fill}),
					...(!ruleConfig.fill && {background: ruleConfig.color}),
				}}
				symbol={ruleConfig.symbol}
			/>
			{label}
		</a>
	);
};
