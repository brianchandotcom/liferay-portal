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

import * as DefaultVariant from '../../../core/components/PageRenderer/DefaultVariant.es';
import {useConfig} from '../../../core/hooks/useConfig.es';
import {Pagination} from '../components/Pagination.es';
import {PaginationControls} from '../components/PaginationControls.es';

export const Container = ({
	activePage,
	children,
	pageIndex,
	pages,
	readOnly,
	strings,
}) => {
	const {showSubmitButton, submitLabel} = useConfig();

	return (
		<div className="ddm-form-page-container paginated">
			<DefaultVariant.Container
				activePage={activePage}
				pageIndex={pageIndex}
			>
				{children}
			</DefaultVariant.Container>

			{pageIndex === activePage && (
				<>
					{pages.length > 0 && (
						<>
							<Pagination activePage={activePage} pages={pages} />
							<PaginationControls
								activePage={activePage}
								readOnly={readOnly}
								showSubmitButton={showSubmitButton}
								strings={strings}
								submitLabel={submitLabel}
								total={pages.length}
							/>
						</>
					)}

					{!pages.length && showSubmitButton && (
						<ClayButton
							className="float-right lfr-ddm-form-submit"
							id="ddm-form-submit"
							type="submit"
						>
							{submitLabel}
						</ClayButton>
					)}
				</>
			)}
		</div>
	);
};

Container.displayName = 'PaginatedVariant.Container';
