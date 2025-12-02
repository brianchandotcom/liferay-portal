/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import classNames from 'classnames';
import React from 'react';

export default function RuleField({
	children,
	className,
	error,
	errorLabel,
	fieldId,
}: {
	children: React.ReactNode;
	className?: string;
	error: boolean;
	errorLabel: string;
	fieldId: string;
}) {
	return (
		<ClayForm.Group className={classNames(className, {'has-error': error})}>
			{children}

			{error ? (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />

						{errorLabel}

						<span className="sr-only" id={`${fieldId}-error`}>
							{Liferay.Language.get('this-field-is-required')}
						</span>
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			) : null}
		</ClayForm.Group>
	);
}
