/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPagination from '@clayui/pagination';
import classnames from 'classnames';
import React from 'react';

import {EVENT_TYPES as CORE_EVENT_TYPES} from '../../../core/actions/eventTypes.es';
import {useEvaluate} from '../../../core/hooks/useEvaluate.es';
import {useForm} from '../../../core/hooks/useForm.es';
import {usePage} from '../../../core/hooks/usePage.es';
import {getFormId, getFormNode} from '../../../utils/formId.es';
import nextPage from '../thunks/nextPage.es';
import previousPage from '../thunks/previousPage.es';

export function Pagination({activePage, pages}) {
	const {containerElement} = usePage();
	const dispatch = useForm();

	const createPreviousPage = useEvaluate(previousPage);
	const createNextPage = useEvaluate(nextPage);

	return (
		<ClayPagination className="ddm-pagination justify-content-center">
			<li
				className={classnames('page-item', {
					'visibility-hidden': activePage === 0,
				})}
			>
				<button
					className="page-link"
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
					«
					<span className="sr-only">
						{Liferay.Language.get('previous')}
					</span>
				</button>
			</li>

			{pages.map((page, index) => (
				<ClayPagination.Item
					active={activePage === index}
					disabled={!page.enabled}
					key={index}
					onClick={() =>
						dispatch({
							payload: {activePage: index},
							type: CORE_EVENT_TYPES.PAGE.CHANGE,
						})
					}
				>
					{page.paginationItemRenderer === 'paginated_success'
						? Liferay.Language.get('success-page')
						: index + 1}
				</ClayPagination.Item>
			))}

			<li
				className={classnames('page-item', {
					'visibility-hidden':
						activePage === pages.length - 1 || activePage === -1,
				})}
			>
				<button
					className="page-link"
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
					»
					<span className="sr-only">
						{Liferay.Language.get('next')}
					</span>
				</button>
			</li>
		</ClayPagination>
	);
}
