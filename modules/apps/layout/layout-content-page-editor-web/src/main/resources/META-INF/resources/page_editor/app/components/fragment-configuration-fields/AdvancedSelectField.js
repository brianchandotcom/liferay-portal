/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {LengthField} from '../../../common/components/LengthField';
import useControlledState from '../../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {useActiveItemId} from '../../contexts/ControlsContext';
import {useGlobalContext} from '../../contexts/GlobalContext';
import {useSelector} from '../../contexts/StoreContext';
import selectCanDetachTokenValues from '../../selectors/selectCanDetachTokenValues';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import isNullOrUndefined from '../../utils/isNullOrUndefined';
import {useId} from '../../utils/useId';

export function AdvancedSelectField({
	disabled,
	field,
	onValueSelect,
	options,
	tokenValues,
	value,
}) {
	const canDetachTokenValues = useSelector(selectCanDetachTokenValues);
	const helpTextId = useId();
	const triggerId = useId();

	const [active, setActive] = useState(false);
	const [isTokenValueOrInherited, setIsTokenValueOrInherited] = useState(
		!isNullOrUndefined(tokenValues[value]) || !value
	);
	const [nextValue, setNextValue] = useControlledState(value);

	const handleSelectChange = (event) => {
		const nextValue =
			event.target.options[event.target.selectedIndex].value;

		setNextValue(nextValue);
		onValueSelect(field.name, nextValue);
	};

	return (
		<div
			className={classNames('page-editor__select-field', {
				'has-value': value,
			})}
		>
			{isTokenValueOrInherited ? (
				<SingleSelectWithIcon
					disabled={disabled}
					field={field}
					helpTextId={helpTextId}
					onChange={handleSelectChange}
					options={options}
					value={nextValue}
				/>
			) : field.typeOptions?.showLengthField ? (
				<LengthField
					field={field}
					onValueSelect={onValueSelect}
					value={nextValue}
				/>
			) : (
				<InputWithIcon
					field={field}
					onBlur={(event) => {
						if (!event.target.value) {
							return;
						}

						onValueSelect(field.name, event.target.value);
					}}
					onChange={(event) => {
						setNextValue(event.target.value);
					}}
					value={nextValue}
				/>
			)}

			{value ? (
				isTokenValueOrInherited && canDetachTokenValues ? (
					<ClayButtonWithIcon
						className="border-0 mb-0 ml-1"
						displayType="secondary"
						onClick={() => {
							setIsTokenValueOrInherited(false);
							setNextValue(tokenValues[value].value);
							onValueSelect(field.name, tokenValues[value].value);
						}}
						small
						symbol="chain-broken"
						title={Liferay.Language.get('detach-token')}
					/>
				) : (
					<ClayDropDown
						active={active}
						alignmentPosition={Align.BottomRight}
						menuElementAttrs={{
							containerProps: {
								className: 'cadmin',
							},
						}}
						onActiveChange={setActive}
						trigger={
							<ClayButtonWithIcon
								className="border-0 ml-1"
								displayType="secondary"
								id={triggerId}
								small
								symbol="theme"
								title={Liferay.Language.get(
									'value-from-stylebook'
								)}
							/>
						}
					>
						<ClayDropDown.ItemList aria-labelledby={triggerId}>
							{options.map(({label, value}) => {
								if (!value) {
									return;
								}

								return (
									<ClayDropDown.Item
										key={value}
										onClick={() => {
											setActive(false);
											setIsTokenValueOrInherited(true);
											setNextValue(value);
											onValueSelect(field.name, value);
										}}
									>
										{label}
									</ClayDropDown.Item>
								);
							})}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				)
			) : null}

			{field.description ? (
				<div className="mt-1 small text-secondary" id={helpTextId}>
					{field.description}
				</div>
			) : null}
		</div>
	);
}

