/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Align} from '@clayui/drop-down';
import classNames from 'classnames';
import {useAtom} from 'jotai';
import {useEffect} from 'react';
import {Link} from 'react-router-dom';
import {headerAtom} from '~/atoms';
import BreadcrumbFinder from '~/components/BreadcrumbFinder';
import Permission from '~/core/Permission';

import DropDownWithActions from '../../DropDown/DropDown';

const Divider = () => <p className="mx-2 text-paragraph-lg">/</p>;

const HeaderBreadcrumb = () => {
	const [headerActions] = useAtom(headerAtom.headerActions);
	const [heading] = useAtom(headerAtom.heading);

	const filteredHeaderActions = Permission.filterActions(
		headerActions.actions,
		headerActions.item?.actions
	);

	useEffect(() => {
		const title = heading.at(-1)?.title;

		if (title) {
			document.title = title;
		}
	}, [heading]);

	return (
		<>
			<BreadcrumbFinder heading={heading} />

			<div className="d-flex flex-row justify-content-between w-100">
				<div className="d-flex flex-1">
					{heading.map((header, index) => {
						const isClickable =
							header.path && index !== heading.length - 1;

						const Component =
							isClickable && header.path
								? Link
								: (props: any) => <span {...props} />;

						return (
							<Component
								className={classNames(
									'tr-header-container__item'
								)}
								key={index}
								to={header.path as string}
							>
								<small className="pr-2 text-paragraph-xs text-secondary">
									{header.category ? (
										header.category.toUpperCase()
									) : (
										<>&ensp;</>
									)}
								</small>

								<div className="d-flex flex-row">
									<p
										className="tr-header-container__item__title"
										title={header.title}
									>
										{header.title}
									</p>

									{!!heading.length &&
										heading.length !== index + 1 && (
											<Divider />
										)}
								</div>
							</Component>
						);
					})}
				</div>

				<div className="align-items-center d-flex justify-content-center">
					{!!filteredHeaderActions.length && (
						<DropDownWithActions
							actions={filteredHeaderActions}
							item={headerActions.item}
							mutate={headerActions.mutate}
							position={Align.BottomLeft}
						/>
					)}
				</div>
			</div>
		</>
	);
};

export default HeaderBreadcrumb;
