import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import HeaderDefault, {BaseCardHeaderDefaultIProps} from './HeaderDefault';
import React, {useContext, useState} from 'react';
import {INTERVAL_KEY_MAP} from 'shared/util/time';
import {RangeSelectors} from 'shared/types';
import {ReportContainer} from '../download-report/DownloadPDFReport';
import {useQueryRangeSelectors} from 'shared/hooks/useQueryRangeSelectors';

interface BaseCardIProps extends React.HTMLAttributes<HTMLElement> {
	className?: string;
	children: (val) => React.ReactNode;
	Header?: React.FC<BaseCardHeaderDefaultIProps>;
	headerProps?: {[key: string]: any};
	label: string;
	legacyDropdownRangeKey: boolean;
	minHeight?: number;
	reportContainer?: ReportContainer;
	showInterval?: boolean;
}

const BaseCard: React.FC<BaseCardIProps> = ({
	Header = HeaderDefault,
	children,
	className,
	headerProps = {},
	id,
	label,
	legacyDropdownRangeKey = true,
	minHeight,
	reportContainer,
	showInterval = false
}) => {
	const context = useContext(BasePage.Context);

	const {filters, router} = context;

	const [interval, setInterval] = useState(INTERVAL_KEY_MAP.day);

	const initialRangeSelectors = useQueryRangeSelectors();

	const [rangeSelectors, setRangeSelectors] = useState<RangeSelectors>(
		initialRangeSelectors
	);

	const otherProps = {
		filters,
		interval,
		onChangeInterval: setInterval,
		onRangeSelectorsChange: setRangeSelectors,
		rangeSelectors,
		router
	};

	return (
		<Card
			className={className}
			id={id}
			minHeight={minHeight}
			reportContainer={reportContainer}
		>
			<Header
				{...otherProps}
				label={label}
				legacy={legacyDropdownRangeKey}
				showInterval={showInterval}
				{...headerProps}
			/>

			{children({...otherProps})}
		</Card>
	);
};

export default BaseCard;
