/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					className="float-left lfr-ddm-form-pagination-prev"
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
					className="float-left lfr-ddm-form-pagination-next"
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
					className="float-left"
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

			{showPartialResultsToRespondents && (
				<ClayButton
					className="float-right"
					displayType="secondary"
					onClick={() => onClick()}
				>
					{Liferay.Language.get('preview-existing-submissions')}
				</ClayButton>
			)}
		</div>
	);
}
