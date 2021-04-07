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
import ClayIcon from '@clayui/icon';
import React from 'react';

import {useConfig} from '../../../core/hooks/useConfig.es';
import {useEvaluate} from '../../../core/hooks/useEvaluate.es';
import {useForm} from '../../../core/hooks/useForm.es';
import {usePage} from '../../../core/hooks/usePage.es';
import {getFormId, getFormNode} from '../../../utils/formId.es';
import nextPage from '../thunks/nextPage.es';
import previousPage from '../thunks/previousPage.es';

export const PaginationControls = ({
	activePage,
	readOnly,
	strings = null,
	total,
}) => {
	const {
		cancelLabel,
		redirectURL,
		showCancelButton,
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
					className="lfr-ddm-form-pagination-prev"
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
					<ClayIcon symbol="angle-left" />
					{strings !== null
						? strings['previous']
						: Liferay.Language.get('previous')}
				</ClayButton>
			)}

			{activePage < total - 1 && (
				<ClayButton
					className="float-right lfr-ddm-form-pagination-next"
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
					<ClayIcon symbol="angle-right" />
				</ClayButton>
			)}

			{activePage === total - 1 && !readOnly && showSubmitButton && (
				<ClayButton
					className="float-right lfr-ddm-form-submit"
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
		</div>
	);
};
