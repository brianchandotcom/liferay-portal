import ClayIcon from '@clayui/icon';
import React from 'react';
import {CLASSNAME} from './index';
import {sub} from 'shared/util/lang';

type Status = 'up' | 'down';

type Symbol = 'caret-top' | 'caret-bottom';

interface SummarySectionVariantIProps
	extends React.HTMLAttributes<HTMLElement> {
	lift: string;
	status: Status;
}

const SummarySectionVariant: React.FC<SummarySectionVariantIProps> = ({
	lift,
	status
}) => {
	const symbol: Symbol = status === 'up' ? 'caret-top' : 'caret-bottom';

	return (
		<div className={`${CLASSNAME}-variant`}>
			<span
				className={`${CLASSNAME}-variant-status ${CLASSNAME}-variant-status-${status}`}
			>
				<ClayIcon className='icon-root' symbol={symbol} />
				{` ${sub(Liferay.Language.get('x-lift'), [lift])}`}
			</span>

			<span>{Liferay.Language.get('over-control')}</span>
		</div>
	);
};

export default SummarySectionVariant;
