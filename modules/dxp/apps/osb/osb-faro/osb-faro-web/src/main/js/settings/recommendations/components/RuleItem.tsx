import MetadataTag from './MetadataTag';
import React from 'react';
import {getFilterValueBreakdown, RULE_NAME_LABEL_MAP} from '../utils/utils';

interface IRuleItemProps {
	name: string;
	value: string;
}

const RuleItem: React.FC<IRuleItemProps> = ({name, value}) => {
	const {exactMatchSign, metadataTag, rule} = getFilterValueBreakdown(value);

	const exactMatch = exactMatchSign === '=';

	return (
		<div className='rule-item-root d-flex align-items-baseline'>
			<b>{`${RULE_NAME_LABEL_MAP[name]}:`}</b>

			{metadataTag && <MetadataTag value={metadataTag} />}

			<span className='rule-value'>
				{exactMatch ? `"${rule}"` : rule}
			</span>
		</div>
	);
};

export default RuleItem;
