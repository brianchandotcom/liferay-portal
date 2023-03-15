import React from 'react';
import {CLASSNAME} from './index';

interface SummarySectionHeadingIProps
	extends React.HTMLAttributes<HTMLElement> {
	value: string;
}

const SummarySectionHeading: React.FC<SummarySectionHeadingIProps> = ({
	value
}) => <h2 className={`${CLASSNAME}-heading`}>{value}</h2>;

export default SummarySectionHeading;
