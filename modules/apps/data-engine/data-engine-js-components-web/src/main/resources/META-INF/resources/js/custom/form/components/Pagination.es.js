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

export const Pagination = ({activePage, pages}) => {
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
};
