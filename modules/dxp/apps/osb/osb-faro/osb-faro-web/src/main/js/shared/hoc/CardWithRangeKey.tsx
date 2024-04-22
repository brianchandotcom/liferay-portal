import Card from 'shared/components/Card';
import React from 'react';
import {compose} from 'redux';
import {DropdownRangeKey} from 'shared/components/dropdown-range-key/DropdownRangeKey';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {ReportContainer} from 'shared/components/download-report/DownloadPDFReport';
import {withRangeKey} from 'shared/hoc';
import {WithRangeKeyProps} from 'shared/hoc/WithRangeKey';

interface ICardWithRangeKeyProps
	extends WithRangeKeyProps,
		React.HTMLAttributes<HTMLElement> {
	children: (val) => React.ReactNode;
	label: string;
	legacyDropdownRangeKey: boolean;
	rangeKeys?: Array<RangeKeyTimeRanges>;
	reportContainer: ReportContainer;
}

const CardWithRangeKey = compose(withRangeKey)(
	({
		children,
		className,
		label,
		legacyDropdownRangeKey = true,
		onRangeSelectorsChange,
		rangeKeys,
		rangeSelectors,
		reportContainer,
		...otherProps
	}: ICardWithRangeKeyProps) => (
		<Card className={className} reportContainer={reportContainer}>
			<Card.Header className='align-items-center d-flex justify-content-between'>
				<Card.Title>{label}</Card.Title>

				<DropdownRangeKey
					legacy={legacyDropdownRangeKey}
					onRangeSelectorChange={onRangeSelectorsChange}
					rangeKeys={rangeKeys}
					rangeSelectors={rangeSelectors}
				/>
			</Card.Header>

			{children({...otherProps, rangeSelectors})}
		</Card>
	)
);

export default CardWithRangeKey;
