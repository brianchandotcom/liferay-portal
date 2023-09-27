/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import * as DefaultVariant from '../../../core/components/PageRenderer/DefaultVariant.es';
import {useConfig} from '../../../core/hooks/useConfig.es';
import {Pagination} from '../components/Pagination.es';
import {PaginationControls} from '../components/PaginationControls.es';

export function Container({
	activePage,
	children,
	pageIndex,
	pages,
	readOnly,
	strings,
}) {
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
					{!!pages.length && (
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
							className="float-right"
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
}

Container.displayName = 'PaginatedVariant.Container';
