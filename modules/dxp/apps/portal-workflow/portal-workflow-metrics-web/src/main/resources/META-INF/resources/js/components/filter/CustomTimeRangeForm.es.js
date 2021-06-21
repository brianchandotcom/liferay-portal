/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useRef} from 'react';
import MaskedInput from 'react-text-mask';

import {
	addClickOutsideListener,
	handleClickOutside,
	removeClickOutsideListener,
} from '../../shared/components/filter/util/filterEvents.es';
import {getMaskByDateFormat} from '../../shared/util/date.es';
import {sub} from '../../shared/util/lang.es';
import {useCustomTimeRange} from './hooks/useCustomTimeRange.es';

export default function CustomTimeRangeForm({
	handleSelectFilter,
	items,
	prefixKey = '',
	setFormVisible,
	withoutRouteParams,
}) {
	const {
		applyCustomFilter,
		dateEnd,
		dateFormat,
		dateStart,
		errors = {},
		setDateEnd,
		setDateStart,
		validate,
	} = useCustomTimeRange(prefixKey, withoutRouteParams);
	const wrapperRef = useRef();

	const dateMask = getMaskByDateFormat(dateFormat);

	const activeCustomFilter = () => {
		items.forEach((item) => {
			item.active = item.key === 'custom';
		});
	};

	const onBlur = ({target: {name, value}}) => {
		validate(name, value);
	};

	const onCancel = () => {
		setFormVisible(false);
	};

	const onChange = (setter) => ({target: {value}}) => {
		setter(value);
	};

	const onApply = () => {
		const {dateEnd: dateEndError, dateStart: dateStartError} = errors || {};

		if (!dateEndError && !dateStartError) {
			activeCustomFilter();
			applyCustomFilter(handleSelectFilter);
			setFormVisible(false);
		}
	};

	useEffect(() => {
		const onClickOutside = handleClickOutside(() => {
			setFormVisible(false);
		}, wrapperRef.current);

		addClickOutsideListener(onClickOutside);

		return () => removeClickOutsideListener(onClickOutside);
	}, [setFormVisible]);

	return (
		<div className="custom-range-wrapper" ref={wrapperRef}>
			<ClayForm className="custom-range-form">
				<h4 className="mb-2">{Liferay.Language.get('custom-range')}</h4>

				<span className="form-text mb-3 text-semi-bold">
					{sub(Liferay.Language.get('default-date-format-is-x'), [
						dateFormat,
					])}
				</span>

				<ClayForm.Group className="form-group-autofit">
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
				</ClayForm.Group>
			</ClayForm>
			<div className="dropdown-divider" />
			<div className="custom-range-footer">
				<ClayButton displayType="secondary" onMouseDown={onCancel}>
					{Liferay.Language.get('cancel')}
				</ClayButton>

				<ClayButton
					className="ml-3"
					displayType="primary"
					onClick={onApply}
				>
					{Liferay.Language.get('apply')}
				</ClayButton>
			</div>
		</div>
	);
}

const FormGroupItem = ({children, error}) => (
	<div className={`form-group-item ${error ? 'has-error' : ''}`}>
		<ClayInput.Group>
			<ClayInput.GroupItem>{children}</ClayInput.GroupItem>
		</ClayInput.Group>

		{error && (
			<ClayForm.FeedbackGroup>
				<ClayForm.FeedbackItem>
					<span className="form-feedback-indicator mr-2">
						<ClayIcon symbol="exclamation-full" />
					</span>

					<span className="text-semi-bold">{error}</span>
				</ClayForm.FeedbackItem>
			</ClayForm.FeedbackGroup>
		)}
	</div>
);
