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

import {Skeleton} from '../../../../common/components';
import {MENU_TYPES} from '../../utils/constants';

const SideMenuSkeleton = () => {
	return (
		<div className="bg-neutral-1 cp-side-menu mr-4 pl-4 pt-4">
			<ul className="list-unstyled mr-2">
				{Object.entries(MENU_TYPES).map((menuType) => {
					const [menuKey, menuName] = menuType;

					return (
						<li key={menuKey}>
							<Skeleton
								className="mb-1"
								height={36}
								width={
									menuName !== MENU_TYPES.productActivation
										? 120
										: 200
								}
							/>
						</li>
					);
				})}
			</ul>
		</div>
	);
};

export default SideMenuSkeleton;
