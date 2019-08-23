import {
	addClickOutsideListener,
	removeClickOutsideListener,
	handleClickOutside
} from '../../../../shared/components/filter/util/filterEvents';
import React, {useEffect, useRef} from 'react';
import Icon from '../../../../shared/components/Icon';
import MaskedInput from 'react-text-mask';
import moment from '../../../../shared/util/moment';
import {sub} from '../../../../shared/util/lang';
import {TimeRangeContext} from '../store/TimeRangeStore';
import {useContext} from 'react';
import {useCustomTimeRange} from '../store/CustomTimeRangeStore';

const CustomTimeRangeForm = ({filterKey}) => {
	const {
		applyCustomFilter,
		dateEnd,
		dateStart,
		errors = {},
		setDateEnd,
		setDateStart,
		validate
	} = useCustomTimeRange(filterKey);
	const {setShowCustomForm} = useContext(TimeRangeContext);
	const wrapperRef = useRef();

	const dateFormat = moment.localeData().longDateFormat('L');

	const onBlur = ({target: {name, value}}) => {
		validate(name, value);
	};

	const onCancel = () => {
		setShowCustomForm(false);
	};

	const onChange = setter => ({target: {value}}) => {
		setter(value);
	};

	const onApply = () => {
		const errors = validate();

		if (!errors) {
			applyCustomFilter();
			setShowCustomForm(false);
		}
	};

	const dateMask = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];

	useEffect(() => {
		const onClickOutside = handleClickOutside(() => {
			setShowCustomForm(false);
		}, wrapperRef.current);

		addClickOutsideListener(onClickOutside);

		return () => removeClickOutsideListener(onClickOutside);
	}, []);

	return (
		<div className="custom-range-wrapper" ref={wrapperRef}>
			<form className="custom-range-form">
				<h4 className="mb-2">{Liferay.Language.get('custom-range')}</h4>

				<span className="form-text mb-3 text-semi-bold">
					{sub(Liferay.Language.get('default-date-format-is-x'), [
						dateFormat
					])}
				</span>

				<div className="form-group-autofit">
					<FormGroupItem error={errors['dateStart']}>
						<label htmlFor="dateStart">
							{Liferay.Language.get('from')}
						</label>

						<MaskedInput
							className="form-control"
							defaultValue={dateStart}
							mask={dateMask}
							name="dateStart"
							onBlur={onBlur}
							onChange={onChange(setDateStart)}
							placeholder={dateFormat}
						/>
					</FormGroupItem>

					<FormGroupItem error={errors['dateEnd']}>
						<label htmlFor="dateEnd">
							{Liferay.Language.get('to')}
						</label>

						<MaskedInput
							className="form-control"
							defaultValue={dateEnd}
							mask={dateMask}
							name="dateEnd"
							onBlur={onBlur}
							onChange={onChange(setDateEnd)}
							placeholder={dateFormat}
						/>
					</FormGroupItem>
				</div>
			</form>

			<div className="dropdown-divider" />

			<div className="custom-range-footer">
				<button className="btn btn-secondary" onMouseDown={onCancel}>
					{Liferay.Language.get('cancel')}
				</button>

				<button className="btn btn-primary ml-3" onClick={onApply}>
					{Liferay.Language.get('apply')}
				</button>
			</div>
		</div>
	);
};

const FormGroupItem = ({children, error}) => (
	<div className={`form-group-item ${error ? 'has-error' : ''}`}>
		<div className="input-group">
			<div className="input-group-item">{children}</div>
		</div>

		{error && (
			<div className="form-feedback-group">
				<div className="form-feedback-item">
					<span className="form-feedback-indicator mr-2">
						<Icon iconName="exclamation-full" />
					</span>

					<span className="text-semi-bold">{error}</span>
				</div>
			</div>
		)}
	</div>
);

export {CustomTimeRangeForm};
