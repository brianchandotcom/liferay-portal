import Card from './Card';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import DatePicker from './date-picker';
import getCN from 'classnames';
import Input from './Input';
import MaskedInput from './MaskedInput';
import moment from 'moment';
import Overlay from './Overlay';
import React, {MouseEventHandler, useState} from 'react';
import {
	applyTimeZone,
	DATE_MASK,
	DATE_TIME_MASK,
	DEFAULT_DATE_FORMAT
} from 'shared/util/date';
import {noop} from 'lodash';

interface IDateInputProps extends React.HTMLAttributes<HTMLInputElement> {
	displayFormat?: string;
	format?: string;
	name?: string;
	onDateInputBlur?: (param: any) => void;
	onDateInputChange?: (param: any) => void;
	overlayAlignment?: OverlayAlignment;
	readOnly?: boolean;
	showTimeSelector?: boolean;
	timeZoneId?: string;
	usePortal?: boolean;
	value: any;
}

export type OverlayAlignment = 'bottomLeft' | 'rightCenter';

const DateInput: React.FC<IDateInputProps> = ({
	className,
	displayFormat,
	format = DEFAULT_DATE_FORMAT,
	id,
	name,
	onDateInputBlur = noop,
	onDateInputChange = noop,
	overlayAlignment = 'bottomLeft',
	readOnly = false,
	showTimeSelector = false,
	timeZoneId,
	usePortal = true,
	value
}) => {
	const [active, setActive] = useState(false);

	const handleClick: MouseEventHandler<HTMLElement> = event => {
		event.preventDefault();

		setActive(!active);
	};

	const handleDateSelect = (value: any): void => {
		onDateInputChange(value.format(format));
	};

	const handleChange = ({target}: React.BaseSyntheticEvent<Event>): void => {
		const {value} = target;

		if (moment(value, format, true).isValid()) {
			onDateInputChange(value);
		}
	};

	const handleOutsideClick = (event: PointerEvent): void => {
		if (onDateInputBlur && active) {
			onDateInputBlur(event);
		}

		setActive(false);
	};

	const getDateValue = (): string => {
		if (!displayFormat) {
			return value;
		}

		let date = value;

		if (showTimeSelector) {
			date = applyTimeZone(value, timeZoneId);
		} else {
			date = moment(value);
		}

		return date.format(displayFormat);
	};

	const date = showTimeSelector
		? applyTimeZone(value, timeZoneId)
		: moment(value, format);

	return (
		<Overlay
			active={active}
			alignment={overlayAlignment}
			className={getCN('date-input-root', className)}
			forceAlignment={false}
			onOutsideClick={handleOutsideClick}
			usePortal={usePortal}
		>
			<Input.Group>
				<Input.GroupItem>
					<MaskedInput
						autoComplete='off'
						data-testid='date-input'
						id={id}
						inset='after'
						keepCharPositions
						mask={showTimeSelector ? DATE_TIME_MASK : DATE_MASK}
						name={name}
						onChange={handleChange}
						onClick={handleClick}
						placeholder={
							showTimeSelector
								? Liferay.Language.get('yyyy-mm-dd-hh-mm-zz')
								: Liferay.Language.get('yyyy-mm-dd')
						}
						readOnly={readOnly}
						showMask
						value={getDateValue()}
					/>

					<Input.Inset position='after'>
						<ClayButton
							aria-label={Liferay.Language.get('choose-a-date')}
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
						date={date.isValid() ? date : null}
						minDate={moment().subtract(100, 'years')}
						onSelect={handleDateSelect}
						showTimeSelector={showTimeSelector}
						timeZoneId={timeZoneId}
					/>
				</Card.Body>
			</Card>
		</Overlay>
	);
};

export default DateInput;