const SingleSelectWithIcon = ({
	disabled,
	field,
	helpTextId,
	onChange,
	options,
	value,
}) => {
	const activeItemId = useActiveItemId();
	const globalContext = useGlobalContext();
	const inputId = useId();

	const [defaultOptionComputedValue, setDefaultComputedValue] = useState('');

	const defaultOptionLabel = useMemo(
		() =>
			options.find((option) => option.value === field.defaultValue).label,
		[field.defaultValue, options]
	);

	const selectedOptionLabel = useMemo(() => {
		if (value === field.defaultValue) {
			if (defaultOptionComputedValue) {
				return `${defaultOptionLabel} · ${defaultOptionComputedValue}`;
			}

			return defaultOptionLabel;
		}

		return (
			options.find((option) => option.value === value)?.label ||
			defaultOptionLabel
		);
	}, [
		defaultOptionComputedValue,
		defaultOptionLabel,
		field.defaultValue,
		options,
		value,
	]);

	useEffect(() => {
		if (!field.cssProperty) {
			return;
		}

		const element = globalContext.document.querySelector(
			`.${getLayoutDataItemUniqueClassName(activeItemId)}`
		);

		if (!element) {
			return;
		}

		setDefaultComputedValue(
			globalContext.window
				.getComputedStyle(element)
				.getPropertyValue(field.cssProperty)
		);
	}, [activeItemId, field.cssProperty, globalContext]);

	return (
		<div className="btn btn-unstyled m-0 p-0 page-editor__single-select-with-icon">
			<label
				className="mb-0 page-editor__single-select-with-icon__label-icon px-1 py-2 text-center"
				htmlFor={inputId}
			>
				<ClayIcon
					className="lfr-portal-tooltip"
					data-title={field.label}
					symbol={field.icon}
				/>

				<span className="sr-only">{field.label}</span>
			</label>

			<ClaySelectWithOption
				aria-describedby={helpTextId}
				className="page-editor__single-select-with-icon__select"
				disabled={Boolean(disabled)}
				id={inputId}
				onChange={onChange}
				options={options}
				value={value}
			/>

			<div
				className={classNames(
					'page-editor__single-select-with-icon__label pl-2 pr-3 py-2 text-truncate w-100',
					{disabled}
				)}
				role="presentation"
			>
				<span>{selectedOptionLabel}</span>
			</div>
		</div>
	);
};

const InputWithIcon = ({field, onBlur, onChange, value}) => {
	const inputId = useId();

	return (
		<ClayInput.Group>
			<ClayInput.GroupItem>
				<ClayInput
					aria-label={field.label}
					id={inputId}
					insetBefore={Boolean(field.icon)}
					onBlur={onBlur}
					onChange={onChange}
					sizing="sm"
					value={value}
				/>

				{field.icon ? (
					<ClayInput.GroupInsetItem before>
						<label
							className="mb-0 page-editor__input-with-icon__label-icon pl-1 pr-3 text-center"
							htmlFor={inputId}
						>
							<ClayIcon
								className="lfr-portal-tooltip"
								data-title={field.label}
								symbol={field.icon}
							/>

							<span className="sr-only">{field.label}</span>
						</label>
					</ClayInput.GroupInsetItem>
				) : null}
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

AdvancedSelectField.propTypes = {
	disabled: PropTypes.bool,
	field: PropTypes.shape({
		...ConfigurationFieldPropTypes,
		typeOptions: PropTypes.shape({
			validValues: PropTypes.arrayOf(
				PropTypes.shape({
					label: PropTypes.string.isRequired,
					value: PropTypes.string.isRequired,
				})
			).isRequired,
		}),
		validValues: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.string.isRequired,
				value: PropTypes.string.isRequired,
			})
		),
	}),
	onValueSelect: PropTypes.func.isRequired,
	options: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string.isRequired,
			value: PropTypes.string.isRequired,
		})
	),
	value: PropTypes.oneOfType([
		PropTypes.number,
		PropTypes.string,
		PropTypes.arrayOf(PropTypes.string),
	]),
};
