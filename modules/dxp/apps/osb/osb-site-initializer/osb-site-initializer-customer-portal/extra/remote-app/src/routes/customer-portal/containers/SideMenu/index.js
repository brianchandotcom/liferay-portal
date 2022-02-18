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
import {useEffect, useRef, useState} from 'react';
import {Link} from 'react-router-dom';
import Button from '../../../../common/components/Button';
import {useCustomerPortal} from '../../context';
import {MENU_TYPES, PAGE_TYPES, PRODUCT_TYPES} from '../../utils/constants';
import {getCamelCase} from '../../utils/getCamelCase';
import SideMenuSkeleton from './Skeleton';

const getSubscriptionKey = (name) => {
	const [prefixPath, suffixPath] = name.split(' ');

	if (suffixPath) {
		return `${prefixPath.toLowerCase()}${suffixPath}`;
	}

	return prefixPath.toLowerCase();
};

const ACTIVATION_PATH = 'activation/';

const MenuItem = ({activeButton, menuKey, setActiveButton, ...props}) => {
	const menuType = MENU_TYPES[menuKey];
	const redirectPage =
		menuType === MENU_TYPES.overview ? '' : PAGE_TYPES[menuKey];

	return (
		<li {...props}>
			<Link to={redirectPage}>
				<Button
					className={classNames(
						'btn-borderless mb-1 px-3 py-2 rounded text-neutral-10',
						{
							'cp-menu-btn-active': activeButton === menuType,
						}
					)}
					onClick={() => setActiveButton(menuType)}
				>
					{menuType}
				</Button>
			</Link>
		</li>
	);
};

const SideMenu = ({getCurrentPage, subscriptionGroups}) => {
	const [{assetsPath}] = useCustomerPortal();
	const [currentMenuSelected, setCurrentMenuSelected] = useState();
	const [hasOpenedProductsMenu, setHasOpenedProductsMenu] = useState(false);
	const productActivationButtonRef = useRef();

	useEffect(() => {
		const currentPage = getCurrentPage();
		if (currentPage) {
			const menuNestedOptions = {
				...MENU_TYPES,
				...PRODUCT_TYPES,
			};
			const pageRouteKey = getCamelCase(currentPage);

			setCurrentMenuSelected(menuNestedOptions[pageRouteKey]);
		}
	}, [getCurrentPage]);

	useEffect(() => {
		if (hasOpenedProductsMenu) {
			const expandedHeightProducts = subscriptionGroups.length * 48;
			productActivationButtonRef.current.style.maxHeight = `${expandedHeightProducts}px`;

			return;
		}
		productActivationButtonRef.current.style.maxHeight = '0px';
	}, [hasOpenedProductsMenu, subscriptionGroups.length]);

	const hasSelectedProduct = Object.values(PRODUCT_TYPES).includes(
		currentMenuSelected
	);

	return (
		<div className="bg-neutral-1 cp-side-menu mr-4 pl-4 pt-4">
			<ul className="list-unstyled mr-2">
				{Object.entries(MENU_TYPES).map((menuType) => {
					const [menuKey, menuName] = menuType;

					if (menuName !== MENU_TYPES.productActivation) {
						return (
							<MenuItem
								activeButton={currentMenuSelected}
								key={menuKey}
								menuKey={menuKey}
								setActiveButton={setCurrentMenuSelected}
							/>
						);
					}

					return (
						<li key={menuKey}>
							<Button
								appendIcon="angle-right-small"
								appendIconClassName="ml-auto"
								className={classNames(
									'align-items-center btn-borderless d-flex px-3 py-2 rounded text-neutral-10 w-100',
									{
										'cp-product-activation-active': hasOpenedProductsMenu,
										'cp-products-list-active': hasSelectedProduct,
									}
								)}
								onClick={() =>
									setHasOpenedProductsMenu(
										(previousHasOpenedProductsMenu) =>
											!previousHasOpenedProductsMenu
									)
								}
							>
								{MENU_TYPES.productActivation}
							</Button>

							<ul
								className={classNames(
									'cp-products-list list-unstyled ml-3 overflow-hidden mb-1',
									{
										'cp-products-list-active': hasOpenedProductsMenu,
									}
								)}
								ref={productActivationButtonRef}
							>
								{subscriptionGroups.map(({name}) => {
									const currentSubscription = name
										.split(' ')[0]
										.toLowerCase();

									const redirectPage =
										PAGE_TYPES[getSubscriptionKey(name)];

									const hasProductSelected =
										currentMenuSelected === name;

									const iconPath = `${assetsPath}/assets/navigation-menu/${currentSubscription}_icon${
										hasProductSelected ? '' : '_gray'
									}.svg`;

									return (
										<li key={name}>
											<Link
												to={`${ACTIVATION_PATH}${redirectPage}`}
											>
												<Button
													className={classNames(
														'align-items-center btn-borderless d-flex mt-1 px-3 py-2 rounded text-neutral-10',
														{
															'cp-menu-btn-active': hasProductSelected,
														}
													)}
													isImagePrependIcon
													onClick={() =>
														setCurrentMenuSelected(
															name
														)
													}
													prependIcon={iconPath}
												>
													{name}
												</Button>
											</Link>
										</li>
									);
								})}
							</ul>
						</li>
					);
				})}
			</ul>
		</div>
	);
};

SideMenu.Skeleton = SideMenuSkeleton;
export default SideMenu;
