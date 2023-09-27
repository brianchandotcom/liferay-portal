/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {
	LengthInput,
	isNullOrUndefined,
	isValidStyleValue,
	useControlledState,
} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {getResetLabelByViewport} from '../../../app/utils/getResetLabelByViewport';
import {ConfigurationFieldPropTypes} from '../../../prop_types/index';
import {useActiveItemId} from '../../contexts/ControlsContext';
import {useGlobalContext} from '../../contexts/GlobalContext';
import {useSelector} from '../../contexts/StoreContext';
import selectCanDetachTokenValues from '../../selectors/selectCanDetachTokenValues';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import getPreviousResponsiveStyle from '../../utils/getPreviousResponsiveStyle';

export function AdvancedSelectField({
	disabled,
	field,
	item,
	onValueSelect,
	options,
	tokenValues,
	value,
}) {
	const activeItemId = useActiveItemId();
	const globalContext = useGlobalContext();
	const helpTextId = useId();
	const triggerId = useId();

	const [active, setActive] = useState(false);
	const [defaultOptionComputedValue, setDefaultComputedValue] = useState('');
	const [error, setError] = useState(false);
	const [isTokenValueOrInherited, setIsTokenValueOrInherited] = useState(
		!isNullOrUndefined(tokenValues[value]) || !value
	);
	const [nextValue, setNextValue] = useControlledState(value);

	const canDetachTokenValues = useSelector(selectCanDetachTokenValues);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const handleSelectChange = (event) => {
		const nextValue =
			event.target.options[event.target.selectedIndex].value;

		setNextValue(nextValue);
		onValueSelect(field.name, nextValue);
	};

	const handleInputBlur = (event) => {
		if (
			!event.target.value ||
			!isValidStyleValue(field.cssProperty, event.target.value)
		) {
			setNextValue(value);
			setError(true);

			setTimeout(() => setError(false), 1000);

			return;
		}

		onValueSelect(field.name, event.target.value);
	};

	const handleInputKeyDown = (event) => {
		if (event.key === 'Enter') {
			handleInputBlur(event);
		}
	};

	const onSetValue = ({isTokenValue, value}) => {
		if (value === null) {
			const previousViewportValue = getPreviousResponsiveStyle(
				field.name,
				item.config,
				selectedViewportSize
			);

			if (previousViewportValue === nextValue) {
				return;
			}

			setNextValue(previousViewportValue);
		}
		else {
			setNextValue(value);
		}

		setIsTokenValueOrInherited(isTokenValue);
		onValueSelect(field.name, value);
	};

	useEffect(() => {
		setIsTokenValueOrInherited(
			!isNullOrUndefined(tokenValues[value]) ||
				(!value && field.inherited)
		);
	}, [selectedViewportSize, tokenValues, value, field.inherited]);

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
	}, [activeItemId, field.cssProperty, globalContext, value]);

	const resetButtonLabel = useMemo(
		() => getResetLabelByViewport(selectedViewportSize),
		[selectedViewportSize]
	);

	return (
		<div
			className={classNames('page-editor__select-field d-flex', {
				custom: !isTokenValueOrInherited,
			})}
		>
			{isTokenValueOrInherited ? (
				<SingleSelectWithIcon
					defaultOptionComputedValue={defaultOptionComputedValue}
					disabled={disabled}
					field={field}
					helpTextId={helpTextId}
					onChange={handleSelectChange}
					options={options}
					value={nextValue}
				/>
			) : field.typeOptions?.showLengthField ? (
				<LengthInput
					className="mb-0"
					field={field}
					onValueSelect={onValueSelect}
					showLabel={!field.icon}
					value={nextValue}
				/>
			) : (
				<InputWithIcon
					field={field}
					onBlur={handleInputBlur}
					onChange={(event) => {
						setNextValue(event.target.value);
					}}
					onKeyDown={handleInputKeyDown}
					value={nextValue}
				/>
			)}

			{canDetachTokenValues ? (
				isTokenValueOrInherited ? (
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('detach-style')}
						className="border-0 flex-shrink-0 mb-0 ml-2 page-editor__select-field__action-button"
						displayType="secondary"
						onClick={() => {
							onSetValue({
								isTokenValue: false,
								value: tokenValues[value]
									? tokenValues[value].value
									: defaultOptionComputedValue,
							});
						}}
						size="sm"
						symbol="chain-broken"
						title={Liferay.Language.get('detach-style')}
					/>
				) : (
					<ClayDropDown
						active={active}
						alignmentPosition={Align.BottomRight}
						className="flex-shrink-0 ml-2"
						menuElementAttrs={{
							containerProps: {
								className: 'cadmin',
							},
						}}
						onActiveChange={setActive}
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'value-from-stylebook'
								)}
								className="border-0"
								displayType="secondary"
								id={triggerId}
								size="sm"
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
											onSetValue({
												isTokenValue: true,
												value,
											});
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

			{value && value !== field.defaultValue ? (
				<ClayButtonWithIcon
					aria-label={resetButtonLabel}
					className="border-0 flex-shrink-0 mb-0 ml-2 page-editor__select-field__action-button"
					displayType="secondary"
					onClick={() =>
						onSetValue({isTokenValue: true, value: null})
					}
					size="sm"
					symbol="restore"
					title={resetButtonLabel}
				/>
			) : null}

			{field.description ? (
				<p className="m-0 mt-1 small text-secondary" id={helpTextId}>
					{field.description}
				</p>
			) : null}

			{error ? (
				<span aria-live="assertive" className="sr-only">
					{Liferay.Language.get(
						'this-field-requires-a-valid-style-value'
					)}
				</span>
			) : null}
		</div>
	);
}

const SingleSelectWithIcon = ({
	defaultOptionComputedValue,
	disabled,
	field,
	helpTextId,
	onChange,
	options,
	value,
}) => {
	const inputId = useId();

	const selectedOptionLabel = useMemo(() => {
		if (value === field.defaultValue) {
			return defaultOptionComputedValue;
		}

		return (
			options.find((option) => option.value === value)?.label ||
			defaultOptionComputedValue
		);
	}, [defaultOptionComputedValue, field.defaultValue, options, value]);

	return (
		<div className="btn btn-unstyled flex-grow-1 m-0 p-0 page-editor__single-select-with-icon">
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
				aria-describedby={field.description ? helpTextId : null}
				className="page-editor__single-select-with-icon__select"
				disabled={Boolean(disabled)}
				id={inputId}
				onChange={onChange}
				options={options}
				value={value || ''}
			/>

			<div
				className={classNames(
					'page-editor__single-select-with-icon__label p-2 w-100 d-flex',
					{disabled}
				)}
				role="presentation"
			>
				<span className="text-truncate">{selectedOptionLabel}</span>

				{!value && field.inherited ? (
					<span
						className="inherited"
						title={Liferay.Language.get('inherited-value')}
					></span>
				) : null}
			</div>
		</div>
	);
};

const InputWithIcon = ({field, onBlur, onChange, onKeyDown, value}) => {
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
					onKeyDown={onKeyDown}
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
			),
		}),
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
