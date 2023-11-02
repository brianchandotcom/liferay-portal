/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useEffect, useMemo, useRef, useState} from 'react';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import {AttachmentsIcon} from '~/common/icons';
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
	const {featureFlags} = useAppPropertiesContext();

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
		<div className="bg-neutral-1 cp-side-menu ml-4 pl-4 pt-4">
			<ul className="list-unstyled mr-2">
				<div className="d-flex">
					<MenuItem iconKey="overview" to="">
						{i18n.translate(getKebabCase(MENU_TYPES.overview))}
					</MenuItem>
				</div>

				{featureFlags.includes('LPS-153478') &&
					hasLiferayExperienceCloud && (
						<div className="d-flex">
							<MenuItem
								iconKey="experienceCloud"
								to={getKebabCase(
									PRODUCT_TYPES.liferayExperienceCloud
								)}
							>
								{MENU_TYPES.liferayExperienceCloud}
							</MenuItem>
						</div>
					)}

				<li>
					<div className="d-flex">
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
									'text-neutral-4':
										activationSubscriptionGroups.length < 1,
									'text-neutral-10': !!activationSubscriptionGroups.length,
								}
							)}
							disabled={activationSubscriptionGroups.length < 1}
							iconKey="productActivation"
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
					</div>

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

				<div className="d-flex">
					<div className="align-items-center d-flex mr-2">
						<AttachmentsIcon />
					</div>

					<MenuItem to={getKebabCase(MENU_TYPES.attachments)}>
						{i18n.translate(getKebabCase(MENU_TYPES.attachments))}
					</MenuItem>
				</div>

				<div className="d-flex">
					<MenuItem
						iconKey="teamMembers"
						to={getKebabCase(MENU_TYPES.teamMembers)}
					>
						{i18n.translate(getKebabCase(MENU_TYPES.teamMembers))}
					</MenuItem>
				</div>
			</ul>
		</div>
	);
};

SideMenu.Skeleton = SideMenuSkeleton;
export default SideMenu;
