/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {ClayDropDownWithItems} from '@clayui/drop-down';
import React, {ComponentProps} from 'react';

type ActionDropdownItemProps = ComponentProps<
	typeof ClayDropDownWithItems
>['items'][number];

interface DesignLibraryBreadcrumbProps {
	actionItems?: ActionDropdownItemProps[];
	breadcrumbItems: {active: boolean; href?: string; label: string}[];
}

function ActionDropdownItem({
	href,
	symbolLeft,
	symbolRight,
	title,
}: ActionDropdownItemProps) {
	const props = {href, symbolLeft, symbolRight, title};

	return <ClayDropDown.Item {...props}>{title}</ClayDropDown.Item>;
}

export default function DesignLibraryBreadcrumb({
	actionItems,
	breadcrumbItems,
}: DesignLibraryBreadcrumbProps) {
	return (
		<div className="autofit-row autofit-row-center bg-white design-library-breadcrumb px-4 py-3">
			<ClayBreadcrumb className="px-0 py-1" items={breadcrumbItems} />

			{actionItems && (
				<div className="autofit-col ml-1">
					<ClayDropDown
						hasLeftSymbols={actionItems.some(
							({symbolLeft}) => !!symbolLeft
						)}
						hasRightSymbols={actionItems.some(
							({symbolRight}) => !!symbolRight
						)}
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'more-actions'
								)}
								className="component-action"
								displayType="unstyled"
								size="sm"
								symbol="ellipsis-v"
							/>
						}
					>
						<ClayDropDown.ItemList>
							{actionItems.map((item, i) => (
								<ActionDropdownItem key={i} {...item} />
							))}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</div>
			)}
		</div>
	);
}
