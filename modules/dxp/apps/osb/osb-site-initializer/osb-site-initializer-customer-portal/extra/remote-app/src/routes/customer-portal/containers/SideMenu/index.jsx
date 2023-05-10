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

import classNames from 'classnames';
import {useEffect, useMemo, useRef, useState} from 'react';
import i18n from '../../../../common/I18n';
import {Button} from '../../../../common/components';
import getKebabCase from '../../../../common/utils/getKebabCase';
import {useCustomerPortal} from '../../context';
import {MENU_TYPES, PRODUCT_TYPES} from '../../utils/constants';
import SideMenuSkeleton from './Skeleton';
import MenuItem from './components/MenuItem';

const ACTIVATION_PATH = 'activation';

const SideMenu = () => {
	const [{subscriptionGroups}] = useCustomerPortal();
	const [isOpenedProductsMenu, setIsOpenedProductsMenu] = useState(false);
	const [menuItemActiveStatus, setMenuItemActiveStatus] = useState([]);

	const productActivationMenuRef = useRef();

	const activationSubscriptionGroups = useMemo(
		() =>
			subscriptionGroups?.filter(
				(subscriptionGroup) => subscriptionGroup.hasActivation
			),
		[subscriptionGroups]
	);

	const hasSomeMenuItemActive = useMemo(
		() => menuItemActiveStatus.some((menuItemActive) => !!menuItemActive),
		[menuItemActiveStatus]
	);

	useEffect(() => {
		const expandedHeightProducts = isOpenedProductsMenu
			? activationSubscriptionGroups?.length * 48
			: 0;

		if (productActivationMenuRef?.current) {
			productActivationMenuRef.current.style.maxHeight = `${expandedHeightProducts}px`;
		}
	}, [
		activationSubscriptionGroups?.length,
		hasSomeMenuItemActive,
		isOpenedProductsMenu,
	]);

	const hasLiferayExperienceCloud = useMemo(
		() =>
			subscriptionGroups?.some(
				({name}) => name === PRODUCT_TYPES.liferayExperienceCloud
			),
		[subscriptionGroups]
	);

	const accountSubscriptionGroupsMenuItem = useMemo(
		() =>
			activationSubscriptionGroups?.map(({name}, index) => {
				if (name !== PRODUCT_TYPES.liferayExperienceCloud) {
					const redirectPage = getKebabCase(name);

					const menuUpdateStatus = (isActive) =>
						setMenuItemActiveStatus(
							(previousMenuItemActiveStatus) => {
								const menuItemStatus = [
									...previousMenuItemActiveStatus,
								];
								menuItemStatus[index] = isActive;

								setIsOpenedProductsMenu(
									menuItemStatus.some(Boolean)
								);

								return menuItemStatus;
							}
						);

					return (
						<MenuItem
							iconKey={redirectPage.split('-')[0]}
							key={`${name}-${index}`}
							setActive={menuUpdateStatus}
							to={`${ACTIVATION_PATH}/${redirectPage}`}
						>
							{name}
						</MenuItem>
					);
				}
			}),
		[activationSubscriptionGroups]
	);

	if (!activationSubscriptionGroups) {
		return <SideMenuSkeleton />;
	}

	return (
		<div className="bg-neutral-1 cp-side-menu pl-4 pt-4">
			<ul className="list-unstyled mr-2">
				<MenuItem to="">
					{i18n.translate(getKebabCase(MENU_TYPES.overview))}
				</MenuItem>

				{Liferay.FeatureFlags['LPS-153478'] &&
					hasLiferayExperienceCloud && (
						<MenuItem
							to={getKebabCase(
								PRODUCT_TYPES.liferayExperienceCloud
							)}
						>
							{MENU_TYPES.liferayExperienceCloud}
						</MenuItem>
					)}

				<li>
					<Button
						appendIcon={
							!!activationSubscriptionGroups.length &&
							'angle-right-small'
						}
						appendIconClassName="ml-auto"
						className={classNames(
							'align-items-center btn-borderless d-flex px-2 py-2 rounded w-100',
							{
								'cp-product-activation-active': isOpenedProductsMenu,
								'cp-products-list-active': hasSomeMenuItemActive,
								'text-neutral-4':
									activationSubscriptionGroups.length < 1,
								'text-neutral-10': !!activationSubscriptionGroups.length,
							}
						)}
						disabled={activationSubscriptionGroups.length < 1}
						onClick={() =>
							setIsOpenedProductsMenu(
								(previousIsOpenedProductsMenu) =>
									!previousIsOpenedProductsMenu
							)
						}
					>
						{i18n.translate(
							getKebabCase(MENU_TYPES.productActivation)
						)}
					</Button>

					<ul
						className={classNames(
							'cp-products-list list-unstyled ml-3 overflow-hidden mb-1',
							{
								'cp-products-list-active': isOpenedProductsMenu,
							}
						)}
						ref={productActivationMenuRef}
					>
						{accountSubscriptionGroupsMenuItem}
					</ul>
				</li>

				<MenuItem to={getKebabCase(MENU_TYPES.teamMembers)}>
					{i18n.translate(getKebabCase(MENU_TYPES.teamMembers))}
				</MenuItem>
			</ul>
		</div>
	);
};

SideMenu.Skeleton = SideMenuSkeleton;
export default SideMenu;
