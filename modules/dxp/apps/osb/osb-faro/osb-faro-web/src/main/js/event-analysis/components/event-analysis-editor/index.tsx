import BreakdownTable from './event-analysis-breakdown';
import Card from 'shared/components/Card';
import CardTabs, {CardTabSizes} from 'shared/components/CardTabs';
import Checkbox from 'shared/components/Checkbox';
import DropdownRangeKey from 'shared/hoc/DropdownRangeKey';
import EventAnalysisBuilder from './event-analysis-builder';
import React from 'react';
import {CalculationTypes, Event} from 'event-analysis/utils/types';
import {RangeSelectors} from 'shared/types';

interface IEventAnalysisEditorProps extends React.HTMLAttributes<HTMLElement> {
	channelId: string;
	compareToPrevious: boolean;
	event: Event;
	onCompareToPreviousChange: (compareToPrevious: boolean) => void;
	onEventChange: (event: Event) => void;
	onRangeSelectorsChange: (rangeSelectors: RangeSelectors) => void;
	onTypeChange: (type: CalculationTypes) => void;
	type: CalculationTypes;
	rangeSelectors: RangeSelectors;
}

const EventAnalysisEditor: React.FC<IEventAnalysisEditorProps> = ({
	channelId,
	compareToPrevious,
	event,
	onCompareToPreviousChange,
	onEventChange,
	onRangeSelectorsChange,
	onTypeChange,
	rangeSelectors,
	type
}) => (
	<Card className='event-analysis-editor-root'>
		<EventAnalysisBuilder event={event} onEventChange={onEventChange} />

		<div className='options-container d-flex justify-content-between'>
			<CardTabs
				activeTabId={type}
				className='type-selector'
				size={CardTabSizes.Small}
				tabs={[
					{
						onClick: () => onTypeChange(CalculationTypes.Total),
						tabId: CalculationTypes.Total,
						title: Liferay.Language.get('total')
					},
					{
						onClick: () => onTypeChange(CalculationTypes.Unique),
						tabId: CalculationTypes.Unique,
						title: Liferay.Language.get('unique')
					},
					{
						onClick: () => onTypeChange(CalculationTypes.Average),
						tabId: CalculationTypes.Average,
						title: Liferay.Language.get('average')
					}
				]}
			/>

			<div className='d-flex align-items-center'>
				<Checkbox
					checked={compareToPrevious}
					className='compare-to-previous-checkbox mb-0 mr-4'
					label={Liferay.Language.get('compare-to-previous')}
					onChange={event =>
						onCompareToPreviousChange(event.currentTarget.checked)
					}
				/>

				<DropdownRangeKey
					legacy={false}
					onChange={onRangeSelectorsChange}
					rangeSelectors={rangeSelectors}
				/>
			</div>
		</div>

		<BreakdownTable
			channelId={channelId}
			compareToPrevious={compareToPrevious}
			event={event}
			rangeSelectors={rangeSelectors}
			type={type}
		/>
	</Card>
);

export default EventAnalysisEditor;
