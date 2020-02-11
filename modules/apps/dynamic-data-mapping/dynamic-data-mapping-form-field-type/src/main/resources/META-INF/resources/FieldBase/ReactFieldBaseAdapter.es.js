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
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {getRepeatedIndex} from 'dynamic-data-mapping-form-renderer';
import React, {useMemo} from 'react';

import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import template from './FieldBaseAdapter.soy';

function FieldBase({
	children,
	displayErrors,
	errorMessage,
	id,
	label,
	onRemoveButton,
	onRepeatButton,
	readOnly,
	repeatable,
	required,
	showLabel = true,
	spritemap,
	tip,
	tooltip,
	valid,
	visible
}) {
	const repeatedIndex = useMemo(() => getRepeatedIndex(name), []);

	return (
		<ClayTooltipProvider>
			<div
				className={classNames('form-group', {
					'has-error': displayErrors && errorMessage && !valid,
					hide: !visible
				})}
				data-field-name={name}
			>
				{repeatable && (
					<div className="lfr-ddm-form-field-repeatable-toolbar">
						{repeatable && repeatedIndex > 0 && (
							<ClayButtonWithIcon
								className="ddm-form-field-repeatable-delete-button p-0"
								disabled={readOnly}
								onClick={onRemoveButton}
								small
								spritemap={spritemap}
								symbol="trash"
								type="button"
							/>
						)}

						<ClayButtonWithIcon
							className="ddm-form-field-repeatable-add-button p-0"
							disabled={readOnly}
							onClick={onRepeatButton}
							small
							spritemap={spritemap}
							symbol="plus"
							type="button"
						/>
					</div>
				)}

				{((label && showLabel) ||
					required ||
					tooltip ||
					repeatable) && (
					<label
						className={classNames({
							'ddm-empty': !showLabel && !required
						})}
						htmlFor={id}
					>
						{label && showLabel && label}

						{required && spritemap && (
							<span className="reference-mark">
								<ClayIcon
									spritemap={spritemap}
									symbol="asterisk"
								/>
							</span>
						)}

						{tooltip && (
							<div className="ddm-tooltip">
								<ClayIcon
									data-tooltip-align="right"
									spritemap={spritemap}
									symbol="question-circle-full"
									title={tooltip}
								/>
							</div>
						)}
					</label>
				)}

				{children}

				{tip && <span className="form-text">{tip}</span>}

				{displayErrors && errorMessage && !valid && (
					<span className="form-feedback-group">
						<div className="form-feedback-item">{errorMessage}</div>
					</span>
				)}
			</div>
		</ClayTooltipProvider>
	);
}

/**
 * This Proxy connects to the store to send the changes directly to the store. This
 * should be replaced when we have a communication with a Store/Provider in React.
 */
const FieldBaseProxy = ({dispatch, name, ...otherProps}) => (
	<FieldBase
		{...otherProps}
		name={name}
		onRemoveButton={() => dispatch('fieldRemoved', name)}
		onRepeatButton={() => dispatch('fieldRepeated', name)}
	/>
);

/**
 * This Adapter must be removed when all fields are written in React, this brings
 * compatibility so that it can continue working with the fields in Metal+Soy and
 * that it works with the fields in React.
 */
const ReactFieldBaseAdapter = getConnectedReactComponentAdapter(
	connectStore(FieldBaseProxy),
	template
);

export {FieldBase, FieldBaseProxy};
export default ReactFieldBaseAdapter;
