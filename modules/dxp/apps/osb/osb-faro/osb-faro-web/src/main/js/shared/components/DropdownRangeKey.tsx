import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import DatePicker from './date-picker';
import getCN from 'classnames';
import moment from 'moment';
import React, {useEffect, useState} from 'react';
import {DEFAULT_DATE_FORMAT} from 'shared/util/date';
import {MomentDateRange} from 'shared/components/DateRangeInput';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {RangeSelectors} from 'shared/types';

const {
	Last7Days,
	Last24Hours,
	Last28Days,
	Last30Days,
	Last90Days,
	Yesterday
} = RangeKeyTimeRanges;

const initialRangeKeys = [Last24Hours, Last7Days, Last30Days, Last90Days];

const legacyRangeKeys = [
	Last24Hours,
	Yesterday,
	Last7Days,
	Last28Days,
	Last30Days,
	Last90Days
];

type Item = {
	description?: string;
	label: string;
	value: RangeKeyTimeRanges;
};

interface DropdownRangeKeyIProps {
	className: string;
	disabled: boolean;
	items: Array<Item>;
	legacy: boolean;
	onChange: (rangeSelectors: RangeSelectors) => void;
	rangeKeys: Array<RangeKeyTimeRanges>;
	rangeSelectors: RangeSelectors;
}

const DropdownRangeKey: React.FC<DropdownRangeKeyIProps> = ({
	className,
	disabled = false,
	items,
	legacy = true, // legacy can be removed once we convert all uses of DropdownRangeKey to include the new values.
	onChange,
	/**
	 * When legacy props is true, rangeKeys will be ignored.
	 */
	rangeKeys = initialRangeKeys,
	rangeSelectors: {rangeEnd, rangeKey, rangeStart} = {
		rangeEnd: '',
		rangeKey: Last30Days,
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

	useEffect(() => {
		if (customDateRange && customDateRange.end && customDateRange.start) {
			const {end, start} = customDateRange;

			const dateRangeItem = {
				label: `${start.format('ll')} - ${end.format('ll')}`,
				value: RangeKeyTimeRanges.CustomRange
			};

			onChange({
				rangeEnd: customDateRange.end.format(DEFAULT_DATE_FORMAT),
				rangeKey: dateRangeItem.value,
				rangeStart: customDateRange.start.format(DEFAULT_DATE_FORMAT)
			});

			setActive(false);
			setShowDatePicker(false);
		}
	}, [customDateRange]);

	useEffect(() => {
		if (showDatePicker) {
			setActive(true);
		}
	}, [showDatePicker]);

	const filterItems = () => {
		if (legacy) {
			return items.filter(({value}) =>
				legacyRangeKeys.includes(value as RangeKeyTimeRanges)
			);
		} else if (seeMore) {
			return items.filter(
				item =>
					!initialRangeKeys
						.filter(value => !rangeKeys.includes(value))
						.includes(item.value)
			);
		}

		return items.filter(
			({value}) =>
				value === rangeKey ||
				rangeKeys.includes(value as RangeKeyTimeRanges)
		);
	};

	const getSelectedItem = () => {
		if (rangeKey === 'CUSTOM') {
			return {
				label: `${moment(rangeStart).format('ll')} - ${moment(
					rangeEnd
				).format('ll')}`,
				value: 'CUSTOM'
			};
		}

		return items.find(({value}) => value === rangeKey) || items[0];
	};

	const handleValueChange = (item: Item) => {
		setActive(false);

		onChange &&
			onChange({
				rangeEnd: '',
				rangeKey: item.value,
				rangeStart: ''
			});

		setCustomDateRange({end: null, start: null});
	};

	const selectedItem = getSelectedItem();

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={3}
			className={getCN(className, 'dropdown-range-key-root')}
			menuElementAttrs={{
				className: getCN('dropdown-range-key-menu-root', {
					'show-date-picker': showDatePicker
				})
			}}
			onActiveChange={active => {
				setActive(active);

				setShowDatePicker(false);
			}}
			trigger={
				<ClayButton
					borderless
					className='button-root'
					disabled={disabled}
					displayType='secondary'
					small
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
				<DatePicker
					date={customDateRange}
					maxDate={moment().subtract(1, 'days')}
					maxRange={365}
					minDate={moment().subtract(10, 'years')}
					onSelect={({end, start}: MomentDateRange) => {
						setCustomDateRange({
							end,
							start
						});
					}}
				/>
			) : (
				<ClayDropDown.ItemList>
					{filterItems().map((item: Item, index: number) => {
						const {description, label, value} = item;

						const activeClass =
							selectedItem.value === value ? 'active' : '';

						return (
							<ClayDropDown.Item
								className={`c-pointer ${activeClass}`}
								key={index}
								onClick={() => handleValueChange(item)}
							>
								{label}

								<div className='font-size-sm-2x'>
									{description}
								</div>
							</ClayDropDown.Item>
						);
					})}

					{!legacy && (
						<>
							{!seeMore && (
								<ClayDropDown.Item
									className='c-pointer'
									key='SEE_MORE'
									onClick={() => setSeeMore(true)}
								>
									{Liferay.Language.get(
										'more-preset-periods'
									)}
								</ClayDropDown.Item>
							)}

							<ClayDropDown.Divider />

							<ClayDropDown.Item
								className={getCN('c-pointer', {
									active: selectedItem.value === 'CUSTOM'
								})}
								key='CUSTOM'
								onClick={() => {
									setActive(false);
									setShowDatePicker(true);
								}}
							>
								<b>{Liferay.Language.get('custom-range')}</b>
							</ClayDropDown.Item>
						</>
					)}
				</ClayDropDown.ItemList>
			)}
		</ClayDropDown>
	);
};

export default DropdownRangeKey;
