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

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import React, {ReactNode} from 'react';

import {FieldFeedback} from './FieldFeedback';

import './FieldBase.scss';

function RequiredMask() {
	return (
		<>
			<span className="ml-1 reference-mark text-warning">
				<ClayIcon symbol="asterisk" />
			</span>

			<span className="hide-accessible sr-only">
				{Liferay.Language.get('mandatory')}
			</span>
		</>
	);
}

export function FieldBase({
	children,
	className,
	disabled,
	errorMessage,
	helpMessage,
	id,
	label,
	required,
	tooltip,
	warningMessage,
}: IProps) {
	return (
		<ClayForm.Group
			className={classNames(className, {
				'has-error': errorMessage,
				'has-warning': warningMessage && !errorMessage,
			})}
		>
			{label && (
				<label className={classNames({disabled})} htmlFor={id}>
					{label}

					{required && <RequiredMask />}
				</label>
			)}

			{tooltip && (
				<>
					&nbsp;
					<ClayTooltipProvider>
						<span data-tooltip-align="top" title={tooltip}>
							<ClayIcon
								className="lfr-objects__field-base-tooltip-icon"
								symbol="question-circle-full"
							/>
						</span>
					</ClayTooltipProvider>
				</>
			)}

			{children}

			<FieldFeedback
				errorMessage={errorMessage}
				helpMessage={helpMessage}
				warningMessage={warningMessage}
			/>
		</ClayForm.Group>
	);
}

interface IProps {
	children: ReactNode;
	className?: string;
	disabled?: boolean;
	errorMessage?: string;
	helpMessage?: string;
	id?: string;
	label?: string;
	required?: boolean;
	tooltip?: string;
	warningMessage?: string;
}
