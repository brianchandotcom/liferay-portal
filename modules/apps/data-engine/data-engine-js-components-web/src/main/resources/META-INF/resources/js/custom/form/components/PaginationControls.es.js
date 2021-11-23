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

import ClayButton from '@clayui/button';
import React from 'react';

import {useConfig} from '../../../core/hooks/useConfig.es';
import {useEvaluate} from '../../../core/hooks/useEvaluate.es';
import {useForm} from '../../../core/hooks/useForm.es';
import {usePage} from '../../../core/hooks/usePage.es';
import {getFormId, getFormNode} from '../../../utils/formId.es';
import nextPage from '../thunks/nextPage.es';
import previousPage from '../thunks/previousPage.es';

export function PaginationControls({
	activePage,
	onClick,
	readOnly,
	strings = null,
	total,
}) {
	const {
		cancelLabel,
		ffShowPartialResultsEnabled,
		redirectURL,
		showCancelButton,
		showPartialResultsToRespondents,
		showSubmitButton,
		submitLabel,
	} = useConfig();
	const {containerElement} = usePage();

	const createPreviousPage = useEvaluate(previousPage);
	const createNextPage = useEvaluate(nextPage);

	const dispatch = useForm();

	return (
		<div className="lfr-ddm-form-pagination-controls">
			{activePage > 0 && (
				<ClayButton
					className={`${
						ffShowPartialResultsEnabled ? 'float-left' : ''
					} lfr-ddm-form-pagination-prev`}
					displayType="secondary"
					onClick={() =>
						dispatch(
							createPreviousPage({
								activePage,
								formId: getFormId(
									getFormNode(containerElement.current)
								),
							})
						)
					}
					type="button"
				>
					{strings !== null
						? strings['previous']
						: Liferay.Language.get('previous')}
				</ClayButton>
			)}

			{activePage < total - 1 && (
				<ClayButton
					className={`${
						ffShowPartialResultsEnabled
							? 'float-left'
							: 'float-right'
					}  lfr-ddm-form-pagination-next`}
					displayType="primary"
					onClick={() =>
						dispatch(
							createNextPage({
								activePage,
								formId: getFormId(
									getFormNode(containerElement.current)
								),
							})
						)
					}
					type="button"
				>
					{strings !== null
						? strings['next']
						: Liferay.Language.get('next')}
				</ClayButton>
			)}

			{activePage === total - 1 && !readOnly && showSubmitButton && (
				<ClayButton
					className={
						ffShowPartialResultsEnabled
							? 'float-left'
							: 'float-right'
					}
					id="ddm-form-submit"
					type="submit"
				>
					{submitLabel}
				</ClayButton>
			)}

			{showCancelButton && !readOnly && (
				<div className="ddm-btn-cancel float-right">
					<a
						className="btn btn-cancel btn-secondary"
						href={redirectURL}
					>
						{cancelLabel}
					</a>
				</div>
			)}

			{ffShowPartialResultsEnabled && showPartialResultsToRespondents && (
				<ClayButton
					className="float-right"
					displayType="secondary"
					onClick={() => onClick()}
				>
					{Liferay.Language.get('see-partial-results')}
				</ClayButton>
			)}
		</div>
	);
}
