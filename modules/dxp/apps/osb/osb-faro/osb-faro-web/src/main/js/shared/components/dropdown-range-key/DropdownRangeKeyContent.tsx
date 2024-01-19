import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import moment from 'moment';
import React, {useEffect, useState} from 'react';
import {Text as ClayText} from '@clayui/core';
import {Data, DropdownRangeKeyIProps} from './DropdownRangeKey';
import {DEFAULT_DATE_FORMAT} from 'shared/util/date';
import {DropdownRangeKeyDatePicker} from './DatePicker';
import {DropdownRangeKeyLegacy} from './DropdownRangeKeyLegacy';
import {formatTimeRange, getFilteredItems, getSelectedItem} from './utils';
import {MomentDateRange} from 'shared/components/DateRangeInput';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {useHistory} from 'react-router-dom';
import {useRetentionPeriod} from 'shared/hooks/useRetentionPeriod';

export const DropdownRangeKeyContent: React.FC<
	DropdownRangeKeyIProps & {data: Data}
> = ({
	data,
	legacy = true, // legacy can be removed once we convert all uses of DropdownRangeKey to include the new values.
	onRangeSelectorChange,
	/**
	 * When legacy props is true, rangeKeys will be ignored.
	 */
	rangeKeys,
	rangeSelectors: {rangeEnd, rangeKey, rangeStart} = {
		rangeEnd: '',
		rangeKey: RangeKeyTimeRanges.Last30Days,
		rangeStart: ''
	}
}) => {
	const [active, setActive] = useState(false);
	const [customDateRange, setCustomDateRange] = useState<MomentDateRange>({
		end: rangeEnd ? moment(rangeEnd, DEFAULT_DATE_FORMAT) : null,
		start: rangeStart ? moment(rangeStart, DEFAULT_DATE_FORMAT) : null
	});
	const [seeMore, setSeeMore] = useState(false);
	const [showDatePicker, setShowDatePicker] = useState(false);
	const history = useHistory();
	const retentionPeriod = useRetentionPeriod();
	const timeRange = formatTimeRange(data.timeRange);
	const filteredItems = getFilteredItems({
		legacy,
		rangeKey,
		rangeKeys,
		retentionPeriod,
		seeMore,
		timeRange
	});
	const selectedItem = getSelectedItem({
		rangeEnd,
		rangeKey,
		rangeStart,
		timeRange
	});

	useEffect(() => {
		const unlisten = history.listen(location => {
			const query = new URLSearchParams(location.search);

			if (query.get('downloadReport')) {
				onRangeSelectorChange &&
					onRangeSelectorChange({
						rangeEnd: query.get('rangeEnd'),
						rangeKey: RangeKeyTimeRanges.CustomRange,
						rangeStart: query.get('rangeStart')
					});
			}
		});

		return () => {
			unlisten();
		};
	}, []);

	useEffect(() => {
		if (customDateRange && customDateRange.end && customDateRange.start) {
			const {end, start} = customDateRange;

			const dateRangeItem = {
				label: `${start.format('ll')} - ${end.format('ll')}`,
				value: RangeKeyTimeRanges.CustomRange
			};

			onRangeSelectorChange &&
				onRangeSelectorChange({
					rangeEnd: customDateRange.end.format(DEFAULT_DATE_FORMAT),
					rangeKey: dateRangeItem.value,
					rangeStart: customDateRange.start.format(
						DEFAULT_DATE_FORMAT
					)
				});

			setActive(false);
		}
	}, [customDateRange]);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={3}
			className='dropdown-range-key-root'
			menuElementAttrs={{
				className: getCN('dropdown-range-key-menu-root', {
					'show-date-picker': showDatePicker
				})
			}}
			onActiveChange={active => {
				setActive(active);
				setShowDatePicker(false);
				setSeeMore(false);
			}}
			trigger={
				<ClayButton
					borderless
					className='button-root'
					displayType='secondary'
					size='sm'
				>
					{selectedItem.label}

					<ClayIcon
						className='icon-root ml-2'
						symbol='caret-bottom'
					/>
				</ClayButton>
			}
		>
			{showDatePicker ? (
				<DropdownRangeKeyDatePicker
					customDateRange={customDateRange}
					onCustomRangeChange={setCustomDateRange}
					retentionPeriod={retentionPeriod}
				/>
			) : (
				<ClayDropDown.ItemList>
					{filteredItems.map(({description, label, value}, index) => (
						<ClayDropDown.Item
							className={getCN('c-pointer', {
								active: selectedItem.value === value
							})}
							key={index}
							onClick={() => {
								setActive(false);

								onRangeSelectorChange &&
									onRangeSelectorChange({
										rangeEnd: '',
										rangeKey: value,
										rangeStart: ''
									});

								setCustomDateRange({
									end: null,
									start: null
								});
							}}
						>
							<div>{label}</div>

							<ClayText size={1}>{description}</ClayText>
						</ClayDropDown.Item>
					))}

					{!legacy && (
						<DropdownRangeKeyLegacy
							onClickSeeMore={() => setSeeMore(true)}
							onClickShowDatePicker={() =>
								setShowDatePicker(true)
							}
							seeMore={seeMore}
							selectedItem={selectedItem}
						/>
					)}
				</ClayDropDown.ItemList>
			)}
		</ClayDropDown>
	);
};
