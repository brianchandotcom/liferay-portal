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

import classnames from 'classnames';
import React from 'react';

import {useEvaluate} from '../../../core/hooks/useEvaluate.es';
import {useForm} from '../../../core/hooks/useForm.es';
import {usePage} from '../../../core/hooks/usePage.es';
import {getFormId, getFormNode} from '../../../utils/formId.es';
import nextPage from '../thunks/nextPage.es';
import previousPage from '../thunks/previousPage.es';

export function MultiStep({activePage, editable, pages}) {
	const {containerElement} = usePage();

	const createPreviousPage = useEvaluate(previousPage);
	const createNextPage = useEvaluate(nextPage);

	const dispatch = useForm();

	return (
		<div className="ddm-form-pagination position-relative wizard-mode">
			<ol className="ddm-wizard multi-step-indicator-label-top multi-step-nav multi-step-nav-collapse-sm">
				{pages.map((page, index) => (
					<li
						className={classnames('multi-step-item', {
							'active': index === activePage,
							'complete': index < activePage,
							'multi-step-item-expand':
								index + 1 !== pages.length,
						})}
						key={index}
						onClick={() => {
							if (index < activePage) {
								dispatch(
									createPreviousPage({
										activePage,
										formId: getFormId(
											getFormNode(
												containerElement.current
											)
										),
									})
								);
							}
							else if (index > activePage) {
								dispatch(
									createNextPage({
										activePage,
										formId: getFormId(
											getFormNode(
												containerElement.current
											)
										),
									})
								);
							}
						}}
					>
						<div className="multi-step-divider"></div>

						<div className="multi-step-indicator">
							<div className="multi-step-indicator-label">
								{page.paginationItemRenderer ===
								'wizard_success'
									? Liferay.Language.get('success-page')
									: page.title
									? page.title
									: Liferay.Language.get('untitled-page')}
							</div>

							{editable ? (
								<a
									className="multi-step-icon"
									data-multi-step-icon={index + 1}
									href="javascript:;"
								/>
							) : (
								<span
									className="multi-step-icon"
									data-multi-step-icon={index + 1}
								/>
							)}
						</div>
					</li>
				))}
			</ol>
		</div>
	);
}
