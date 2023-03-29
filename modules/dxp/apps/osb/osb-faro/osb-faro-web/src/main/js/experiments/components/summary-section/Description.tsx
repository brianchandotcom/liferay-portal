import React from 'react';
import {CLASSNAME} from './index';

interface SummarySectionDescriptionIProps
	extends React.HTMLAttributes<HTMLElement> {
	value: string;
}

const SummarySectionDescription: React.FC<SummarySectionDescriptionIProps> = ({
	value
}) => <div className={`${CLASSNAME}-description`}>{value}</div>;

export default SummarySectionDescription;
