import Card from './Card';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import DatePicker from './date-picker';
import getCN from 'classnames';
import Input from './Input';
import moment from 'moment';
import Overlay from './Overlay';
import React, {useState} from 'react';
import {DEFAULT_DATE_FORMAT} from 'shared/util/date';
import {isNil, noop} from 'lodash';
import {sub} from 'shared/util/lang';

const convertToMoment = (value: string, format): moment.Moment => {
	const date = moment(value, format);

	return date.isValid() ? date : null;
};

export type DateRange = {
	end: string;
	start: string;
};

export type MomentDateRange = {
	end: moment.Moment;
	start: moment.Moment;
};

interface IDateInputProps {
	className?: string;
	displayFormat?: string;
	format?: string;
	id?: string;
	name?: string;
	onBlur?: (event?: FocusEvent) => void;
	onChange: (range: DateRange) => void;
	overlayAlignment?: string;
	usePortal?: boolean;
	value: DateRange;
}

const DateInput: React.FC<IDateInputProps> = ({
	className,
	displayFormat,
	format = DEFAULT_DATE_FORMAT,
	onBlur = noop,
	onChange = noop,
	overlayAlignment = 'bottomLeft',
	usePortal = true,
	value
}) => {
	const [active, setActive] = useState(false);

	const convertMomentToDisplayFormat = (value: moment.Moment): string =>
		isNil(value) ? null : value.format(displayFormat || format);

	const handleClick = () => setActive(!active);

	const handleDateSelect = ({end, start}: MomentDateRange) => {
		onChange({
			end: convertMomentToDisplayFormat(end),
			start: convertMomentToDisplayFormat(start)
		});
	};

	const getDateRangeDisplay = ({end, start}: MomentDateRange): string => {
		if (end || start) {
			return sub(Liferay.Language.get('x-to-x'), [
				convertMomentToDisplayFormat(start),
				convertMomentToDisplayFormat(end)
			]) as string;
		}

		return '';
	};

	const momentDateRange = {
		end: convertToMoment(value.end, format),
		start: convertToMoment(value.start, format)
	};

	return (
		<Overlay
			active={active}
			alignment={overlayAlignment}
			className={getCN('date-range-input-root', className)}
			containerClass='date-range-input-root'
			forceAlignment={false}
			onOutsideClick={event => {
				if (onBlur && active) {
					onBlur(event);
				}

				setActive(false);
			}}
			usePortal={usePortal}
		>
			<Input.Group>
				<Input.GroupItem>
					<Input
						autoComplete='off'
						data-testid='date-range-input'
						inset='after'
						onClick={handleClick}
						placeholder={sub(Liferay.Language.get('x-to-x'), [
							Liferay.Language.get('yyyy-mm-dd'),
							Liferay.Language.get('yyyy-mm-dd')
						])}
						readOnly
						value={getDateRangeDisplay(momentDateRange)}
					/>

					<Input.Inset position='after'>
						<ClayButton
							aria-label={Liferay.Language.get(
								'choose-date-range'
							)}
							className='button-root'
							displayType='unstyled'
							onClick={handleClick}
						>
							<ClayIcon className='icon-root' symbol='calendar' />
						</ClayButton>
					</Input.Inset>
				</Input.GroupItem>
			</Input.Group>

			<Card>
				<Card.Body>
					<DatePicker
						date={momentDateRange}
						minDate={moment().subtract(100, 'years')}
						onSelect={handleDateSelect}
					/>
				</Card.Body>
			</Card>
		</Overlay>
	);
};

export default DateInput;
