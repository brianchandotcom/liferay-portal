/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';

import DropDownWithDrillDown from './Filter/components/DropDownWithDrillDown';
import SearchBar from './Search/SearchBar';

interface FilterItem {
	component: JSX.Element;
	disabled?: boolean;
	name: string;
}

interface IProps {
	children?: JSX.Element | JSX.Element[];
	filters: FilterItem[];
	onSearchSubmit: (term: string) => void;
}

const TableHeader = ({children, filters, onSearchSubmit}: IProps) => {
	const getMenus = () =>
		filters.reduce<
			React.ComponentProps<typeof DropDownWithDrillDown>['menus']
		>(
			(previousValue, currentValue, index) => ({
				...previousValue,
				x0a0: [
					...(previousValue.x0a0 || []),
					{
						child: `x0a${index + 1}`,
						disabled: currentValue.disabled,
						title: currentValue.name,
					},
				],
				[`x0a${index + 1}`]: [
					{
						child: currentValue.component,
						type: 'component',
					},
				],
			}),
			{}
		);

	return (
		<div className="bg-neutral-1 d-md-flex justify-content-between p-3 rounded">
			<div className="d-flex pb-2">
				<SearchBar onSearchSubmit={onSearchSubmit} />

				<DropDownWithDrillDown
					className="align-items-center"
					initialActiveMenu="x0a0"
					menus={getMenus()}
					trigger={
						<ClayButton
							borderless
							className="btn-secondary px- py-2"
						>
							<span className="inline-item inline-item-before">
								<ClayIcon symbol="filter" />
							</span>
							Filter
						</ClayButton>
					}
				/>
			</div>

			{children}
		</div>
	);
};
export default TableHeader;
